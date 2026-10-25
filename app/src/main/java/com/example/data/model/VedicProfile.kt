package com.example.data.model

enum class Graha(val sanskritName: String, val englishName: String, val gemstone: String, val mantra: String, val dayOfWeek: String) {
    SURYA("Surya", "Sun", "Ruby (Manikya)", "Om Suryaya Namaha", "Sunday"),
    CHANDRA("Chandra", "Moon", "Pearl (Moti)", "Om Chandraya Namaha", "Monday"),
    MANGALA("Mangala", "Mars", "Red Coral (Moonga)", "Om Angarakaya Namaha", "Tuesday"),
    BUDHA("Budha", "Mercury", "Emerald (Panna)", "Om Budhaya Namaha", "Wednesday"),
    GURU("Guru / Brihaspati", "Jupiter", "Yellow Sapphire (Pukhraj)", "Om Gram Greem Groom Sah Gurave Namaha", "Thursday"),
    SHUKRA("Shukra", "Venus", "Diamond (Heera) / White Zircon", "Om Shukraya Namaha", "Friday"),
    SHANI("Shani", "Saturn", "Blue Sapphire (Neelam)", "Om Sham Shanicharaya Namaha", "Saturday"),
    RAHU("Rahu", "North Lunar Node", "Hessonite (Gomed)", "Om Rahave Namaha", "Saturday evening"),
    KETU("Ketu", "South Lunar Node", "Cat's Eye (Lehsuniya)", "Om Ketave Namaha", "Tuesday")
}

data class VedicRashi(
    val sanskritName: String,
    val westernEquivalent: String,
    val symbol: String,
    val rulingGraha: Graha,
    val elementTattva: String,
    val nature: String,
    val spiritualPath: String
)

data class Nakshatra(
    val number: Int,
    val name: String,
    val rulingGraha: Graha,
    val deity: String,
    val symbol: String,
    val shakti: String,
    val qualities: String
)

data class VedicNumerology(
    val moolank: Int, // Root / Psychic Number (Day 1-31)
    val bhagyank: Int, // Destiny / Life Number (Full DOB)
    val namank: Int, // Chaldean Name Number
    val moolankGraha: Graha,
    val bhagyankGraha: Graha,
    val namankGraha: Graha,
    val luckyVedicDays: List<String>,
    val friendlyNumbers: List<Int>,
    val enemyNumbers: List<Int>,
    val primaryRemedyMantra: String,
    val recommendedGemstone: String
)

data class VedicProfile(
    val rashi: VedicRashi,
    val nakshatra: Nakshatra,
    val numerology: VedicNumerology,
    val dashaPeriod: String,
    val favorableDirections: String
)
