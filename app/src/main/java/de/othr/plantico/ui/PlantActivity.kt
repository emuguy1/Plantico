package de.othr.plantico.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import de.othr.plantico.R
import de.othr.plantico.TestViewModel
import de.othr.plantico.TestViewModelFactory
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.databinding.ActivityPlantBinding
import de.othr.plantico.ui.homescreen.HomescreenActivity

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
                    //binding.plantDetailHeader.text = plant.plantName
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

        binding.bottomNavigation.setOnItemSelectedListener{ menu ->

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
                    val intent = Intent(this, MainActivity::class.java)
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
