package de.othr.plantico.ui.search

import androidx.lifecycle.ViewModel
import de.othr.plantico.database.entities.Plant

class SearchViewModel : ViewModel() {
    //TODO: Maybe change this to Filtering in database instead of accessing all plants?
    //Search for plants that contain the query as a substring. Not case-sensitive!
    fun searchForPlantsInList(plants: List<Plant>, query: String): List<Plant> {
        return plants.filter { plant: Plant -> plant.plantName.contains(query, ignoreCase = true) }
    }
}
