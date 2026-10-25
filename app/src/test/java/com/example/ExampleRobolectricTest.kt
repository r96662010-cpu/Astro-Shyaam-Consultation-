package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.ZodiacSign
import com.example.util.AstrologyCalculator
import com.example.util.NumerologyCalculator
import com.example.util.TarotDeck
import com.example.util.VedicCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context matches AstroOracle`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("AstroOracle", appName)
    }

    @Test
    fun `zodiac calculation returns accurate signs`() {
        assertEquals(ZodiacSign.CANCER, ZodiacSign.fromMonthDay(7, 15))
        assertEquals(ZodiacSign.ARIES, ZodiacSign.fromMonthDay(3, 25))
        assertEquals(ZodiacSign.SCORPIO, ZodiacSign.fromMonthDay(10, 31))
        assertEquals(ZodiacSign.PISCES, ZodiacSign.fromMonthDay(2, 20))
    }

    @Test
    fun `numerology life path calculation handles standard and master numbers`() {
        // 1998-07-15: 1+9+9+8 = 27 -> 9, month: 7, day: 1+5 = 6 -> 9 + 7 + 6 = 22 (Master Builder!)
        val lp = NumerologyCalculator.calculateLifePath(1998, 7, 15)
        assertEquals(22, lp)

        // 1990-01-01: 1+9+9+0 = 19 -> 10 -> 1, month: 1, day: 1 -> 1+1+1 = 3
        val lp2 = NumerologyCalculator.calculateLifePath(1990, 1, 1)
        assertEquals(3, lp2)
    }

    @Test
    fun `tarot deck has complete major arcana cards`() {
        assertEquals(22, TarotDeck.majorArcana.size)
        val fool = TarotDeck.majorArcana.find { it.id == 0 }
        assertNotNull(fool)
        assertEquals("The Fool", fool?.name)
        assertTrue(fool?.uprightKeywords?.isNotEmpty() == true)
        assertTrue(fool?.reversedKeywords?.isNotEmpty() == true)
    }

    @Test
    fun `vedic calculations verify rashis, nakshatras, and ank jyotish`() {
        assertEquals(12, VedicCalculator.rashis.size)
        assertEquals(27, VedicCalculator.nakshatras.size)

        // Birth on 15th: Moolank = 1+5 = 6 (Venus / Shukra)
        val vedicNum = VedicCalculator.calculateVedicNumerology("Celeste", 1998, 7, 15)
        assertEquals(6, vedicNum.moolank)
        assertEquals("Shukra", vedicNum.moolankGraha.sanskritName)
        assertTrue(vedicNum.primaryRemedyMantra.isNotBlank())
        assertTrue(vedicNum.recommendedGemstone.isNotBlank())
    }
}
