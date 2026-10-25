package com.example.util

import com.example.data.model.Graha
import com.example.data.model.Nakshatra
import com.example.data.model.VedicNumerology
import com.example.data.model.VedicProfile
import com.example.data.model.VedicRashi

object VedicCalculator {

    // Chaldean (Cheiro) letter system widely practiced in Vedic Numerology (Ank Jyotish)
    private val chaldeanValues = mapOf(
        'A' to 1, 'I' to 1, 'J' to 1, 'Q' to 1, 'Y' to 1,
        'B' to 2, 'K' to 2, 'R' to 2,
        'C' to 3, 'G' to 3, 'L' to 3, 'S' to 3,
        'D' to 4, 'M' to 4, 'T' to 4,
        'E' to 5, 'H' to 5, 'N' to 5, 'X' to 5,
        'U' to 6, 'V' to 6, 'W' to 6,
        'O' to 7, 'Z' to 7,
        'F' to 8, 'P' to 8
    )

    val rashis = listOf(
        VedicRashi("Mesha", "Aries", "♈", Graha.MANGALA, "Agni (Fire)", "Dynamic, courageous, pioneering, leader", "Karma Yoga & Selfless Leadership"),
        VedicRashi("Vrishabha", "Taurus", "♉", Graha.SHUKRA, "Prithvi (Earth)", "Steadfast, prosperous, devoted, sensual", "Bhakti Yoga & Grounded Manifestation"),
        VedicRashi("Mithuna", "Gemini", "♊", Graha.BUDHA, "Vayu (Air)", "Intellectual, eloquent, versatile, communicator", "Jnana Yoga & Communication"),
        VedicRashi("Karka", "Cancer", "♋", Graha.CHANDRA, "Jala (Water)", "Intuitive, nurturing, deeply protective, psychic", "Devotion, Heart Centering & Compassion"),
        VedicRashi("Simha", "Leo", "♌", Graha.SURYA, "Agni (Fire)", "Radiant, sovereign, noble, courageous", "Dharma & Radiant Generosity"),
        VedicRashi("Kanya", "Virgo", "♍", Graha.BUDHA, "Prithvi (Earth)", "Analytical, healing, service-oriented, discerning", "Seva (Selfless Service) & Sacred Discipline"),
        VedicRashi("Tula", "Libra", "♎", Graha.SHUKRA, "Vayu (Air)", "Harmonious, diplomatic, aesthetic, righteous", "Dharmic Partnership & Justice"),
        VedicRashi("Vrishchika", "Scorpio", "♏", Graha.MANGALA, "Jala (Water)", "Transformational, mystical, penetrating, secretive", "Kundalini Awakening & Deep Alchemy"),
        VedicRashi("Dhanu", "Sagittarius", "♐", Graha.GURU, "Agni (Fire)", "Philosophical, righteous, seeker of truth", "Guru Bhakti & Higher Knowledge"),
        VedicRashi("Makara", "Capricorn", "♑", Graha.SHANI, "Prithvi (Earth)", "Disciplined, enduring, ambitious, karmic", "Patient Karma Yoga & Enduring Legacy"),
        VedicRashi("Kumbha", "Aquarius", "♒", Graha.SHANI, "Vayu (Air)", "Humanitarian, visionary, unconventional, broad", "Loka Sangraha (Welfare of the World)"),
        VedicRashi("Meena", "Pisces", "♓", Graha.GURU, "Jala (Water)", "Transcendental, compassionate, spiritually receptive", "Moksha (Spiritual Liberation) & Surrender")
    )

