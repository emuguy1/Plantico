package de.othr.plantico.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import de.othr.plantico.PlantViewModel
import de.othr.plantico.PlantViewModelFactory
import de.othr.plantico.R
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.databinding.ActivityPlantBinding
import de.othr.plantico.setupMenuBinding

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
        binding.bottomNavigation.setupMenuBinding(R.id.action_plant,this)

    }

    companion object {
        const val SELECTED_PLANT = "selected_plant"

    }
}
