package de.othr.plantico.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import de.othr.plantico.database.daos.OwnedPlantDAO
import de.othr.plantico.database.daos.PlantDAO
import de.othr.plantico.database.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Plant::class, OwnedPlant::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PlanticoDB : RoomDatabase() {

    abstract fun plantDAO(): PlantDAO
    abstract fun ownedPlantDAO(): OwnedPlantDAO


    private class PlantDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.plantDAO(), database.ownedPlantDAO())
                }
            }
        }

        suspend fun populateDatabase(plantDAO: PlantDAO, ownedPlantDAO: OwnedPlantDAO) {
            plantDAO.insert(
                Plant(
                    "Golden Barell Cactus",
                    "Echinocactus grusonii",
                    "Cactaceae",
                    "Cactoideae",
                    "Echinocactus",
                    "E. grusonii",
                    "" +
                            "The golden barell cactus is a cactus in the form of a large spherical globe. It may reach a height of up to 1 meter after growing many years, and has a lifetime of roughly 30 years. It is one of the most popular cacti in cultivation due to its basic requirements.",
                    "The plants do have some basic requirements; an average minimum winter temperature of 12 °C (53.6 °F); and good drainage with less watering in winter.[4] Excess water in cool periods may lead to rot. Golden Barrels are hardy to about −8 °C (15 °F) for brief periods.",
                    "Water the cactus sparingly from spring until fall and stop watering during winter. Do not overwater as this can lead to roo rot!",
                    PlantDifficulty.EASY,
                    listOf(
                        PlantCategory.DURABLE,
                        PlantCategory.INDOOR,
                        PlantCategory.OUTDOOR,
                        PlantCategory.HIGH_LIGHT
                    ),
                    30,
                    WateringLevel.LOW
                )
            )
            plantDAO.insert(
                Plant(
                    "Monstera",
                    "Echinocactus grusonii",
                    "Cactaceae",
                    "Cactoideae",
                    "Echinocactus",
                    "E. grusonii",
                    "" +
                            "The golden barell cactus is a cactus in the form of a large spherical globe. It may reach a height of up to 1 meter after growing many years, and has a lifetime of roughly 30 years. It is one of the most popular cacti in cultivation due to its basic requirements.",
                    "The plants do have some basic requirements; an average minimum winter temperature of 12 °C (53.6 °F); and good drainage with less watering in winter.[4] Excess water in cool periods may lead to rot. Golden Barrels are hardy to about −8 °C (15 °F) for brief periods.",
                    "Water the cactus sparingly from spring until fall and stop watering during winter. Do not overwater as this can lead to roo rot!",
                    PlantDifficulty.EASY,
                    listOf(
                        PlantCategory.DURABLE,
                        PlantCategory.INDOOR,
                        PlantCategory.OUTDOOR,
                        PlantCategory.HIGH_LIGHT
                    ),
                    30,
                    WateringLevel.LOW
                )
            )

            plantDAO.insert(
                Plant(
                    "Aloe Vera",
                    "Echinocactus grusonii",
                    "Cactaceae",
                    "Cactoideae",
                    "Echinocactus",
                    "E. grusonii",
                    "" +
                            "The golden barell cactus is a cactus in the form of a large spherical globe. It may reach a height of up to 1 meter after growing many years, and has a lifetime of roughly 30 years. It is one of the most popular cacti in cultivation due to its basic requirements.",
                    "The plants do have some basic requirements; an average minimum winter temperature of 12 °C (53.6 °F); and good drainage with less watering in winter.[4] Excess water in cool periods may lead to rot. Golden Barrels are hardy to about −8 °C (15 °F) for brief periods.",
                    "Water the cactus sparingly from spring until fall and stop watering during winter. Do not overwater as this can lead to roo rot!",
                    PlantDifficulty.EASY,
                    listOf(
                        PlantCategory.DURABLE,
                        PlantCategory.INDOOR,
                        PlantCategory.OUTDOOR,
                        PlantCategory.HIGH_LIGHT
                    ),
                    30,
                    WateringLevel.LOW
                )
            )
            ownedPlantDAO.insertOwnedPlant(
                OwnedPlant(
                    "My first cactus!",
                    1,
                    null,
                    null,
                    null,
                    null
                )
            )
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
