package de.othr.plantico

import de.othr.plantico.ui.homescreen.toPlanticoString
import org.junit.Test
import org.junit.Assert.*
import java.time.Instant
import java.util.*

internal class DateExtensionTest {

    @Test
    fun toPlanticoString() {
        val expected = "24th May"
        val actual = Date.from(Instant.ofEpochSecond(1653414767)).toPlanticoString()
        assertEquals(expected, actual)
    }
}