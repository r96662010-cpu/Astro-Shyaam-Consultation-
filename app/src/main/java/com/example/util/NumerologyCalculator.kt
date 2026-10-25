package com.example.util

import com.example.data.model.NumerologyProfile
import java.util.Calendar

object NumerologyCalculator {

    private val letterValues = mapOf(
        'A' to 1, 'B' to 2, 'C' to 3, 'D' to 4, 'E' to 5,
        'F' to 6, 'G' to 7, 'H' to 8, 'I' to 9,
        'J' to 1, 'K' to 2, 'L' to 3, 'M' to 4, 'N' to 5,
        'O' to 6, 'P' to 7, 'Q' to 8, 'R' to 9,
        'S' to 1, 'T' to 2, 'U' to 3, 'V' to 4, 'W' to 5,
        'X' to 6, 'Y' to 7, 'Z' to 8
    )

    private val vowels = setOf('A', 'E', 'I', 'O', 'U')

    fun calculateProfile(name: String, year: Int, month: Int, day: Int): NumerologyProfile {
        val lifePath = calculateLifePath(year, month, day)
        val destiny = calculateExpressionNumber(name)
        val soulUrge = calculateSoulUrgeNumber(name)
        val personality = calculatePersonalityNumber(name)
        val birthday = reduceToSingleOrMaster(day)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val personalYear = calculatePersonalYear(month, day, currentYear)

        return NumerologyProfile(
            lifePathNumber = lifePath,
            destinyNumber = destiny,
            soulUrgeNumber = soulUrge,
            personalityNumber = personality,
            birthdayNumber = birthday,
            personalYearNumber = personalYear
        )
    }

    fun calculateLifePath(year: Int, month: Int, day: Int): Int {
        val reducedMonth = reduceToSingleOrMaster(month)
        val reducedDay = reduceToSingleOrMaster(day)
        val reducedYear = reduceToSingleOrMaster(year)

        return reduceToSingleOrMaster(reducedMonth + reducedDay + reducedYear)
    }

    fun calculateExpressionNumber(name: String): Int {
        val sum = name.uppercase()
            .filter { it in 'A'..'Z' }
            .sumOf { letterValues[it] ?: 0 }
        return if (sum == 0) 1 else reduceToSingleOrMaster(sum)
    }

    fun calculateSoulUrgeNumber(name: String): Int {
        val sum = name.uppercase()
            .filter { it in vowels }
            .sumOf { letterValues[it] ?: 0 }
        return if (sum == 0) 1 else reduceToSingleOrMaster(sum)
    }

    fun calculatePersonalityNumber(name: String): Int {
        val sum = name.uppercase()
            .filter { it in 'A'..'Z' && it !in vowels }
            .sumOf { letterValues[it] ?: 0 }
        return if (sum == 0) 1 else reduceToSingleOrMaster(sum)
    }

    fun calculatePersonalYear(month: Int, day: Int, targetYear: Int): Int {
        val rMonth = reduceToSingleOrMaster(month)
        val rDay = reduceToSingleOrMaster(day)
        val rYear = reduceToSingleOrMaster(targetYear)
        // Personal years are conventionally reduced to 1 through 9
        return reduceToSingleDigit(rMonth + rDay + rYear)
    }

    fun reduceToSingleOrMaster(n: Int): Int {
        var num = n
        while (num > 9 && num != 11 && num != 22 && num != 33) {
            num = num.toString().sumOf { it.digitToInt() }
        }
        return num
    }

    fun reduceToSingleDigit(n: Int): Int {
        var num = n
        while (num > 9) {
            num = num.toString().sumOf { it.digitToInt() }
        }
        return num
    }

    /**
     * Calculates compatibility score (0-100%) and celestial synergy description
     * between two Life Path numbers.
     */
    fun calculateCompatibility(lp1: Int, lp2: Int): Pair<Int, String> {
        val num1 = if (lp1 > 9) reduceToSingleDigit(lp1) else lp1
        val num2 = if (lp2 > 9) reduceToSingleDigit(lp2) else lp2

        // Triad affinities in Pythagorean Numerology:
        // Mind Triad: 1, 5, 7
        // Manifestation Triad: 2, 4, 8
        // Creative / Heart Triad: 3, 6, 9
        val triad1 = when (num1) {
            1, 5, 7 -> 1
            2, 4, 8 -> 2
            else -> 3
        }
        val triad2 = when (num2) {
            3, 6, 9 -> 3
            2, 4, 8 -> 2
            else -> 1
        }

        return when {
            num1 == num2 -> 92 to "Soul Mirror Bond: You share the exact same life frequency. Deep intrinsic understanding, though you must take care not to amplify each other's shadows."
            triad1 == triad2 -> 88 to "Harmonic Celestial Resonance: You belong to the same vibration family ($triad1). Your communication and fundamental life rhythm flow naturally like twin streams."
            (num1 in listOf(2, 6) && num2 in listOf(2, 6, 9)) || (num1 in listOf(1, 8) && num2 in listOf(1, 4, 8)) -> 85 to "Dynamic Power Complement: Distinct strengths that interlock seamlessly. One provides grounding while the other fuels visionary inspiration."
            (num1 == 1 && num2 == 9) || (num1 == 9 && num2 == 1) -> 82 to "Alpha & Omega Union: A sacred beginning and completion loop. You teach each other radical detachment and decisive will."
            (num1 == 4 && num2 == 5) || (num1 == 5 && num2 == 4) -> 74 to "Anchor and Sail Dynamic: 4 provides the immovable earth while 5 summons the wandering winds. Demands conscious patience and mutual awe."
            else -> 79 to "Complementary Karmic Growth: You awaken untapped dimensions in one another. With conscious compassion, this connection yields tremendous wisdom."
        }
    }
}
