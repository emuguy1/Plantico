package de.othr.plantico

import androidx.lifecycle.*
import de.othr.plantico.database.Repository
import de.othr.plantico.database.entities.OwnedPlant
import de.othr.plantico.database.entities.Plant
import kotlinx.coroutines.launch

class PlantViewModel(private val repository: Repository) : ViewModel() {
    val allPlants: LiveData<List<Plant>> = repository.allPlants.asLiveData()
    val allOwnedPlants: LiveData<List<OwnedPlant>> = repository.allOwnedPlants.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertPlant(plant: Plant) = viewModelScope.launch {
        repository.insertPlant(plant)
    }

    fun insertOwnedPlant(plant: OwnedPlant) = viewModelScope.launch {
        repository.insertOwnedPlant(plant)
    }

    fun deleteOwnedPlantByID(ownedPlantID: Int) = viewModelScope.launch {
        repository.deleteOwnedPlantByID(ownedPlantID)
    }

    fun updateOwnedPlant(ownedPlant: OwnedPlant) = viewModelScope.launch {
        repository.updateOwnedPlant(ownedPlant)
    }


    fun getPlantByID(plantID: Int): Plant {
        return repository.getPlantByID(plantID)
    }

    fun getOwnedPlantByID(ownedPlantID: Int): OwnedPlant {
        return repository.getOwnedPlantByID(ownedPlantID)
    }

    fun getAllOwnedPlantByPlantID(plantID: Int): LiveData<List<OwnedPlant>> {
        return repository.getAllOwnedPlantByPlantID(plantID).asLiveData()
    }

    fun getAllOwnedPlantSortedByWateringTime(): LiveData<List<OwnedPlant>> {
        return repository.getAllOwnedPlantSortedByWateringTime().asLiveData()
    }

}

class PlantViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlantViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
