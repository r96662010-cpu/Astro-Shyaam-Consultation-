package com.example.data.model

enum class ArcanaType {
    MAJOR, MINOR
}

data class TarotCard(
    val id: Int,
    val name: String,
    val romanNumeral: String,
    val arcana: ArcanaType,
    val suit: String? = null,
    val astrologyAssociation: String,
    val element: String,
    val uprightKeywords: List<String>,
    val reversedKeywords: List<String>,
    val uprightMeaning: String,
    val reversedMeaning: String,
    val iconSymbol: String,
    val cosmicAdvice: String
)

data class DrawnCard(
    val card: TarotCard,
    val isReversed: Boolean,
    val positionName: String = "",
    val positionDescription: String = ""
)

enum class SpreadType(
    val title: String,
    val cardCount: Int,
    val description: String,
    val positionNames: List<String>,
    val positionDescriptions: List<String>
) {
    DAILY_CARD(
        "Card of the Day",
        1,
        "Daily focal energy, spiritual message, and guiding theme.",
        listOf("Today's Guiding Energy"),
        listOf("The core celestial frequency shaping your day")
    ),
    THREE_CARD_TIMELINE(
        "Past • Present • Future",
        3,
        "Illuminates the karmic roots, the active landscape, and the emerging destiny.",
        listOf("The Root (Past)", "The Catalyst (Present)", "The Horizon (Future)"),
        listOf("Formative karmic events and lessons learned", "Current crossroads and dominant vibrations", "Probable trajectory if current course is held")
    ),
    MIND_BODY_SPIRIT(
        "Mind • Body • Spirit",
        3,
        "A holistic scan of your mental focus, physical vitality, and soulful alignment.",
        listOf("Mind", "Body", "Spirit"),
        listOf("Mental clarity, thoughts, and subconscious beliefs", "Physical energy, environment, and grounded action", "Higher self, intuition, and spiritual alignment")
    ),
    LOVE_RELATIONSHIP(
        "Cosmic Connection (Love)",
        3,
        "Deep exploration into relational dynamics, mutual synergy, and emotional evolution.",
        listOf("Your Heart", "Their Energy", "The Union"),
        listOf("Your authentic emotional desires and inner posture", "Their unspoken feelings, challenges, and posture", "The synergy, lesson, and future potential of this bond")
    ),
    CAREER_DESTINY(
        "Career & Sovereign Path",
        3,
        "Strategic and spiritual counsel for professional ambition, finances, and destiny.",
        listOf("Current Standing", "The Obstacle / Test", "Path to Victory"),
        listOf("Your skills, resources, and current position", "Hidden stumbling blocks or internal resistance", "Decisive action required to achieve breakthrough")
    )
}
