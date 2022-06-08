package de.othr.plantico.ui.plantDetails

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import de.othr.plantico.PlantViewModel
import de.othr.plantico.PlantViewModelFactory
import de.othr.plantico.R
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.database.entities.PlantDifficulty
import de.othr.plantico.database.entities.WateringLevel
import de.othr.plantico.databinding.ActivityPlantDetailPageBinding
import de.othr.plantico.ownedPlant.AddOwnedPlantActivity
import de.othr.plantico.ownedPlant.OwnedPlantActivity
import de.othr.plantico.setupMenuBinding

class PlantDetailActivity : AppCompatActivity() {


    private var plantID = 0
    private val plantList = ArrayList<Plant>()
    private lateinit var binding: ActivityPlantDetailPageBinding
    private var plant: Plant? = null

    private val plantViewModel: PlantViewModel by viewModels {
        PlantViewModelFactory((application as PlantApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantDetailPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mActionBarToolbar = binding.toolbarPlantView
        mActionBarToolbar.title = ""
        setSupportActionBar(mActionBarToolbar)

        val bundle = savedInstanceState ?: intent.extras
        if (bundle != null) {
            plantID = bundle.getInt(OwnedPlantActivity.SELECTED_PLANT)

        }

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

        plantViewModel.allPlants.observe(this) { plants ->
            // Update the cached copy of the words in the adapter.
            plants.let {
                plantList.clear()
                plantList.addAll(it)
                plant = plantList.find { plant -> plant.id == plantID }!!
                if (plant != null) {
                    binding.apply {
                        plantName.text = plant!!.plantName
                        plantFamilyText.text = plant!!.family
                        plantSubfamilyText.text = plant!!.subfamily
                        plantTribeText.text = plant!!.plantNameLat
                        plantGenusText.text = plant!!.genus
                        plantSpeciesText.text = plant!!.typeSpecies
                        adapter.setList(plant!!.plantCategory)
                        plantWateringCycleText.text = plant!!.wateringCycleDays.toString() + " Days"
                        plantGeneralDescriptionText.text = plant!!.desc
                        plantPropogationsInstructionsText.text = plant!!.propagationDesc
                        plantWateringInstructionsText.text = plant!!.wateringDesc

                        //Difficulty
                        val invisible = View.INVISIBLE
                        val visible = View.VISIBLE
                        when (plant!!.difficulty) {
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
                            .getDrawable(application, R.drawable.ic_waterdropplett_full_icon)
                        val waterdropplettEmpty = ContextCompat
                            .getDrawable(application, R.drawable.ic_waterdropplett_empty_icon)

                        when (plant!!.wateringLevel) {
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
            }
        }

        binding.addPlantButton.setOnClickListener {
            val intent = Intent(this, AddOwnedPlantActivity::class.java).putExtra(
                SELECTED_ADD_PLANT,
                plant!!.id
            )
            startActivity(intent)
        }
        binding.bottomNavigation.setupMenuBinding(R.id.action_home, this)


    }

    companion object {
        const val SELECTED_ADD_PLANT = "selected_add_plant"

    }

}
