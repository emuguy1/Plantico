package de.othr.plantico.ui.plantDetails

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import de.othr.plantico.PlantViewModel
import de.othr.plantico.PlantViewModelFactory
import de.othr.plantico.R
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.databinding.ActivityHomescreenBinding
import de.othr.plantico.databinding.ActivityPlantDetailPageBinding
import de.othr.plantico.ui.PlantActivity
import de.othr.plantico.ui.homescreen.HomescreenActivity
import de.othr.plantico.ui.homescreen.PlantAdapter
import de.othr.plantico.ui.homescreen.WateringAdapter
import de.othr.plantico.ui.search.SearchableActivity

class PlantDetailActivity() : AppCompatActivity() {


    private var plantID = 0
    private val plantList = ArrayList<Plant>()
    private lateinit var binding: ActivityPlantDetailPageBinding
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
            plantID = bundle.getInt(PlantActivity.SELECTED_PLANT)

        }

        binding.categoriesItemList.setHasFixedSize(true)
        val layout = FlexboxLayoutManager(
            this
        )
        layout.flexDirection = FlexDirection.ROW
        layout.justifyContent = JustifyContent.FLEX_START
        binding.categoriesItemList.layoutManager = layout
        val adapter = CategorieListAdapter(
            this
        )
        binding.categoriesItemList.adapter = adapter

        var plant: Plant? = null
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
                        //1 equals PlantDifficulty.INTERMEDIATE
                        if (plant!!.difficulty.ordinal >= 1) {
                            plantDifficultyMedium.visibility = View.VISIBLE
                        }
                        //2 equals PlantDifficulty.ADVANCED
                        if (plant!!.difficulty.ordinal >= 2) {
                            plantDifficultyHard.visibility = View.VISIBLE
                        }
                        //Watering
                        if (plant!!.wateringLevel.ordinal < 2) {
                            wateringHeavy.setImageDrawable(
                                ContextCompat.getDrawable(
                                    application,
                                    R.drawable.ic_waterdropplett_empty_icon
                                )
                            )
                        }
                        if (plant!!.wateringLevel.ordinal < 1) {
                            wateringMedium.setImageDrawable(
                                ContextCompat.getDrawable(
                                    application,
                                    R.drawable.ic_waterdropplett_empty_icon
                                )
                            )
                        }
                    }
                }
            }
        }
        setupMenuBinding()
    }

    fun setupMenuBinding() {
        binding.bottomNavigation.selectedItemId = R.id.action_home
        binding.bottomNavigation.setOnItemSelectedListener { menu ->

            when (menu.itemId) {

                R.id.action_home -> {
                    val intent = Intent(this, HomescreenActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.action_plant -> {
                    val intent = Intent(this, PlantActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.action_search -> {
                    val intent = Intent(this, SearchableActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.action_user -> {

                    true
                }

                else -> false
            }
        }
    }
}