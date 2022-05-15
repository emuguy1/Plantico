package de.othr.plantico.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import de.othr.plantico.TestViewModel
import de.othr.plantico.TestViewModelFactory
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.databinding.ActivityPlantBinding

class PlantActivity : AppCompatActivity() {
    private val testViewModel: TestViewModel by viewModels {
        TestViewModelFactory((application as PlantApplication).repository)
    }
    private val plantList = ArrayList<Plant>()
    private var plantID = 0
    private lateinit var binding: ActivityPlantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        testViewModel.allPlants.observe(this) { plants ->
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
    }

    companion object {
        const val SELECTED_PLANT = "selected_plant"

    }
}
