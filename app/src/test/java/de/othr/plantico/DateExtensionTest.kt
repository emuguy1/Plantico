package de.othr.plantico

import de.othr.plantico.ui.homescreen.addDays
import de.othr.plantico.ui.homescreen.toPlanticoString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.*

internal class DateExtensionTest {

    private val twentyfourthMay: Date = Date.from(Instant.ofEpochSecond(1653394485))
    private val twentyfifthMay: Date = Date.from(Instant.ofEpochSecond(1653480886))

    @Test
    fun toPlanticoString() {
        val expected = "24th May"
        val actual = twentyfourthMay.toPlanticoString()
        assertEquals(expected, actual)
    }

    @Test
    fun addDays(){
        val expected = twentyfifthMay
        val actual = twentyfourthMay.addDays(1)
        assertEquals(expected, actual)
    }
}