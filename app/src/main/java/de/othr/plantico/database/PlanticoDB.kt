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
import de.othr.plantico.nowUTC
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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

            plantDAO.insert(
                Plant(
                    "Silver Cockscomb",
                    "Celosia argentea L.",
                    "Amaranthaceae",
                    "Tracheophytes",
                    "Celosia",
                    "C. argentea",
                    "Celosia argentea, commonly known as the plumed cockscomb or silver cock!s comb, is a herbaceous plant of tropical origin, and is knownfor its very bright colors. In India and China it is known as a troublesome weed.\n" +
                            "As these plants are of tropical origin, they grow best in full sunlight and should be placed in a well-drained area. Full sunlight means they should get at least 8 hours of direct sunlight. For healthy growth plant them in the area where they get early morning sunlight and afternoon shade. In the afternoon the sunlight are mostly harsh especially in hot summer. Afternoon shade will save the plant from excessive heat. The flowerheads can last up to 8 weeks, and further growth can be promoted by removing dead flowers.",
                    "You can propagate wool flowers from cuttings and seeds. Start propagation from April in subtropics and slightly cooler climates. If you live in a cold place, wait till mid-may, seed sowing can be done till July and beyond if temperature requirements are satisfied. In a hot climate (USDA Zone 11-12), wait till the summer ends.\n" +
                            "\n" +
                            "From Cuttings\n" +
                            "\n" +
                            "Take a 5-6 inches long cutting from a healthy plant with at least 2-3 leaf nodes.\n" +
                            "Strip all the bottom leaves with a few remaining on top.\n" +
                            "Dip it in a rooting hormone (optional step)\n" +
                            "Place the cutting in soil. Keep it where it can get bright, indirect daylight.\n" +
                            "Once it forms roots (2-4 weeks) and begins to grow, transplant the cuttings into the desired place.\n" +
                            "From Seeds\n" +
                            "\n" +
                            "Get the seeds of your favorite variety from a garden center or online.\n" +
                            "Sow them 1/3 to 1/4 inch (0.6 to 1 cm) deep in a seed tray or pot that has a well-draining soilless mix.\n" +
                            "Keep the soil moist and place the pot in bright light.\n" +
                            "Ensure the soil temperature is above 64 F (18 C), but you can start them when the temperature is as low as 60 F (15 C).",
                    "Celosia can be planted in the garden in the spring when temperatures will remain above 60 degrees Fahrenheit. They will be damaged if the temperature drops lower than that. Immediately after planting them, water them generously to settle the soil around the roots. A 3-inch layer of organic mulch can be spread around them to control weeds and help maintain moisture levels. Water them as often as necessary to keep the soil consistently moist.",
                    PlantDifficulty.INTERMEDIATE,
                    listOf(
                        PlantCategory.OUTDOOR,
                        PlantCategory.HIGH_LIGHT,
                        PlantCategory.TROPICAL,
                        PlantCategory.FLOWERING,
                        PlantCategory.SUMMER,
                        PlantCategory.BALCONY
                    ),
                    10,
                    WateringLevel.LOW
                )
            )

            plantDAO.insert(
                Plant(
                    "Echeveria gibbiflora",
                    "Echeveria gibbiflora DC.",
                    "Crassulaceae",
                    "Eudicots",
                    "Echeveria",
                    "E. gibbiflora",
                    "Echeveria gibbiflora is a species of flowering plant in the family Crassulaceae. It was described by Swiss botanist Augustin Pyramus de Candolle in 1828. It occurs in Mexico and Guatemala\n" +
                            "Echeveria gibbiflora is a large species of Echeveria, producing rosettes of 15 leaves, a tall flowering stem up to 1 metre (3 ft 3 in) in height, and an average of 160 flower buds. The red, tubular flowers are about 2.5 centimetres (0.98 in) long with 10 stamens and 5 styles. It flowers between September and January. The dry fruits each produce approximately 200 small seeds.",
                    "An excellent way to promote the growth of your Echeveria gibbiflora is by propagating new plants from cuttings.\n" +
                            "\n" +
                            "For Echeveria propagation, you will need a cutting that has at least one healthy leaf on it and then insert it into some potting soil after allowing them to callus for a few days.\n" +
                            "\n" +
                            "Ensure not to use too much water when watering because they can rot quickly if submerged for an extended period.\n" +
                            "\n" +
                            "Place them somewhere where they receive bright light but indirect sun exposure as well – these are perfect conditions for rooting succulent cuttings!\n" +
                            "\n" +
                            "Make sure to provide them with plenty of water and fertilizer every couple of weeks.\n" +
                            "\n" +
                            "Keep at it and be patient. You will soon have more Echeveria gibbiflora plants!\n" +
                            "\n" +
                            "The best time to propagate is during the spring or fall season, while the inactive growth stage usually starts mid-spring until fall.\n" +
                            "\n" +
                            "This way, cuttings can take root well since there’s still enough light exposure for photosynthesis.\n" +
                            "\n" +
                            "It’s not recommended to propagate succulents when they are in dormancy because it takes a long time for them to recover from the stressful process of rooting and growing new leaves.",
                    "he Echeveria gibbiflora succulent does not need watering as often as other plants.\n" +
                            "\n" +
                            "The plant should be watered about once every two weeks. It is recommended to use distilled water or rainwater without any additives such as chlorine.\n" +
                            "\n" +
                            "If the soil’s surface stays wet for an extended period, then this may lead to root rot in your succulents.\n" +
                            "\n" +
                            "You must take care when choosing what type of potting mix you are using so that it will retain moisture enough on its own without needing constant watering from you!\n" +
                            "\n" +
                            "You can tell if your Echeveria needs more water because the leaves will start turning brown at their tips, indicating dehydration.\n" +
                            "\n" +
                            "You should also try checking by gently squeezing the leaves in your fingers. If it’s dry and does not leave any indent, then you need to water more often because that plant needs more than just once a week!\n" +
                            "\n" +
                            "If there is too much water on the soil surface, or if the potting mix drains well already, then it should be watered about every two weeks, as mentioned above.",
                    PlantDifficulty.EASY,
                    listOf(
                        PlantCategory.OUTDOOR,
                        PlantCategory.INDOOR,
                        PlantCategory.EVERGREEN,
                        PlantCategory.BALCONY,
                        PlantCategory.DURABLE,
                        PlantCategory.LOW_MAINTENANCE
                    ),
                    30,
                    WateringLevel.LOW
                )
            )

            plantDAO.insert(
                Plant(
                    "Dragontree",
                    "Dracaena reflexa Lam.",
                    "Asparagaceae",
                    "Nolinoideae",
                    "Dracaena",
                    "D. reflexa",
                    "Dracaena reflexa (commonly called song of India or song of Jamaica) is a tree native to Mozambique, Madagascar, Mauritius, and other nearby islands of the Indian Ocean. It is widely grown as an ornamental plant and houseplant, valued for its richly coloured, evergreen leaves, and thick, irregular stems.\n" +
                            "While it may reach a height of 4–5 m, rarely 6 m in ideal, protected locations, D. reflexa is usually much smaller, especially when grown as a houseplant. It is slow-growing and upright in habit, tending to an oval shape with an open crown. The lanceolate leaves are simple, spirally arranged," +
                            " 5–20 cm long and 1.5–5 cm broad at the base, with a parallel venation and entire margin; they grow in tight whorls and are a uniform dark green.\n" +
                            "\n" +
                            "The flowers are small, clustered, usually white and extremely fragrant, appearing in mid winter. Neither the flowers nor the fruit are especially showy. D. reflexa var. angustifolia (syn. D. marginata) differs in having a magenta tint to its flowers, a shrubby habit, and olive green leaves.",
                    "Dracaena houseplants are great candidates for a propagation method called air layering. This basically involves taking a cutting, but before you do so, you trick the plant into developing a root system on the stem in question. A great way to avoid having to be nervous about whether your cutting will take or not!\n" +
                            "\n" +
                            "Propagating Dracaena through air layering is super easy and all you need is a knife, some plastic wrap and some sphagnum moss (which you can buy as ‘orchid moss‘). Some rooting hormone also comes in handy.\n" +
                            "\n" +
                            "Select a spot on the stem that corresponds with the length you want your new plant to be.\n" +
                            "Sterilize your knife with some alcohol and carefully scrape away a layer of bark on the plant’s stem. The exposed band can be about half an inch (1.2 cm) wide and go around the whole stem. You basically want to create a wound.\n" +
                            "If you have rooting hormone, this is where you dust it onto the mark you just created. It’s not a must, but it can speed things up.\n" +
                            "Wet your sphagnum moss and wrap it around the wounded part of the stem. Cover it with plastic wrap and secure it in place to make your Dracaena think it has been planted in soil and should start producing roots.\n" +
                            "Be patient. Once you see new roots inside the plastic wrap, you can remove the construction and cut the stem just below the rooted part. You now have a brand new and already rooted Dracaena that you can pop right into a fresh planter. Yay!\n" +
                            "As mentioned before, the beheaded stem will soon start sprouting new leaves. In fact, it’ll generally produce more ‘heads’ than it had before.",
                    "Part of what makes Dracaena so popular indoors is that they’re not all too demanding when it comes to watering. In fact, these plants like it when you let the soil almost dry out between waterings. As such, they’re pretty forgiving if you miss a watering or two (which, let’s admit it, we’re all guilty of from time to time).\n" +
                            "\n" +
                            "Once you notice that the soil has gone dry, you just need to water the plant thoroughly and you’re good to go for another good while. Be sure to remove any excess water from the saucer underneath your Dracaena to avoid soggy soil.",
                    PlantDifficulty.INTERMEDIATE,
                    listOf(
                        PlantCategory.INDOOR,
                        PlantCategory.HIGH_LIGHT,
                        PlantCategory.EVERGREEN,
                        PlantCategory.NON_HARDY
                    ),
                    10,
                    WateringLevel.LOW
                )
            )

            plantDAO.insert(
                Plant(
                    "Garden petunia",
                    "Petunia axillaris Lam.",
                    "Solanaceae",
                    "Tracheophytes",
                    "Petunia",
                    "P. axillaris",
                    "Petunia axillaris, the large white petunia, wild white petunia or white moon petunia, is an annual herbaceous plant in the family Solanaceae, genus Petunia. It is native to temperate South America. The plant's flowers, the only white ones found in the Petunia genus, are 3 to 7 cm long. The commonly-grown garden petunia is a hybrid of P. axillaris and P. integrifolia.",
                    "Sowing Seed and Encouraging Germination\n" +
                            "The tiny petunia seeds require light for germination, so they should be sown on the surface of the germinating media and left uncovered. Gently pressing the petunia seeds down with a block of wood or other blunt object ensures that the seeds are solidly in contact with the media. A thorough, gentle misting at the time of sowing and as needed until germination so that the media never dries out is necessary. Covering the container with plastic or glass maintains high humidity. The optimum germination temperature range for petunia seeds is 70 to 80 degrees Fahrenheit. Petunia seeds germinate within eight to 12 days.\n" +
                            "\n" +
                            "Seedling Care and Transplant\n" +
                            "After seedlings emerge, any covering to maintain humidity around the seeds should be removed and the container moved to an area with higher light and a temperature around 60 to 65 degrees Fahrenheit. Once the petunia seedlings develop three true leaves, they are ready for transplant into larger containers. Watering frequency is decreased so that the surface of the soil in the container dries between waterings. Petunias are fairly heavy feeders and benefit from the application of a diluted fertilizer every two weeks.\n" +
                            "\n" +
                            "Taking and Preparing Cuttings\n" +
                            "Petunias guaranteed to have the desirable traits of the parent plant are grown by successfully rooting cuttings taken from the parent plant. A section of petunia growth about 3 inches long is cut from the tip of a stem on the parent plant with a sharp sterile knife. Leaves are removed from the bottom half of the cutting, the end of the cutting is dipped in a rooting hormone and the cutting is inserted into a prepared rooting media so that the lowest remaining leaves are just above the surface of the media.\n" +
                            "\n" +
                            "Rooting Cuttings\n" +
                            "A suitable rooting media for petunia could contain equal parts peat moss and sand or perlite. Bright but indirect light, high humidity, consistently and evenly moist media and a temperature maintained around 75 and 80 degrees Fahrenheit encourage roots. Once roots an inch long form, the petunias are ready for transplant into containers with nutrient-rich soil.",
                    "Petunias are fairly heat tolerant, so you shouldn’t have to worry about watering them frequently. A thorough watering once a week should be sufficient (unless there are prolonged periods of drought in your area). Avoid watering shallowly, as this encourages shallow roots.\n" +
                            "Note: The spreading types of petunias and those in containers will require more frequent watering than those planted in the ground.",
                    PlantDifficulty.INTERMEDIATE,
                    listOf(
                        PlantCategory.NON_HARDY,
                        PlantCategory.BALCONY,
                        PlantCategory.OUTDOOR,
                        PlantCategory.HIGH_LIGHT
                    ),
                    2,
                    WateringLevel.MEDIUM
                )
            )

            plantDAO.insert(
                Plant(
                    "Calla Lily",
                    "Zantedeschia aethiopica",
                    "Araceae",
                    "Alismatales",
                    "Zantedeschia",
                    "Z. aethiopica",
                    "Zantedeschia aethiopica is a rhizomatous herbaceous perennial plant, evergreen where rainfall and temperatures are adequate, deciduous where there is a dry season. Its preferred habitat is in streams and ponds or on the banks. It grows to 0.6–1 m (2.0–3.3 ft) tall, with large clumps of broad, arrow shaped dark green leaves up to 45 cm (18 in) long. The inflorescences are large and are produced in spring, summer and autumn, with a pure white spathe up to 25 cm (9.8 in) and a yellow spadix up to 90 mm (3+1⁄2 in) long. The spadix produces a faint, sweet fragrance.\n" +
                            "\n" +
                            "Zantedeschia aethiopica contains calcium oxalate, and ingestion of the raw plant may cause a severe burning sensation and swelling of lips, tongue, and throat; stomach pain and diarrhea may occur.",
                    "Divide plants every 3 to 4 years as new growth begins in the spring. Just lift the plants and divide the clumps. Replant the new bulbs adding some compost.\n " +
                            "Apply a high-potassium liquid fertilizer every 2 weeks from planting until 6 weeks after flowering.\n" +
                            "Apply a thin layer of compost each spring, followed by a 2-inch layer of mulch.\n" +
                            "Stake tall lilies.\n" +
                            "Lilies do not bloom more than once per season, but you can remove the faded flowers so that the plants don’t waste energy making seeds.\n" +
                            "After the lily blooms, you can also remove just the stem itself. However, do NOT remove leaves until they have died down and turned brown in fall. It’s very important not to cut back the leaves until the end of their season because hey help provide nourishment to the bulb for next season’s blooms.\n" +
                            "Cut down the dead stalks in the late fall or early spring.\n" +
                            "Before winter, add 4 to 6 inches of mulch, simply to delay the ground freeze and allow the roots to keep growing. Leave the mulch until spring once the last hard frost has passed. See your local frost dates. See your local frost dates.\n" +
                            "If your region doesn’t have snow cover, keep soil moist in winter.\n" +
                            "When lily shoots grow through the mulch in the spring, start to remove it gradually.",
                    "During active growth, water freely—especially if rainfall is less than 1 inch per week.",
                    PlantDifficulty.ADVANCED,
                    listOf(
                        PlantCategory.SUMMER,
                        PlantCategory.NON_HARDY,
                        PlantCategory.OUTDOOR,
                        PlantCategory.FLOWERING
                    ),
                    4,
                    WateringLevel.HIGH
                )
            )

            //Would have to be removed before publication
            ownedPlantDAO.insertOwnedPlant(
                OwnedPlant(
                    "My first cactus!",
                    1,
                    Date().nowUTC(),
                    null,
                    30,
                    null
                )
            )

            ownedPlantDAO.insertOwnedPlant(
                OwnedPlant(
                    "Monsti Monstera",
                    2,
                    Date().nowUTC(),
                    Date().nowUTC(),
                    7,
                    "living room"
                )
            )
            ownedPlantDAO.insertOwnedPlant(
                OwnedPlant(
                    "Mahaloa",
                    3,
                    Date().nowUTC(),
                    Date().nowUTC(),
                    16,
                    "living room"
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
