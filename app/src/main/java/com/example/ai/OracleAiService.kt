package com.example.ai

import android.util.Log
import com.example.data.model.DrawnCard
import com.example.data.model.UserProfile
import com.example.data.model.VedicProfile
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout

private const val TAG = "OracleAiService"

class OracleAiService {

    // Lazy initialization mandated by Firebase AI Logic skill to prevent early startup crashes
    private val generativeModel by lazy {
        Firebase.ai.generativeModel("gemini-3.8-flash")
    }

    /**
     * Streams conversational guidance from the AI (Mystic Oracle or Vedic Jyotish Rishi).
     */
    fun streamChatGuidance(
        userMessage: String,
        profile: UserProfile?,
        recentDrawnCards: List<DrawnCard> = emptyList(),
        isVedicMode: Boolean = false
    ): Flow<String> = flow {
        val systemPrompt = if (isVedicMode) {
            buildVedicSystemContext(profile?.name ?: "Seeker", profile?.vedicProfile)
        } else {
            buildWesternSystemContext(profile, recentDrawnCards)
        }

        val roleHeader = if (isVedicMode) "Acharya Jyotish Rishi's Counsel:" else "Mystic Oracle's Guidance:"
        val fullPrompt = "$systemPrompt\n\nSeeker's Inscription: $userMessage\n\n$roleHeader"

        try {
            withTimeout(60_000L) {
                val stream = generativeModel.generateContentStream(fullPrompt)
                stream.collect { chunk ->
                    val text = chunk.text
                    if (!text.isNullOrEmpty()) {
                        emit(text)
                    }
                }
            }
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "Chat guidance timed out", e)
            emit("\n\n*[The celestial frequencies experienced a cosmic delay. Please ask again in a moment.]*")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Chat guidance failed", e)
            emit("\n\n*[Celestial transit notice: ${e.localizedMessage ?: "Connection to the cosmic ether was interrupted."}]*")
        }
    }

    /**
     * Generates a deep, personalized interpretation of a Tarot Spread.
     */
    suspend fun interpretTarotSpread(
        spreadTitle: String,
        question: String,
        cards: List<DrawnCard>,
        profile: UserProfile?
    ): String {
        val cardsSummary = cards.joinToString("\n") { drawn ->
            "- Position '${drawn.positionName}': ${drawn.card.name} (${if (drawn.isReversed) "Reversed" else "Upright"}) - Arcana: ${drawn.card.arcana}, Astrology: ${drawn.card.astrologyAssociation}. Keywords: ${if (drawn.isReversed) drawn.card.reversedKeywords.joinToString() else drawn.card.uprightKeywords.joinToString()}"
        }

        val prompt = buildString {
            append("You are the revered Mystic Oracle, an ancient, compassionate master of Esoteric Tarot, Hermetic Astrology, and Sacred Pythagorean Numerology.\n")
            if (profile != null) {
                append("The Seeker is ${profile.name}, born under the celestial sign of ${profile.zodiacSign.englishName} (${profile.zodiacSign.symbol}, ${profile.zodiacSign.element.label} element) with Life Path Number ${profile.numerologyProfile.lifePathNumber} and Destiny Number ${profile.numerologyProfile.destinyNumber}.\n")
            }
            append("Tarot Spread: $spreadTitle\n")
            if (question.isNotBlank()) {
                append("Seeker's Sacred Question: \"$question\"\n")
            }
            append("Cards Drawn:\n$cardsSummary\n\n")
            append("Provide an enchanting, deeply insightful, poetic yet practical synthesis (3-4 concise paragraphs):\n")
            append("1. **The Cosmic Climate**: Synthesize how these cards reflect the current energetic tides.\n")
            append("2. **Core Revelation**: Deeply interpret each position in relation to the seeker's query and astrological makeup.\n")
            append("3. **Sacred Prescription**: Provide actionable spiritual counsel, affirmations, or warnings.\n")
            append("Maintain a mystical, sacred, empowering tone with elegant markdown formatting (headers, bullet points, bold key terms).")
        }

        return try {
            withTimeout(60_000L) {
                val response = generativeModel.generateContent(prompt)
                response.text ?: "The oracle's vision is clouded. Meditate and draw again."
            }
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "Tarot interpretation timed out", e)
            "The cosmic reading took longer than expected to channel. Please tap to re-consult the oracle."
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Tarot interpretation failed", e)
            "Celestial connection error: ${e.localizedMessage ?: "Unable to read cosmic currents."}"
        }
    }

    private fun buildWesternSystemContext(profile: UserProfile?, recentCards: List<DrawnCard>): String {
        return buildString {
            append("You are the Mystic Oracle, an ethereal, benevolent, and deeply wise esoteric guide specializing in Western Astrology, Tarot, and Pythagorean Numerology.\n")
            append("Tone: Poetic, empathetic, articulate, spiritually uplifting, authentic, and grounded in ancient cosmic wisdom. Never sound generic or clinical.\n")
            if (profile != null) {
                append("Seeker Profile:\n")
                append("- Name: ${profile.name}\n")
                append("- Sun Sign: ${profile.zodiacSign.englishName} ${profile.zodiacSign.symbol} (Ruler: ${profile.zodiacSign.ruler}, Element: ${profile.zodiacSign.element.label})\n")
                append("- Life Path Number: ${profile.numerologyProfile.lifePathNumber} (${profile.numerologyProfile.lifePathInfo.title})\n")
                append("- Destiny / Expression Number: ${profile.numerologyProfile.destinyNumber} (${profile.numerologyProfile.destinyInfo.title})\n")
                append("- Soul Urge Number: ${profile.numerologyProfile.soulUrgeNumber}\n")
                append("- Current Personal Year: ${profile.numerologyProfile.personalYearNumber} (${profile.numerologyProfile.personalYearInfo.title})\n")
            }
            if (recentCards.isNotEmpty()) {
                append("Recently Drawn Tarot Cards:\n")
                recentCards.forEach { card ->
                    append("- ${card.card.name} (${if (card.isReversed) "Reversed" else "Upright"})\n")
                }
            }
            append("\nInstructions:\n")
            append("- Ground your astrological, numerological, and tarot answers directly in the Seeker's sacred numbers and signs.\n")
            append("- Provide clear, insightful guidance for questions regarding love, career, karma, dream interpretation, and daily decisions.\n")
            append("- Use clean markdown with clear structure, occasional celestial symbolism, and inspiring conclusions.")
        }
    }

    private fun buildVedicSystemContext(name: String, vedicProfile: VedicProfile?): String {
        return buildString {
            append("You are Acharya Jyotish Rishi, a revered and compassionate Master of Vedic Astrology (Parashara Jyotish Shastra, Jaimini, 27 Nakshatras, and Chaldean/Vedic Ank Jyotish).\n")
            append("Tone: Serene, profound, spiritually grounding, respectful (use 'Namaste' or 'Pranam'), and rooted in ancient Vedic wisdom and Dharma.\n")
            append("Seeker: $name\n")
            if (vedicProfile != null) {
                append("Vedic Birth Coordinates:\n")
                append("- Sidereal Rashi: ${vedicProfile.rashi.sanskritName} (${vedicProfile.rashi.westernEquivalent}) - Tattva: ${vedicProfile.rashi.elementTattva}, Ruling Graha: ${vedicProfile.rashi.rulingGraha.sanskritName} (${vedicProfile.rashi.rulingGraha.englishName})\n")
                append("- Janma Nakshatra: ${vedicProfile.nakshatra.name} (Star #${vedicProfile.nakshatra.number}) - Ruled by ${vedicProfile.nakshatra.rulingGraha.sanskritName}, Deity: ${vedicProfile.nakshatra.deity}, Shakti: ${vedicProfile.nakshatra.shakti}\n")
                append("- Moolank (Psychic/Driver Number): ${vedicProfile.numerology.moolank} ruled by ${vedicProfile.numerology.moolankGraha.sanskritName}\n")
                append("- Bhagyank (Destiny/Life Number): ${vedicProfile.numerology.bhagyank} ruled by ${vedicProfile.numerology.bhagyankGraha.sanskritName}\n")
                append("- Namank (Chaldean Name Number): ${vedicProfile.numerology.namank} ruled by ${vedicProfile.numerology.namankGraha.sanskritName}\n")
                append("- Active Dasha Cycle: ${vedicProfile.dashaPeriod}\n")
                append("- Favorable Directions: ${vedicProfile.favorableDirections}\n")
                append("- Primary Vedic Mantra: ${vedicProfile.numerology.primaryRemedyMantra}\n")
                append("- Recommended Ratna (Gemstone): ${vedicProfile.numerology.recommendedGemstone}\n")
            }
            append("\nInstructions for Vedic Guidance:\n")
            append("1. Address the seeker with warmth and dharmic clarity.\n")
            append("2. When analyzing questions, explicitly cite their Vedic Rashi, Nakshatra qualities, and Grahas (Planets).\n")
            append("3. Provide authentic Vedic Upayas (Remedies): specific Mantras to chant (with meaning), Gemstones (Ratnas), acts of charity (Daan), auspicious days of the week (Vrat), and mindful adjustments.\n")
            append("4. Use Vedic terminology gracefully (Grahas, Bhavas, Dashas, Yogas, Nakshatras, Karma, Dharma) with concise explanations in English.\n")
            append("5. Maintain an inspiring, spiritually uplifting tone that empowers the seeker's free will while honoring their karmic destiny.")
        }
    }
}
