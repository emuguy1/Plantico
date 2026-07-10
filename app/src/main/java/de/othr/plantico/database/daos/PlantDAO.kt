package de.othr.plantico.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.othr.plantico.database.entities.Plant
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDAO {
    @Query("SELECT * FROM plant ORDER BY id")
    fun getAllPlants(): Flow<List<Plant>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(plant: Plant)

    @Query("DELETE FROM Plant")
    suspend fun deleteAll()

    @Query("SELECT * FROM plant WHERE id=:plantID")
    fun getPlantByID(plantID: Int): Flow<Plant>
}
