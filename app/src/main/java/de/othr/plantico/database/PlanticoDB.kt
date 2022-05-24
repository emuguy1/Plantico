package de.othr.plantico.database

import android.content.Context
import android.os.Build
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
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

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
                    "The plants do have some basic requirements; an average minimum winter temperature of 12 °C (53.6 °F); and good drainage with less watering in winter. Excess water in cool periods may lead to rot. Golden Barrels are hardy to about −8 °C (15 °F) for brief periods.",
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
                    "Monstera Deliciosa",
                    "Araceae",
                    "-",
                    "Monstera",
                    "M. deliciosa",
                    "Monstera deliciosa, the Swiss cheese plant or split-leaf philodendron" +
                            " is a species of flowering plant native to tropical forests of southern Mexico," +
                            " south to Panama. It has been introduced to many tropical areas, and has become a " +
                            "mildly invasive species in Hawaii, Seychelles, Ascension Island and the Society Islands. " +
                            "It is very widely grown in temperate zones as a houseplant.",
                    "1. Find the Node\n" +
                            "Look your monstera over and find the node. The little nub is key and will be the ONLY way you can propagate as it turns into a root. It looks kind of like kind of like a plant pimple, and is located at a petiole intersection.\n" +
                            "\n" +
                            "2. Snip the Stem\n" +
                            "With sharp sheers snipp the stem to include the aerial root or node and leaves in one. After taking this cutting from the main plant, wash it under filtered water.\n" +
                            "\n" +
                            "3. Snip extra leaves\n" +
                            "If there were any extra leaves, this would be the time to remove them. Two to three leaves are fine, but remove any additional.\n" +
                            "\n" +
                            "4. Time for Water\n" +
                            "Clean and fill a glass jar (no lid needed) halfway with room temperature, filtered water (chlorine is not good eats for young plants)\n" +
                            "\n" +
                            "5. Position the Cutting\n" +
                            "Carefully position the cutting so it remains upright. You can always try a rig with twist ties, get a taller jar or lean the plant on the jar lip to keep it centered.",
                    "When to Water a Monstera:\n" +
                            "Monsteras like “moderate” watering. In the houseplant world, moderate watering means you should water when the soil feels moist one or two inches below the surface.\n" +
                            "\n" +
                            "How can you test this?\n" +
                            "There are three ways:\n" +
                            "\n" +
                            "1.Finger test: Gently poke your finger into the soil until you’re about two knuckles deep. If the soil still feels moist that far down, you can hold off on watering. If it’s dry, it might be time to give your monstera a drink!\n" +
                            "\n" +
                            "2.Poke a stick method: Insert a wooden stick like a chopstick or dowel into the soil. If it comes out clean, the soil is dry and it’s time to water. If it comes out damp with bits of dark soil stuck to it, your monstera is still on the moist side.\n" +
                            "\n" +
                            "3.Moisture meter: This is a more advanced method, but it can really give you a peek at how much water your monstera’s roots are holding onto. You can buy one of these online or at stores. Most of them will be color-coded to let you know when the reading is dry or wet.\n" +
                            "\n" +
                            "To use a moisture meter, simply insert the sensor into the soil about halfway between the base of the plant and the side of the pot and about halfway down into the pot. When the meter reads at about a 3, the soil is drying out and ready for water.",
                    PlantDifficulty.INTERMEDIATE,
                    listOf(
                        PlantCategory.INDOOR,
                        PlantCategory.HIGH_LIGHT,
                        PlantCategory.BIG_LEAFS,
                        PlantCategory.ALL_YEAR
                    ),
                    14,
                    WateringLevel.MEDIUM
                )
            )

            plantDAO.insert(
                Plant(
                    "Aloe Vera",
                    "Aloe vera",
                    "Asphodelaceae",
                    "Asphodeloideae",
                    "Aloe",
                    "A. vera",
                    "Aloe vera is a succulent plant species of the genus Aloe. Having some 500 species, Aloe is widely distributed, and is considered an invasive species in many world regions.",
                    "1.Watch for offshoots around the parent plant.\n" +
                            "The easiest way to propagate aloe is to remove baby aloe offsets from a parent plant. These offsets, also known as pups or offshoots, grow out of the soil around the base of the plant.\n" +
                            "2.Remove your aloe plant from its pot.\n" +
                            "Once you see offshoots growing up to four inches high around the base of your aloe plant, carefully remove the entire plant from its pot. Shake the plant lightly or use gloved fingers to gently remove existing potting soil. The best time to repot an aloe plant is during its peak growing season in late spring or early summer.\n" +
                            "3.Divide your aloe plant.\n" +
                            "Check the base of the plant for offshoots that have formed their own root systems. If possible, pull the offshoots off the main plant while keeping their new root systems intact. For offshoots that are strongly attached to their parent plant, use a clean knife to carefully remove them.\n" +
                            "4.Let your aloe plants heal.\n" +
                            "Leave the parent plant and the offshoots out of the soil in a dry, temperate place for at least twenty-four hours. Healthy aloe plants form calluses over wounds to speed up recovery time.\n" +
                            "5.Repot your aloe plants.\n" +
                            "Once you see calluses forming on your aloe plants, repot them in new pots—ideally clay pots with drainage holes. For damaged offshoots with weak root systems, apply a small amount of rooting hormone before repotting. Use a well-draining soil mix—like a mix designed specifically for cacti. Popular materials for cactus potting mix include coarse sand, perlite, pumice, crushed granite, gravel, and regular garden soil.\n" +
                            "6.Care for your new aloe plants.\n" +
                            "New aloe plants thrive in dry places with indirect light. Place your potted offshoots near a south-facing window away from direct sunlight. Avoid watering your new aloe plants until their roots have established themselves—typically one to two weeks after planting. Overwatering can result in root rot.\n" +
                            "7.Alternatively, propagate with leaf cuttings.\n" +
                            "Propagating aloe with aloe leaf cuttings is less effective than the traditional method, but it can come in handy when you’d rather not repot your parent plant. Use a clean, sharp knife to remove a leaf near the base of the plant and place the leaf in its own pot with cactus potting soil. Chances are high that the leaf will rot before developing roots, but this method can work on occasion.\n" +
                            "8.Alternatively, sow aloe from seed.\n" +
                            "Another way to grow new aloe plants is to harvest the seeds from pods on mature aloe plants. Germinate the seeds in warm, moist soil until they begin to sprout. Once established, move the new growth to a pot filled with cactus soil.",
                    "Aloe plants are succulents which are mostly considered drought tolerant plants. However, they do need water, just like any other plant, but what are aloe water needs? Aloe succulents are healthier and have the best appearance when they are kept lightly moist.",
                    PlantDifficulty.EASY,
                    listOf(
                        PlantCategory.DURABLE,
                        PlantCategory.OUTDOOR,
                        PlantCategory.SMALL_LEAFS,
                        PlantCategory.WINTER,
                        PlantCategory.FLOWERING,
                        PlantCategory.EVERGREEN
                    ),
                    14,
                    WateringLevel.LOW
                )
            )
            plantDAO.insert(
                Plant(
                    "Umbrella Plant",
                    "Cyperus alternifolius",
                    "Cyperaceae",
                    "-",
                    "Cyperus",
                    "C. alternifolius",
                    "They are annual or perennial plants, mostly aquatic and growing in still or slow-moving water up to 0.5 m deep. The species vary greatly in size, with small species only 5 cm tall, while others can reach 5 m in height. Common names include papyrus sedges, flatsedges, nutsedges, umbrella-sedges and galingales. The stems are circular in cross-section in some, triangular in others, usually leafless for most of their length, with the slender grass-like leaves at the base of the plant, and in a whorl at the apex of the flowering stems. The flowers are greenish and wind-pollinated; they are produced in clusters among the apical leaves. The seed is a small nutlet.",
                    " It is planted in gardens in the ground, pots, in ponds, and as a house plant. It is not hardy, and requires protection when temperatures fall below 5 °C (41 °F). It is propagated by dividing the roots and requires copious amounts of water.",
                    "This plant grow towards the light so rotate it around every month for even growth. Water every 7-14 days or as the soil becomes dry. This plant is flexible about watering but will be more tolerant of dry soil than overwatering. Yellow leaves are a sign of overwatering and drooping leaves are a sign it needs more water.",
                    PlantDifficulty.EASY,
                    listOf(
                        PlantCategory.DURABLE,
                        PlantCategory.INDOOR,
                        PlantCategory.OUTDOOR,
                        PlantCategory.HIGH_LIGHT,
                        PlantCategory.SEMI_TROPICAL
                    ),
                    7,
                    WateringLevel.HIGH
                )
            )
            plantDAO.insert(
                Plant(
                    "Sawfly Orchid",
                    "Ophrys tenthredinifera",
                    "Orchidaceae",
                    "Orchidoideae",
                    "Ophrys",
                    "O. tenthredinifera",
                    "The sawfly orchid, is a terrestrial species of orchid native to the Mediterranean region from Portugal and Morocco to Turkey. The common name refers to a purported resemblance between the flower and the sawfly, a wasp-like insect.",
                    "Aerial roots are long roots that grow off the main stem of a monopodial orchid.\n" +
                            "\n" +
                            "These roots can be carefully trimmed from the main stem, and then planted in a new pot. The roots will anchor themselves into the soil and, eventually, a new plant will sprout.",
                    "The easiest way is to soak your orchid in a bowl of water once every week or two --- when the moss dries out. Unlike most houseplants, you don’t need to keep orchid moss evenly moist; if it stays too moist, the orchid can rot. \n" +
                            "\n" +
                            "You can also water your orchid like a traditional houseplant – just apply a splash of water to the moss once every seven to 10 days. If the moss feels moist, you can wait a few days and check again before watering. \n" +
                            "\n" +
                            "Happily, orchids are surprisingly resilient when it comes to drying out when they’re not in bloom. If you’re on vacation or get busy, you can often get by three or maybe even four weeks, depending on the conditions, without your orchid dying. One sign to watch for that your orchids are really thirsty is when the leaves start to look crinkled.",
                    PlantDifficulty.ADVANCED,
                    listOf(
                        PlantCategory.INDOOR,
                        PlantCategory.HIGH_LIGHT,
                        PlantCategory.TROPICAL,
                        PlantCategory.FLOWERING
                    ),
                    20,
                    WateringLevel.LOW
                )
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ownedPlantDAO.insertOwnedPlant(
                    OwnedPlant(
                        "My first cactus!",
                        1,
                        Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)),
                        null,
                        null,
                        null
                    )
                )
            }
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
