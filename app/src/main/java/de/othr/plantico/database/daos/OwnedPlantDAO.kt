package de.othr.plantico.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.othr.plantico.database.entities.OwnedPlant
import kotlinx.coroutines.flow.Flow

@Dao
interface OwnedPlantDAO {
    @Query("SELECT * FROM ownedplant ORDER BY id")
    fun getAllOwnedPlants(): Flow<List<OwnedPlant>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOwnedPlant(plant: OwnedPlant)

    @Query("DELETE FROM OwnedPlant")
    suspend fun deleteAll()
}