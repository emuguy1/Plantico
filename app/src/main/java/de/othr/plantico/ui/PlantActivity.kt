package de.othr.plantico.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import de.othr.plantico.PlantViewModel
import de.othr.plantico.PlantViewModelFactory
import de.othr.plantico.R
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.databinding.ActivityPlantBinding
import de.othr.plantico.ui.homescreen.HomescreenActivity
import de.othr.plantico.ui.search.SearchableActivity

class PlantActivity : AppCompatActivity() {
    private val plantViewModel: PlantViewModel by viewModels {
        PlantViewModelFactory((application as PlantApplication).repository)
    }
    private val plantList = ArrayList<Plant>()
    private var plantID = 0
    private lateinit var binding: ActivityPlantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        plantViewModel.allPlants.observe(this) { plants ->
            // Update the cached copy of the words in the adapter.
            plants.let {
                plantList.clear()
                plantList.addAll(it)
                val plant = plantList.find { plant -> plant.id == plantID }
                if (plant != null) {
                    binding.plantDetailHeader.text = plant.plantName
                }
            }
        }
        //testViewModel.
        //plantHeader.setText("lol")
        val bundle = savedInstanceState ?: intent.extras
        if (bundle != null) {
            plantID = bundle.getInt(SELECTED_PLANT)

        }
        binding.bottomNavigation.selectedItemId = R.id.action_plant

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

    companion object {
        const val SELECTED_PLANT = "selected_plant"

    }
}
