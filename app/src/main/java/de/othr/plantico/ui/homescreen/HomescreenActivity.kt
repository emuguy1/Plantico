package de.othr.plantico.ui.homescreen

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import de.othr.plantico.TestViewModel
import de.othr.plantico.TestViewModelFactory
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.databinding.ActivityHomescreenBinding
import de.othr.plantico.ui.PlantListAdapter


class HomescreenActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHomescreenBinding
    private val testViewModel: TestViewModel by viewModels {
        TestViewModelFactory((application as PlantApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomescreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mActionBarToolbar= binding.toolbarHomescreen
        mActionBarToolbar.title=""
        setSupportActionBar(mActionBarToolbar)

        val adapter = PlantAdapter()
        val wateringPlantAdapter = WateringAdapter()
        binding.recyclerviewLastVisitedPlants.adapter = adapter
        binding.recyclerviewLastVisitedPlants.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewUpcomingWatering.adapter = wateringPlantAdapter
        binding.recyclerviewUpcomingWatering.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        testViewModel.allPlants.observe(this) { plants ->
            // Update the cached copy of the words in the adapter.
            plants.let {
                adapter.submitList(it)
                wateringPlantAdapter.submitList(it)
                if(it.isNotEmpty()){
                    binding.recyclerviewLastVisitedPlants.visibility= View.VISIBLE
                    binding.recyclerviewUpcomingWatering.visibility= View.VISIBLE
                    binding.noDataSearch.visibility= View.GONE
                    binding.noDataWatering.visibility= View.GONE
                }
            }
        }
    }

}