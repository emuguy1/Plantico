package de.othr.plantico.ui.ownedPlant

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import de.othr.plantico.*
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.database.entities.OwnedPlant
import de.othr.plantico.database.entities.PlantDifficulty
import de.othr.plantico.database.entities.WateringLevel
import de.othr.plantico.databinding.ActivityOwnedPlantDetailBinding
import de.othr.plantico.ui.plantDetails.CategoryListAdapter
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


class OwnedPlantActivity : AppCompatActivity() {
    private val plantViewModel: PlantViewModel by viewModels {
        PlantViewModelFactory((application as PlantApplication).repository)
    }
    private lateinit var binding: ActivityOwnedPlantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnedPlantDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.categoriesItemList.setHasFixedSize(true)
        val layout = FlexboxLayoutManager(
            this
        )
        layout.flexDirection = FlexDirection.ROW
        layout.justifyContent = JustifyContent.FLEX_START
        binding.categoriesItemList.layoutManager = layout
        val adapter = CategoryListAdapter(
            this
        )
        binding.categoriesItemList.adapter = adapter


        val bundle = savedInstanceState ?: intent.extras
        if (bundle != null) {
            val ownPlantID = bundle.getInt(SELECTED_OWN_PLANT)
            val plantID = bundle.getInt(SELECTED_PLANT)

            plantViewModel.getPlantByID(plantID).observe(this) { plant ->
                binding.apply {
                    plantName.text = plant.plantName
                    plantFamilyText.text = plant.family
                    plantSubfamilyText.text = plant.subfamily
                    plantTribeText.text = plant.plantNameLat
                    plantGenusText.text = plant.genus
                    plantSpeciesText.text = plant.typeSpecies
                    adapter.setList(plant.plantCategory)
                    plantGeneralDescriptionText.text = plant.desc
                    plantPropogationsInstructionsText.text = plant.propagationDesc
                    plantWateringInstructionsText.text = plant.wateringDesc

                    //Difficulty
                    val invisible = View.INVISIBLE
                    val visible = View.VISIBLE
                    when (plant.difficulty) {
                        PlantDifficulty.EASY -> {
                            plantDifficultyEasy.visibility = visible
                            plantDifficultyMedium.visibility = invisible
                            plantDifficultyHard.visibility = invisible
                        }
                        PlantDifficulty.INTERMEDIATE -> {
                            plantDifficultyEasy.visibility = visible
                            plantDifficultyMedium.visibility = visible
                            plantDifficultyHard.visibility = invisible
                        }
                        PlantDifficulty.ADVANCED -> {
                            plantDifficultyEasy.visibility = visible
                            plantDifficultyMedium.visibility = visible
                            plantDifficultyHard.visibility = visible
                        }
                    }

                    //Watering
                    val waterdropplettFull = ContextCompat
                        .getDrawable(
                            application,
                            R.drawable.ic_waterdropplett_full_icon
                        )
                    val waterdropplettEmpty = ContextCompat
                        .getDrawable(
                            application,
                            R.drawable.ic_waterdropplett_empty_icon
                        )

                    when (plant.wateringLevel) {
                        WateringLevel.LOW -> {
                            wateringLight.setImageDrawable(waterdropplettFull)
                            wateringMedium.setImageDrawable(waterdropplettEmpty)
                            wateringHeavy.setImageDrawable(waterdropplettEmpty)
                        }
                        WateringLevel.MEDIUM -> {
                            wateringLight.setImageDrawable(waterdropplettFull)
                            wateringMedium.setImageDrawable(waterdropplettFull)
                            wateringHeavy.setImageDrawable(waterdropplettEmpty)
                        }
                        WateringLevel.HIGH -> {
                            wateringLight.setImageDrawable(waterdropplettFull)
                            wateringMedium.setImageDrawable(waterdropplettFull)
                            wateringHeavy.setImageDrawable(waterdropplettFull)
                        }
                    }
                }


            }

            var ownedPlant: OwnedPlant? = null

            plantViewModel.getOwnedPlantByID(ownPlantID).observe(this) { oPlant ->
                ownedPlant = oPlant
                binding.apply {
                    plantAgeText.text = oPlant.birthday?.toDateString() ?: "No birthday available."
                    plantLastWateredText.text =
                        oPlant.lastWatered?.toDateString() ?: "No watering info available."
                    ownedPlantName.text = oPlant.plantName
                    plantLocationText.text = oPlant.location

                    plantWateringCycleText.text = oPlant.customWateringCycle.toString() + " Days"
                }


            }

            binding.addWateringButton.setOnClickListener {
                ownedPlant?.let {
                    it.lastWatered =
                        Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
                    plantViewModel.updateOwnedPlant(it)
                }
            }





            binding.bottomNavigation.setupMenuBinding(R.id.action_plant, this)

        }
    }


    companion object {
        const val SELECTED_PLANT = "selected_plant"
        const val SELECTED_OWN_PLANT = "selected_own_plant"

    }
}



