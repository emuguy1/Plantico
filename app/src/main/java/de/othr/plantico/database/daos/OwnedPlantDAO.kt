package de.othr.plantico.database.daos

import androidx.room.*
import de.othr.plantico.database.entities.OwnedPlant
import kotlinx.coroutines.flow.Flow

@Dao
interface OwnedPlantDAO {
    @Query("SELECT * FROM OwnedPlant ORDER BY id")
    fun getAllOwnedPlants(): Flow<List<OwnedPlant>>

    @Query("SELECT * FROM OwnedPlant ORDER BY location ASC")
    fun getAllByPlantSortByLocation(): Flow<List<OwnedPlant>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOwnedPlant(plant: OwnedPlant)

    @Query("DELETE FROM OwnedPlant")
    suspend fun deleteAll()

    @Query("SELECT * FROM OwnedPlant WHERE id=:ownedPlantID")
    fun getOwnedPlantByID(ownedPlantID: Int): OwnedPlant

    @Query("DELETE FROM OwnedPlant WHERE id=:ownedPlantID")
    suspend fun deleteOwnedPlantByID(ownedPlantID: Int)

    @Query("SELECT * FROM OwnedPlant WHERE plantID=:plantID ORDER BY id")
    fun getAllByPlantID(plantID: Int): Flow<List<OwnedPlant>>

    @Query("SELECT * FROM OwnedPlant ORDER BY lastWatered + customWateringCycle * 86400000")
    fun getAllOrderedByNextWatering(): Flow<List<OwnedPlant>>

    @Update
    suspend fun updateOwnedPlant(ownedPlant: OwnedPlant)

    /*
    TODO
    @Query("SELECT * FROM OwnedPlant ORDER BY lastWatered + :plantCycle * 86400000")
    fun getAllOrderedByNextWatering(plantCycle: Int): Flow<List<OwnedPlant>>

    @Query("SELECT * FROM OwnedPlant ORDER BY lastWatered + customWateringCycle * 86400000")
    fun getAllOrderedByNextCustomWatering(): Flow<List<OwnedPlant>>

     */


}
