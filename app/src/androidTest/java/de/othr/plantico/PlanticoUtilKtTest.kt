package de.othr.plantico

import org.junit.Assert.*
import org.junit.Test
import java.time.Instant
import java.util.*

class PlanticoUtilKtTest {

    private val twentyfourthMay: Date = Date.from(Instant.ofEpochSecond(1653394485))
    private val twentyfifthMay: Date = Date.from(Instant.ofEpochSecond(1653480885))

    @Test
    fun toPlanticoString() {
        val expected = "24th May"
        val actual = twentyfourthMay.toPlanticoString()
        assertEquals(expected, actual)
    }

    @Test
    fun addDays() {
        val expected = twentyfifthMay
        val actual = twentyfourthMay.addDays(1)
        assertEquals(expected, actual)
    }
}