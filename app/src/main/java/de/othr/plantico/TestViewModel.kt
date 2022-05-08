package de.othr.plantico

import androidx.lifecycle.*
import de.othr.plantico.database.PlanticoDB
import de.othr.plantico.database.Repository
import de.othr.plantico.database.entities.Plant
import kotlinx.coroutines.launch

class TestViewModel(private val repository: Repository) : ViewModel() {
    val allPlants: LiveData<List<Plant>> = repository.allPlants.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(plant: Plant) = viewModelScope.launch {
        repository.insert(plant);
    }
}

class TestViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TestViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