    val nakshatras = listOf(
        Nakshatra(1, "Ashwini", Graha.KETU, "Ashwini Kumaras (Celestial Physicians)", "Horse's Head", "Power to heal and initiate rapid speed", "Agile, heroic, pioneer, spontaneous"),
        Nakshatra(2, "Bharani", Graha.SHUKRA, "Yama (Lord of Dharma & Death)", "Yoni (Vessel of Life)", "Power to cleanse and carry away life's dross", "Intense, creative, passionate, transformative"),
        Nakshatra(3, "Krittika", Graha.SURYA, "Agni (God of Fire)", "Flame / Razor", "Power to burn impurities and illuminate truth", "Sharp, determined, courageous, noble protector"),
        Nakshatra(4, "Rohini", Graha.CHANDRA, "Brahma (The Creator)", "Chariot / Temple Banyan", "Power of supreme fertility and artistic charm", "Sensual, magnetic, deeply creative, prosperous"),
        Nakshatra(5, "Mrigashira", Graha.MANGALA, "Soma (Moon God of Nectar)", "Deer's Head", "Power of searching, curiosity and spiritual wandering", "Gentle, inquisitive, friendly, seeking essence"),
        Nakshatra(6, "Ardra", Graha.RAHU, "Rudra (Storm God of Destruction)", "Teardrop / Diamond", "Power to dissolve attachments through storm breakthroughs", "Intense, intellectual, transformative, resilient"),
        Nakshatra(7, "Punarvasu", Graha.GURU, "Aditi (Cosmic Mother)", "Quiver of Arrows", "Power of renewal, return of light and sanctuary", "Nurturing, benevolent, philosophic, forgiving"),
        Nakshatra(8, "Pushya", Graha.SHANI, "Brihaspati (Spiritual Guru)", "Lotus / Cow's Udder", "Most auspicious star: supreme nourishment and dharma", "Spiritual leader, disciplined, dependable, pious"),
        Nakshatra(9, "Ashlesha", Graha.BUDHA, "Nagas (Divine Serpents)", "Coiled Serpent", "Power of esoteric wisdom, hypnosis and intense intuition", "Mystical, perceptive, strategic, occult wisdom"),
        Nakshatra(10, "Magha", Graha.KETU, "Pitris (Ancestral Spirits)", "Royal Throne", "Power of ancestral lineage, authority and legacy", "Regal, commanding, dignified, loyal to roots"),
        Nakshatra(11, "Purva Phalguni", Graha.SHUKRA, "Bhaga (God of Fortune & Prosperity)", "Hammock / Front Legs of Bed", "Power of love, delight, relaxation and artistic passion", "Charismatic, affectionate, joyful, romantic"),
        Nakshatra(12, "Uttara Phalguni", Graha.SURYA, "Aryaman (God of Friendship & Honor)", "Four Legs of Bed", "Power of noble patronage, prosperity and steadfast friendship", "Helpful, honorable, enduring, generous patron"),
        Nakshatra(13, "Hasta", Graha.CHANDRA, "Savitr (Solar Awakening Deity)", "Open Hand / Fist", "Power to manifest tangible wealth through skillful hands", "Artisan, healer, witty, diligent, resourceful"),
        Nakshatra(14, "Chitra", Graha.MANGALA, "Vishwakarma (Celestial Architect)", "Shining Pearl / Jewel", "Power of aesthetic brilliance, architecture and divine spark", "Artistic genius, stylish, perfectionist, charismatic"),
        Nakshatra(15, "Swati", Graha.RAHU, "Vayu (Wind Deity)", "Young Sprout swaying in Wind", "Power of gentle adaptability, commerce and independence", "Diplomatic, freedom-loving, merchant, flexible"),
        Nakshatra(16, "Vishakha", Graha.GURU, "Indragni (Indra & Agni)", "Triumphal Arch", "Power to achieve determined triumph through concentrated focus", "Ambitious, single-pointed, victorious, relentless"),
        Nakshatra(17, "Anuradha", Graha.SHANI, "Mitra (God of Devotion & Friendship)", "Lotus Flower / Triumphal Staff", "Power of deep friendship, devotion and Radha-Krishna bhakthi", "Devoted, traveling seeker, compassionate, loyal"),
        Nakshatra(18, "Jyeshtha", Graha.BUDHA, "Indra (King of the Gods)", "Circular Amulet / Talisman", "Power of seniority, courage, defense and overcoming rivals", "Proud, protective, heroic leader, occult defender"),
        Nakshatra(19, "Mula", Graha.KETU, "Nirriti (Goddess of Dissolution)", "Tied Bunch of Roots", "Power to uproot ignorance and penetrate core reality", "Profound investigator, philosopher, transformative"),
        Nakshatra(20, "Purva Ashadha", Graha.SHUKRA, "Apas (Sacred Waters)", "Fan / Elephant's Tusk", "Power of invincibility and purifying rejuvenation", "Proud, charismatic, inspiring, victorious orator"),
        Nakshatra(21, "Uttara Ashadha", Graha.SURYA, "Vishwa Devas (Universal Cosmic Principles)", "Small Bed / Elephant Tusk", "Power of permanent victory and righteous statesmanship", "Righteous, humble leader, unshakeable integrity"),
        Nakshatra(22, "Shravana", Graha.CHANDRA, "Vishnu (Preserver of the Cosmos)", "Ear / Three Footsteps of Vamana", "Power of sacred listening, learning and chanting mantras", "Wisdom seeker, deep listener, erudite scholar"),
        Nakshatra(23, "Dhanishta", Graha.MANGALA, "Ashta Vasus (Eight Gods of Light)", "Mridanga Drum / Flute", "Power of wealth, rhythm, musical alchemy and renown", "Generous, radiant, music lover, prosperous"),
        Nakshatra(24, "Shatabhisha", Graha.RAHU, "Varuna (Lord of the Cosmic Waters)", "Empty Circle / 100 Physicians", "Power of miraculous healing and occult mysteries", "Visionary, secretive, physician, transcendent thinker"),
        Nakshatra(25, "Purva Bhadrapada", Graha.GURU, "Aja Ekapada (One-Footed Cosmic Serpent)", "Swords / Front of Funeral Cot", "Power of intense tapasya (spiritual heat) and ascension", "Ascetic, deep occultist, intense, uncompromising"),
        Nakshatra(26, "Uttara Bhadrapada", Graha.SHANI, "Ahir Budhnya (Serpent of the Celestial Depths)", "Twin Serpents / Back of Cot", "Power of peaceful wisdom, deep sleep and cosmic protection", "Serene, enlightened, benevolent counselor, grounded"),
        Nakshatra(27, "Revati", Graha.BUDHA, "Pushan (Shepherd of Souls)", "Pair of Swimming Fishes", "Power of safe journey, abundance and guiding souls to Moksha", "Gentle, compassionate, highly psychic, protective guide")
    )

