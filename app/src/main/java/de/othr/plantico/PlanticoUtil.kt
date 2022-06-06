package de.othr.plantico

import android.app.Activity
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.othr.plantico.ui.PlantActivity
import de.othr.plantico.ui.homescreen.HomescreenActivity
import de.othr.plantico.ui.search.SearchableActivity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*


fun Date.toPlanticoString(): String {
    val suffixes = arrayOf(
        "th", "st", "nd", "rd", "th", "th", "th", "th",
        "th", "th", "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th", "st"
    )
    val c = Calendar.getInstance()
    c.time = this
    val day = c[Calendar.DAY_OF_MONTH]
    val month_name = SimpleDateFormat("MMMM", Locale.ENGLISH).format(c.getTime())
    return "" + day + suffixes[day] + " " + month_name

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

fun String.parseStringToDate(): Date? {
    try {
        val formatter = SimpleDateFormat("dd.mm.yyyy")
        return formatter.parse(this)
    } catch (e: Exception) {
        return null;
    }

}

fun BottomNavigationView.setupMenuBinding(id: Int, activity: Activity) {
    this.selectedItemId = id
    this.setOnItemSelectedListener { menu ->

        when (menu.itemId) {

            R.id.action_home -> {
                val intent = Intent(activity, HomescreenActivity::class.java)
                activity.startActivity(intent)
                true
            }

            R.id.action_plant -> {
                val intent = Intent(activity, PlantActivity::class.java)
                activity.startActivity(intent)
                true
            }

            R.id.action_search -> {
                val intent = Intent(activity, SearchableActivity::class.java)
                activity.startActivity(intent)
                true
            }

            R.id.action_user -> {

                true
            }

            else -> false
        }
    }
}