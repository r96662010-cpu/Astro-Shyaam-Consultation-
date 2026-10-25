package com.example.util

import com.example.data.model.Element
import com.example.data.model.ZodiacSign
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

data class DailyCosmicForecast(
    val dateString: String,
    val moonPhase: String,
    val moonPhaseSymbol: String,
    val luckyColor: String,
    val luckyNumbers: List<Int>,
    val powerAffirmation: String,
    val planetaryTransit: String,
    val elementEnergy: String
)

object AstrologyCalculator {

    fun parseBirthDate(dateStr: String): Triple<Int, Int, Int>? {
        // Expected format "yyyy-MM-dd"
        return try {
            val parts = dateStr.split("-").map { it.toInt() }
            if (parts.size == 3) Triple(parts[0], parts[1], parts[2]) else null
        } catch (_: Exception) {
            null
        }
    }

    fun getDailyForecast(sign: ZodiacSign, date: Calendar = Calendar.getInstance()): DailyCosmicForecast {
        val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
        val dateString = dateFormat.format(date.time)

        // Deterministic daily seed based on sign + day of year for consistent day reading
        val daySeed = date.get(Calendar.DAY_OF_YEAR) + date.get(Calendar.YEAR) * 365 + sign.ordinal * 17
        val rng = Random(daySeed)

        val moonPhases = listOf(
            "New Moon in Scorpio" to "🌑",
            "Waxing Crescent in Sagittarius" to "🌒",
            "First Quarter Moon in Capricorn" to "🌓",
            "Waxing Gibbous in Pisces" to "🌔",
            "Full Radiant Moon in Taurus" to "🌕",
            "Waning Gibbous in Gemini" to "🌖",
            "Last Quarter Moon in Virgo" to "🌗",
            "Waning Crescent in Libra" to "🌘"
        )
        val selectedMoon = moonPhases[rng.nextInt(moonPhases.size)]

        val colors = listOf(
            "Celestine Indigo", "Golden Starlight", "Mystic Amethyst",
            "Solar Amber", "Emerald Forest", "Moonstone Silver",
            "Rose Quartz Pink", "Obsidian Velvet", "Cosmic Sapphire"
        )
        val luckyColor = colors[rng.nextInt(colors.size)]

        val luckyNumbers = (1..3).map { rng.nextInt(1, 99) }.sorted()

        val affirmations = listOf(
            "I walk in divine synchronicity; what belongs to my soul will never pass me by.",
            "I open my heart to unexpected blessings and release fear of the unknown.",
            "My intuition is my sharpest compass, guiding me toward expansion and truth.",
            "The universe conspires in my favor as I align my actions with higher purpose.",
            "I am grounded like ancient stone and radiant like morning sunlight.",
            "I grant myself permission to outgrow what no longer honors my destiny.",
            "Peace is my sacred superpower; harmony effortlessly surrounds my aura today."
        )
        val powerAffirmation = affirmations[rng.nextInt(affirmations.size)]

        val transits = listOf(
            "Mercury sextile Jupiter: Ideal for clear contracts, profound conversations, and bold ideas.",
            "Venus trine Neptune: Deep emotional sensitivity, creative awakening, and magnetic attraction.",
            "Mars trine Saturn: Relentless discipline, physical stamina, and milestone breakthroughs.",
            "Sun conjunct Chiron: Healing of long-held spiritual doubts and reclaiming authentic power.",
            "Jupiter trine Uranus: Sudden strokes of good fortune and expansive perspective shifts."
        )
        val transit = transits[rng.nextInt(transits.size)]

        val elementEnergies = mapOf(
            Element.FIRE to "High creative drive. Take brave action, but pause before reacting in heat.",
            Element.EARTH to "Steady abundance vibration. Ground your nervous system in nature or tangible tasks.",
            Element.AIR to "Brilliant mental clarity. Ideation, networking, and writing are supercharged.",
            Element.WATER to "Heightened psychic sensitivity. Protect your energetic borders and trust your gut."
        )

        return DailyCosmicForecast(
            dateString = dateString,
            moonPhase = selectedMoon.first,
            moonPhaseSymbol = selectedMoon.second,
            luckyColor = luckyColor,
            luckyNumbers = luckyNumbers,
            powerAffirmation = powerAffirmation,
            planetaryTransit = transit,
            elementEnergy = elementEnergies[sign.element] ?: "Balanced cosmic alignment."
        )
    }
}
