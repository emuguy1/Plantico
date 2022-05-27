package de.othr.plantico.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import de.othr.plantico.database.daos.OwnedPlantDAO
import de.othr.plantico.database.daos.PlantDAO
import de.othr.plantico.database.entities.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException
import java.util.*

@RunWith(RobolectricTestRunner::class)
class PlanticoDBTest {
    private lateinit var planticoDB: PlanticoDB
    private lateinit var plantDAO: PlantDAO
    private lateinit var ownedPlantDAO: OwnedPlantDAO


    @Before
    fun setUp() {
        planticoDB = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            PlanticoDB::class.java
        ).build()
        plantDAO = planticoDB.plantDAO()
        ownedPlantDAO = planticoDB.ownedPlantDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        planticoDB.close()
    }

    @Test
    @Throws(Exception::class)
    fun writePlantAndReadInList() = runBlocking {
        val plant: Plant = Plant(
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
        plantDAO.insert(plant)
        val plants = plantDAO.getAllPlants()
        assert(plants.first().contains(plant))
        val ownedPlant = OwnedPlant(
            "My first cactus!",
            1,
            Date(),
            null,
            30,
            null
        )

        ownedPlantDAO.insertOwnedPlant(ownedPlant)

        val ownedPlants = ownedPlantDAO.getAllOwnedPlants()
        assert(ownedPlants.first().contains(ownedPlant))

        val ownedPlantUpdate = ownedPlants.first().first()

        ownedPlantUpdate.customWateringCycle = 10

        ownedPlantDAO.updateOwnedPlant(ownedPlantUpdate)

        assert(ownedPlants.first().first().customWateringCycle == 10)

        ownedPlantDAO.deleteOwnedPlantByID(ownedPlantUpdate.id)

        assert(!ownedPlants.first().contains(ownedPlant))

    }
    
}
