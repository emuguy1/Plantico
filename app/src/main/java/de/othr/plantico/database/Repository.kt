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

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteOwnedPlantByID(ownedPlantID: Int) {
        ownedPlantDAO.deleteOwnedPlantByID(ownedPlantID)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateOwnedPlant(ownedPlant: OwnedPlant) {
        ownedPlantDAO.updateOwnedPlant(ownedPlant)
    }

    fun getPlantByID(plantID: Int): Plant {
        return plantDAO.getPlantByID(plantID)
    }

    fun getOwnedPlantByID(ownedPlantID: Int): OwnedPlant {
        return ownedPlantDAO.getOwnedPlantByID(ownedPlantID)
    }

    fun getAllOwnedPlantByPlantID(plantID: Int): Flow<List<OwnedPlant>> {
        return ownedPlantDAO.getAllByPlantID(plantID)
    }

    fun getAllOwnedPlantSortedByWateringTime(): Flow<List<OwnedPlant>>{
        return ownedPlantDAO.getAllOrderedByNextWatering()
    }


}
