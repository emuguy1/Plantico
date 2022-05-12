package de.othr.plantico.database

import androidx.annotation.WorkerThread
import de.othr.plantico.database.daos.PlantDAO
import de.othr.plantico.database.entities.Plant
import kotlinx.coroutines.flow.Flow

class Repository(private val plantDAO: PlantDAO) {
    val allPlants: Flow<List<Plant>> = plantDAO.getAllPlants()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(plant: Plant) {
        plantDAO.insert(plant)
    }


}
