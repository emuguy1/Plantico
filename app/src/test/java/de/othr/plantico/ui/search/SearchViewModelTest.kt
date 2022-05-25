package de.othr.plantico.ui.search

import de.othr.plantico.database.entities.Plant
import de.othr.plantico.database.entities.PlantCategory
import de.othr.plantico.database.entities.PlantDifficulty
import de.othr.plantico.database.entities.WateringLevel
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


internal class SearchViewModelTest {

    lateinit var searchViewModel: SearchViewModel
    lateinit var testPlantList: List<Plant>

    @BeforeEach
    fun setUp() {
        searchViewModel = SearchViewModel()
        testPlantList =
            listOf(createPlant("Aloe Vera"), createPlant("Cactus"), createPlant("Monstera"))
    }

    private fun createPlant(plantName: String): Plant {
        return Plant(
            plantName,
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            PlantDifficulty.EASY,
            listOf(PlantCategory.ALL_YEAR, PlantCategory.INDOOR),
            3,
            WateringLevel.LOW
        )
    }

    @Test
    @DisplayName("Test the search functionality for plants in one list")
    fun searchForPlantsInList() {
        // Test search with full name of a plant
        assertEquals(1, searchViewModel.searchForPlantsInList(testPlantList, "Cactus").size)

        // Test search with small Case
        assertEquals(1, searchViewModel.searchForPlantsInList(testPlantList, "cactus").size)

        // Test search with some characters of multiple plants
        assertEquals(2, searchViewModel.searchForPlantsInList(testPlantList, "era").size)

        // Test search with no results
        assertEquals(0, searchViewModel.searchForPlantsInList(testPlantList, "abcde").size)
    }
}
