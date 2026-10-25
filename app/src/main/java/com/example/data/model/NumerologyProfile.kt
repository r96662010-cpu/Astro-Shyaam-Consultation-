package com.example.data.model

data class NumerologyNumberInfo(
    val number: Int,
    val title: String,
    val keywords: String,
    val description: String,
    val rulingPlanet: String,
    val gift: String,
    val shadow: String
)

data class NumerologyProfile(
    val lifePathNumber: Int,
    val destinyNumber: Int,
    val soulUrgeNumber: Int,
    val personalityNumber: Int,
    val birthdayNumber: Int,
    val personalYearNumber: Int
) {
    val lifePathInfo: NumerologyNumberInfo
        get() = getNumberInfo(lifePathNumber)

    val destinyInfo: NumerologyNumberInfo
        get() = getNumberInfo(destinyNumber)

    val soulUrgeInfo: NumerologyNumberInfo
        get() = getNumberInfo(soulUrgeNumber)

    val personalYearInfo: NumerologyNumberInfo
        get() = getPersonalYearInfo(personalYearNumber)

    companion object {
        fun getNumberInfo(num: Int): NumerologyNumberInfo {
            return when (num) {
                1 -> NumerologyNumberInfo(
                    1, "The Primal Initiator & Leader",
                    "Independence, Originality, Courage, Ambition",
                    "You are here to forge unique trails and stand fiercely on your own ground. Natural leadership, creative impulse, and willpower define your essence.",
                    "Sun", "Fearless innovation and self-reliance", "Impatience, authoritarianism, isolation"
                )
                2 -> NumerologyNumberInfo(
                    2, "The Divine Harmonizer & Empath",
                    "Diplomacy, Sensitivity, Intuition, Partnership",
                    "You are the peacemaker and quiet mystic. You perceive subtle undercurrents between souls and flourish when building bridges of harmony.",
                    "Moon", "Deep psychic intuition and empathetic listening", "Over-sensitivity, people pleasing, self-doubt"
                )
                3 -> NumerologyNumberInfo(
                    3, "The Creative Alchemist & Expresser",
                    "Joy, Imagination, Eloquence, Magnetism",
                    "You are the sparkling voice of joy. You transform ideas into artistic wonder, uplifting others through speech, art, humor, and pure charisma.",
                    "Jupiter", "Boundless creative inspiration and infectious optimism", "Scattered focus, superficiality, mood swings"
                )
                4 -> NumerologyNumberInfo(
                    4, "The Sacred Architect & Builder",
                    "Discipline, Foundation, Loyalty, Realism",
                    "You are the stone pillar upon which lasting castles are erected. Diligent, grounded, and noble, you manifest stability out of chaotic dreams.",
                    "Saturn & Earth", "Ironclad reliability and masterful craftsmanship", "Rigidity, resistance to change, workaholism"
                )
                5 -> NumerologyNumberInfo(
                    5, "The Cosmic Nomad & Free Spirit",
                    "Freedom, Adventure, Adaptability, Catalyst",
                    "You seek the kaleidoscope of existence. Change is your lifeblood; you crave sensory exploration, travel, and breaking archaic chains.",
                    "Mercury", "Superhuman adaptability and fearless exploration", "Restlessness, impulsiveness, commitment avoidance"
                )
                6 -> NumerologyNumberInfo(
                    6, "The Nurturing Guardian & Healer",
                    "Unconditional Love, Service, Harmony, Responsibility",
                    "You radiate warmth and sanctuary. Devoted to home, family, community, and beauty, your presence heals wounds and balances discordant environments.",
                    "Venus", "Profound healing empathy and aesthetic grace", "Martyrdom, micromanagement, guilt"
                )
                7 -> NumerologyNumberInfo(
                    7, "The Mystic Philosopher & Seeker",
                    "Esoteric Wisdom, Solitude, Analysis, Spiritual Truth",
                    "You walk the path of the sage. Drawn to occult knowledge, philosophy, science, and the hidden mechanisms of the cosmos, you crave sacred truth.",
                    "Neptune", "Profound spiritual penetration and intellectual brilliance", "Detachment, cynicism, secrecy, aloofness"
                )
                8 -> NumerologyNumberInfo(
                    8, "The Sovereign Manifestor & Powerhouse",
                    "Abundance, Authority, Karma, Material Mastery",
                    "You understand the energetic law of reciprocity: as above, so below. You are built to handle immense resources, authority, and financial empires.",
                    "Saturn", "Executive mastery and reality manifestation", "Materialism, control obsession, emotional coldness"
                )
                9 -> NumerologyNumberInfo(
                    9, "The Universal Compassionate & Sage",
                    "Humanitarianism, Completion, Transcendence, Forgiveness",
                    "You carry the accumulated wisdom of all prior numbers. Your calling is global; you stand as a beacon of love, art, and universal brotherhood.",
                    "Mars & Neptune", "Selfless cosmic empathy and visionary perspective", "Difficulty letting go, bitterness, aloof martyrdom"
                )
                11 -> NumerologyNumberInfo(
                    11, "Master Number 11: The Illuminated Seer",
                    "Spiritual Illumination, Prophecy, Inspiration, Higher Truth",
                    "A bridge between the mundane and celestial realms. You experience intense intuitive lightning and serve as an inspiring cosmic conduit.",
                    "Uranus / Moon", "Direct psychic channeling and visionary leadership", "Nervous anxiety, intense internal pressure, overwhelm"
                )
                22 -> NumerologyNumberInfo(
                    22, "Master Number 22: The Master Builder",
                    "Cosmic Manifestation, Practical Genius, Global Legacy",
                    "Possesses the vision of 11 paired with the structural mastery of 4. You are meant to bring towering dreams into physical reality for humanity.",
                    "Pluto / Earth", "Manifesting impossible global structures and systems", "Crushing burdens of responsibility, fear of failure"
                )
                33 -> NumerologyNumberInfo(
                    33, "Master Number 33: The Master Teacher",
                    "Universal Love, Christ Consciousness, Spiritual Altruism",
                    "The rarest master vibration. Exemplifies divine devotion, compassionate teaching, and emotional healing at the highest level of being.",
                    "Venus / Neptune", "Spiritual alchemy and transcendent compassion", "Self-sacrifice to exhaustion, boundary dissolution"
                )
                else -> NumerologyNumberInfo(
                    num, "Sacred Vibration $num", "Evolution, Awakening, Balance",
                    "A multifaceted vibration combining sacred numeric harmonies.",
                    "Cosmos", "Intuition and inner strength", "Scattered intention"
                )
            }
        }

        fun getPersonalYearInfo(yearNum: Int): NumerologyNumberInfo {
            return when (yearNum) {
                1 -> NumerologyNumberInfo(1, "Year of New Beginnings & Seeds", "Planting, Independence, Fresh Starts", "A brand new 9-year cycle begins. Take bold leaps.", "Sun", "Initiative", "Fear of change")
                2 -> NumerologyNumberInfo(2, "Year of Patience & Cooperation", "Partnership, Quiet Growth, Waiting", "Nurture the seeds planted last year. Diplomacy brings rewards.", "Moon", "Intuition", "Impatience")
                3 -> NumerologyNumberInfo(3, "Year of Joy & Creative Flowering", "Expression, Social Circles, Travel", "Share your light and celebrate life. Express yourself freely.", "Jupiter", "Expansion", "Scattered focus")
                4 -> NumerologyNumberInfo(4, "Year of Foundations & Work", "Discipline, Health, Solid Systems", "Roll up your sleeves. Construct the fortress of your future.", "Saturn", "Groundedness", "Fatigue")
                5 -> NumerologyNumberInfo(5, "Year of Dynamic Freedom & Shifts", "Adventure, Pivot, Unpredictable Doors", "Midway through the 9-year cycle, major breakthroughs and unexpected journeys arise.", "Mercury", "Agility", "Recklessness")
                6 -> NumerologyNumberInfo(6, "Year of Heart, Home & Duty", "Family, Domestic Harmony, Healing", "Focus on relationships, family healing, and beautifying your sanctuary.", "Venus", "Love", "Martyrdom")
                7 -> NumerologyNumberInfo(7, "Year of Reflection & Soul Study", "Solitude, Meditation, Spiritual Depth", "A sacred pause for inner contemplation, study, and soul realignment.", "Neptune", "Inner sight", "Loneliness")
                8 -> NumerologyNumberInfo(8, "Year of Harvest & Power", "Finances, Recognition, Karmic Payoff", "Step into executive power and harvest the fruits of past labors.", "Saturn", "Abundance", "Greed")
                9 -> NumerologyNumberInfo(9, "Year of Completion & Release", "Clearing Space, Forgiveness, Closure", "The final year of your 9-year cycle. Release what is finished to make way for the new.", "Mars", "Liberation", "Clinging to the past")
                else -> NumerologyNumberInfo(yearNum, "Cosmic Transition Year", "Growth, Cycles", "A year of transformation.", "Cosmos", "Awareness", "Hesitation")
            }
        }
    }
}
