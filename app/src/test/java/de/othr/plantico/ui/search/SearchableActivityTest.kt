package de.othr.plantico.ui.search

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.othr.plantico.database.entities.PlantCategory
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class SearchableActivityTest {
    @Test
    fun searchForPlantsInList() {
        launchActivity<SearchableActivity>().use { scenario ->
            scenario.onActivity { activity ->
                // Test search with a single category
                activity.selectedCategories = listOf(PlantCategory.INDOOR)
                assertEquals(6, activity.searchForPlantsInList().size)

                // Test search with multiple categories
                activity.selectedCategories = listOf(PlantCategory.INDOOR, PlantCategory.DURABLE, PlantCategory.NON_HARDY)
                assertEquals(9, activity.searchForPlantsInList().size)

                // Test search with all categories
                activity.selectedCategories = PlantCategory.values().toList() // all values
                assertEquals(10, activity.searchForPlantsInList().size)

                // Test search with no categories
                activity.selectedCategories = ArrayList()
                assertEquals(10, activity.searchForPlantsInList().size)
            }
        }
    }
}
