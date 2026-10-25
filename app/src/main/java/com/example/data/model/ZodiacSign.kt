package com.example.data.model

enum class Element(val label: String, val icon: String, val traits: String) {
    FIRE("Fire", "🔥", "Passionate, dynamic, spontaneous, driven"),
    EARTH("Earth", "🌿", "Grounded, practical, loyal, structured"),
    AIR("Air", "💨", "Intellectual, communicative, visionary, free"),
    WATER("Water", "🌊", "Intuitive, emotional, deep, compassionate")
}

enum class ZodiacSign(
    val englishName: String,
    val symbol: String,
    val dateRange: String,
    val element: Element,
    val ruler: String,
    val traits: String,
    val dailyAdvice: String
) {
    ARIES(
        "Aries", "♈", "Mar 21 - Apr 19",
        Element.FIRE, "Mars",
        "Bold, pioneering, courageous, high energy",
        "Direct your raw fire into one clear goal today. Channel impatience into forward momentum."
    ),
    TAURUS(
        "Taurus", "♉", "Apr 20 - May 20",
        Element.EARTH, "Venus",
        "Sensual, steadfast, patient, grounded",
        "Anchor into physical comfort and trust your steady pace. Great things bloom from patience."
    ),
    GEMINI(
        "Gemini", "♊", "May 21 - Jun 20",
        Element.AIR, "Mercury",
        "Curious, versatile, witty, expressive",
        "Your mind is illuminated today. Speak your discoveries, but listen closely to quiet murmurs."
    ),
    CANCER(
        "Cancer", "♋", "Jun 21 - Jul 22",
        Element.WATER, "Moon",
        "Nurturing, intuitive, protective, empathetic",
        "Honor your emotional currents. Your intuition detects hidden truths beneath words."
    ),
    LEO(
        "Leo", "♌", "Jul 23 - Aug 22",
        Element.FIRE, "Sun",
        "Radiant, charismatic, generous, dramatic",
        "Let your inner golden light warm those around you. Lead with generosity of heart."
    ),
    VIRGO(
        "Virgo", "♍", "Aug 23 - Sep 22",
        Element.EARTH, "Mercury",
        "Discerning, analytical, helpful, refined",
        "Notice the holy beauty in fine details. Release perfectionism in favor of presence."
    ),
    LIBRA(
        "Libra", "♎", "Sep 23 - Oct 22",
        Element.AIR, "Venus",
        "Harmonious, charming, diplomatic, artistic",
        "Seek balance not by avoiding tension, but by centering your authentic truth."
    ),
    SCORPIO(
        "Scorpio", "♏", "Oct 23 - Nov 21",
        Element.WATER, "Pluto & Mars",
        "Intense, transformative, magnetic, perceptive",
        "Deep alchemy is underway. Shed old armor and allow personal rebirth to unfold."
    ),
    SAGITTARIUS(
        "Sagittarius", "♐", "Nov 22 - Dec 21",
        Element.FIRE, "Jupiter",
        "Philosophical, adventurous, optimistic, honest",
        "Expand your horizon today. The universe is inviting you to question beliefs and seek wisdom."
    ),
    CAPRICORN(
        "Capricorn", "♑", "Dec 22 - Jan 19",
        Element.EARTH, "Saturn",
        "Ambitious, disciplined, enduring, practical",
        "Every brick laid today builds an eternal legacy. Honor both your summit and your rest."
    ),
    AQUARIUS(
        "Aquarius", "♒", "Jan 20 - Feb 18",
        Element.AIR, "Uranus & Saturn",
        "Innovative, humanitarian, eccentric, visionary",
        "Break free of conformity. Your unconventional insight is a beacon for collective growth."
    ),
    PISCES(
        "Pisces", "♓", "Feb 19 - Mar 20",
        Element.WATER, "Neptune & Jupiter",
        "Mystical, compassionate, artistic, boundless",
        "The veil between dreams and reality is thin. Create, daydream, and trust the quiet synchronicity."
    );

    companion object {
        fun fromMonthDay(month: Int, day: Int): ZodiacSign {
            return when (month) {
                1 -> if (day <= 19) CAPRICORN else AQUARIUS
                2 -> if (day <= 18) AQUARIUS else PISCES
                3 -> if (day <= 20) PISCES else ARIES
                4 -> if (day <= 19) ARIES else TAURUS
                5 -> if (day <= 20) TAURUS else GEMINI
                6 -> if (day <= 20) GEMINI else CANCER
                7 -> if (day <= 22) CANCER else LEO
                8 -> if (day <= 22) LEO else VIRGO
                9 -> if (day <= 22) VIRGO else LIBRA
                10 -> if (day <= 22) LIBRA else SCORPIO
                11 -> if (day <= 21) SCORPIO else SAGITTARIUS
                12 -> if (day <= 21) SAGITTARIUS else CAPRICORN
                else -> ARIES
            }
        }
    }
}
