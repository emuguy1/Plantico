package de.othr.plantico.ui.search

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class SearchableActivityTest {
    @Test
    fun searchForPlantsInList() {
        launchActivity<SearchableActivity>().use { scenario ->
            scenario.onActivity { activity ->
                // Test search with full name of a plant
                assertEquals(1, activity.searchForPlantsInList("Aloe Vera").size)

                // Test search with small Case
                assertEquals(1, activity.searchForPlantsInList("aloe vera").size)

                // Test search with some characters of multiple plants
                assertEquals(2, activity.searchForPlantsInList("era").size)

                // Test search with no results
                assertEquals(0, activity.searchForPlantsInList("abcde").size)
            }
        }
    }
}
