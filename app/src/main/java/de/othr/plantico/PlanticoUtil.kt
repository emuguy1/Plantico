package de.othr.plantico

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*


fun Date.toPlanticoString():String{
    val suffixes = arrayOf(
        "th", "st", "nd", "rd", "th", "th", "th", "th",
        "th", "th","th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th", "st")
    val c = Calendar.getInstance()
    c.time = this
    val day = c[Calendar.DAY_OF_MONTH]
    val month_name = SimpleDateFormat("MMMM", Locale.ENGLISH).format(c.getTime())
    return ""+day + suffixes[day] + " " + month_name

}

fun Date.addDays(days: Int): Date {
    val c: Calendar = Calendar.getInstance()
    c.time = this
    c.add(Calendar.DATE, days)
    return c.time
}

fun Date.nowUTC(): Date {
    return Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC))

}