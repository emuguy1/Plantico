package de.othr.plantico.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import de.othr.plantico.database.daos.PlantDAO
import de.othr.plantico.database.entities.Plant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Plant::class), version = 1, exportSchema = false)
public abstract class PlanticoDB: RoomDatabase() {

    abstract fun plantDAO(): PlantDAO

    private class PlantDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.plantDAO())
                }
            }
        }

        suspend fun populateDatabase(plantDAO: PlantDAO) {
            plantDAO.insert(Plant(0, "Cactus"))
        }
    }


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PlanticoDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PlanticoDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlanticoDB::class.java,
                    "plantico_database"
                )
                    .addCallback(PlantDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance

            }
        }
    }
}