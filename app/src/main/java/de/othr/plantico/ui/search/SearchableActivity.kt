package de.othr.plantico.ui.search

import android.os.Bundle
import android.view.View
import android.widget.ListAdapter
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import de.othr.plantico.PlantViewModel
import de.othr.plantico.PlantViewModelFactory
import de.othr.plantico.R
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.databinding.ActivitySearchBinding
import de.othr.plantico.setupMenuBinding
import de.othr.plantico.ui.homescreen.PlantAdapter

class SearchableActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val plantViewModel: PlantViewModel by viewModels {
        PlantViewModelFactory((application as PlantApplication).repository)
    }

    var adapter: ListAdapter? = null
    var allPlants: List<Plant> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = PlantAdapter()
        binding.recyclerviewSearchedPlants.adapter = adapter
        binding.recyclerviewSearchedPlants.layoutManager = LinearLayoutManager(this)

        plantViewModel.allPlants.observe(this) { plants ->
            plants.let {
                // Set the plants list which should get searched
                allPlants = it
                // Set the Initial plants list in the adapter
                // TODO: Maybe change this to frequently visited plants later on?
                adapter.submitList(it)
                binding.numberResultsFound.text = getString(R.string.results_found, it.size)
                if (it.isNotEmpty()) {
                    binding.recyclerviewSearchedPlants.visibility = View.VISIBLE
                    binding.noResultsFound.visibility = View.GONE
                }
            }
        }

        // Register Listener
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val results = searchForPlantsInList(allPlants, query)
                if (results.isEmpty()) {
                    binding.recyclerviewSearchedPlants.visibility = View.GONE
                    binding.noResultsFound.visibility = View.VISIBLE
                }
                adapter.submitList(
                    results
                )
                binding.numberResultsFound.text = getString(R.string.results_found, results.size)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val results = searchForPlantsInList(allPlants, newText)
                adapter.submitList(
                    results
                )
                binding.numberResultsFound.text = getString(R.string.results_found, results.size)
                return false
            }
        })


        binding.bottomNavigation.setupMenuBinding(R.id.action_search,this)
    }

    //TODO: Maybe change this to Filtering in database instead of accessing all plants?
    //Search for plants that contain the query as a substring. Not case-sensitive!
    fun searchForPlantsInList(plants: List<Plant>, query: String): List<Plant> {
        return plants.filter { plant: Plant -> plant.plantName.contains(query, ignoreCase = true) }
    }
}
