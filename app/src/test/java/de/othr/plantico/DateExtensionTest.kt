package de.othr.plantico

import de.othr.plantico.toPlanticoString
import de.othr.plantico.addDays
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.*

internal class DateExtensionTest {

    private val twentyfourthMay: Date = Date.from(Instant.ofEpochSecond(1653394485))
    private val twentyfifthMay: Date = Date.from(Instant.ofEpochSecond(1653480886))

    @Test
    @DisplayName("Test the Date Extension for PlanticoDateString")
    fun toPlanticoString() {
        val expected = "24th May"
        val actual = twentyfourthMay.toPlanticoString()
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Test the Date Extension for adding int Days")
    fun addDays(){
        val expected = twentyfifthMay
        val actual = twentyfourthMay.addDays(1)
        assertEquals(expected, actual)
    }
}