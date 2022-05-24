package de.othr.plantico.database

import androidx.annotation.WorkerThread
import de.othr.plantico.database.daos.OwnedPlantDAO
import de.othr.plantico.database.daos.PlantDAO
import de.othr.plantico.database.entities.OwnedPlant
import de.othr.plantico.database.entities.Plant
import kotlinx.coroutines.flow.Flow

class Repository(private val plantDAO: PlantDAO, private val ownedPlantDAO: OwnedPlantDAO) {
    val allPlants: Flow<List<Plant>> = plantDAO.getAllPlants()
    val allOwnedPlants: Flow<List<OwnedPlant>> = ownedPlantDAO.getAllOwnedPlants()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPlant(plant: Plant) {
        plantDAO.insert(plant)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertOwnedPlant(plant: OwnedPlant) {
        ownedPlantDAO.insertOwnedPlant(plant)
    }


}
