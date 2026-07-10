package de.othr.plantico.ui.ownedPlantList

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import de.othr.plantico.PlantViewModel
import de.othr.plantico.PlantViewModelFactory
import de.othr.plantico.R
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.databinding.ActivityOwnPlantBinding
import de.othr.plantico.setupMenuBinding
import de.othr.plantico.ui.homescreen.WateringAdapter


class ActivityOwnedPlants : AppCompatActivity() {
    private val plantViewModel: PlantViewModel by viewModels {
        PlantViewModelFactory((application as PlantApplication).repository)
    }

    private lateinit var binding: ActivityOwnPlantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnPlantBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = OwnedPlantListAdapter(this)
        binding.plantList.adapter = adapter
        binding.plantList.layoutManager = LinearLayoutManager(this)


        plantViewModel.getAllOwnedPlantSortedByPlantLocation().observe(this) { plants ->
            // Update the cached copy of the words in the adapter.
            plants.let {
                adapter.submitList(it)
            }
        }

        plantViewModel.allPlants.observe(this) { plants ->
            plants.let {
                adapter.setPlantList(it)
            }
        }

        binding.bottomNavigation.setupMenuBinding(R.id.action_plant,this)

    }

}