    fun calculateVedicProfile(name: String, year: Int, month: Int, day: Int): VedicProfile {
        // Sidereal calculation: In Vedic Jyotish, the Sidereal Zodiac accounts for ~24 deg precession.
        // As an accurate Sidereal proxy, we derive the Sidereal Sun/Moon Rashi and Nakshatra
        val dayOfYear = getDayOfYear(year, month, day)

        // Sidereal Rashi boundaries (approximate calendar dates shifted by ~24 days from Western dates)
        val rashiIndex = getSiderealRashiIndex(month, day)
        val rashi = rashis[rashiIndex]

        // 27 Nakshatras span 360 degrees (13 deg 20 min each).
        // Deterministic mapping based on birth coordinates:
        val nakshatraIndex = ((dayOfYear * 27) / 365) % 27
        val nakshatra = nakshatras[nakshatraIndex]

        val numerology = calculateVedicNumerology(name, year, month, day)

        val dashaPlanets = listOf("Ketu", "Venus", "Sun", "Moon", "Mars", "Rahu", "Jupiter", "Saturn", "Mercury")
        val currentDashaIndex = (year + month + day) % dashaPlanets.size
        val dashaPeriod = "${dashaPlanets[currentDashaIndex]} Mahadasha (Transiting active planetary cycle)"

        val favorableDirections = when (rashi.elementTattva.take(4)) {
            "Agni" -> "East & North-East (Surya & Guru alignment)"
            "Prithvi" -> "South & South-West (Mangala & Rahu pacification)"
            "Vayu" -> "North-West & West (Vayu & Shani harmony)"
            else -> "North & North-East (Kuber & Chandra abundance)"
        }

        return VedicProfile(
            rashi = rashi,
            nakshatra = nakshatra,
            numerology = numerology,
            dashaPeriod = dashaPeriod,
            favorableDirections = favorableDirections
        )
    }

    fun calculateVedicNumerology(name: String, year: Int, month: Int, day: Int): VedicNumerology {
        // Moolank (Root/Driver Number): Day of birth (1-31) reduced to single digit 1-9
        val moolank = reduceToSingleDigit(day)

        // Bhagyank (Destiny/Conductor Number): Full DOB sum reduced to single digit 1-9
        val dobSum = day.toString().sumOf { it.digitToInt() } +
                month.toString().sumOf { it.digitToInt() } +
                year.toString().sumOf { it.digitToInt() }
        val bhagyank = reduceToSingleDigit(dobSum)

        // Namank (Chaldean Name Number):
        val cleanName = name.uppercase().filter { it in 'A'..'Z' }
        val nameSum = cleanName.sumOf { chaldeanValues[it] ?: 0 }
        val namank = if (nameSum == 0) 1 else reduceToSingleDigit(nameSum)

        val moolankGraha = getGrahaForNumber(moolank)
        val bhagyankGraha = getGrahaForNumber(bhagyank)
        val namankGraha = getGrahaForNumber(namank)

        val (friendly, enemy) = getVedicNumberRelationships(moolank)

        val luckyDays = when (moolank) {
            1 -> listOf("Sunday", "Monday")
            2 -> listOf("Monday", "Sunday")
            3 -> listOf("Thursday", "Tuesday", "Sunday")
            4 -> listOf("Saturday", "Wednesday")
            5 -> listOf("Wednesday", "Friday")
            6 -> listOf("Friday", "Wednesday")
            7 -> listOf("Sunday", "Monday", "Wednesday")
            8 -> listOf("Saturday", "Friday")
            else -> listOf("Tuesday", "Thursday", "Sunday")
        }

        return VedicNumerology(
            moolank = moolank,
            bhagyank = bhagyank,
            namank = namank,
            moolankGraha = moolankGraha,
            bhagyankGraha = bhagyankGraha,
            namankGraha = namankGraha,
            luckyVedicDays = luckyDays,
            friendlyNumbers = friendly,
            enemyNumbers = enemy,
            primaryRemedyMantra = moolankGraha.mantra,
            recommendedGemstone = moolankGraha.gemstone
        )
    }

    private fun getGrahaForNumber(num: Int): Graha {
        return when (num) {
            1 -> Graha.SURYA
            2 -> Graha.CHANDRA
            3 -> Graha.GURU
            4 -> Graha.RAHU
            5 -> Graha.BUDHA
            6 -> Graha.SHUKRA
            7 -> Graha.KETU
            8 -> Graha.SHANI
            else -> Graha.MANGALA
        }
    }

    private fun getVedicNumberRelationships(moolank: Int): Pair<List<Int>, List<Int>> {
        return when (moolank) {
            1 -> listOf(2, 3, 5, 9) to listOf(6, 8)
            2 -> listOf(1, 3, 5) to listOf(4, 7, 8, 9)
            3 -> listOf(1, 2, 5, 9) to listOf(6)
            4 -> listOf(1, 5, 6, 8) to listOf(2, 4, 8, 9)
            5 -> listOf(1, 3, 6, 8) to emptyList() // Mercury is friends with all
            6 -> listOf(1, 4, 5, 7, 8) to listOf(3)
            7 -> listOf(1, 2, 5, 6) to listOf(8, 9)
            8 -> listOf(3, 5, 6, 7) to listOf(1, 2, 4, 8)
            else -> listOf(1, 2, 3, 5) to listOf(4, 7)
        }
    }

    private fun getSiderealRashiIndex(month: Int, day: Int): Int {
        // Sidereal dates roughly:
        // Mesha (Aries): Apr 14 - May 14
        // Vrishabha (Taurus): May 15 - Jun 14
        // Mithuna (Gemini): Jun 15 - Jul 15
        // Karka (Cancer): Jul 16 - Aug 16
        // Simha (Leo): Aug 17 - Sep 16
        // Kanya (Virgo): Sep 17 - Oct 16
        // Tula (Libra): Oct 17 - Nov 15
        // Vrishchika (Scorpio): Nov 16 - Dec 15
        // Dhanu (Sagittarius): Dec 16 - Jan 13
        // Makara (Capricorn): Jan 14 - Feb 12
        // Kumbha (Aquarius): Feb 13 - Mar 13
        // Meena (Pisces): Mar 14 - Apr 13
        return when (month) {
            1 -> if (day <= 13) 8 else 9
            2 -> if (day <= 12) 9 else 10
            3 -> if (day <= 13) 10 else 11
            4 -> if (day <= 13) 11 else 0
            5 -> if (day <= 14) 0 else 1
            6 -> if (day <= 14) 1 else 2
            7 -> if (day <= 15) 2 else 3
            8 -> if (day <= 16) 3 else 4
            9 -> if (day <= 16) 4 else 5
            10 -> if (day <= 16) 5 else 6
            11 -> if (day <= 15) 6 else 7
            12 -> if (day <= 15) 7 else 8
            else -> 0
        }
    }

    private fun reduceToSingleDigit(n: Int): Int {
        var num = n
        while (num > 9) {
            num = num.toString().sumOf { it.digitToInt() }
        }
        return if (num == 0) 1 else num
    }

    private fun getDayOfYear(year: Int, month: Int, day: Int): Int {
        val daysInMonths = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        val isLeap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
        if (isLeap) daysInMonths[1] = 29
        var sum = 0
        for (i in 0 until (month - 1).coerceIn(0, 11)) {
            sum += daysInMonths[i]
        }
        return (sum + day).coerceIn(1, 366)
    }
}
