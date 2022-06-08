package de.othr.plantico.ui.search

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import de.othr.plantico.PlantViewModel
import de.othr.plantico.PlantViewModelFactory
import de.othr.plantico.R
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.database.entities.PlantCategory
import de.othr.plantico.database.entities.PlantDifficulty
import de.othr.plantico.databinding.ActivitySearchBinding
import de.othr.plantico.setupMenuBinding

class SearchableActivity : AppCompatActivity(),
    SearchCategoryDialogFragment.SearchCategoryDialogListener {

    private lateinit var binding: ActivitySearchBinding
    private val plantViewModel: PlantViewModel by viewModels {
        PlantViewModelFactory((application as PlantApplication).repository)
    }

    var adapter: SearchPlantAdapter? = null
    var allPlants: List<Plant> = ArrayList()

    // Initialise selectedCategories, so all Categories are selected by default
    var selectedCategories: List<PlantCategory> = PlantCategory.values().toList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = SearchPlantAdapter(application)
        binding.recyclerviewSearchedPlants.adapter = adapter
        binding.recyclerviewSearchedPlants.layoutManager = LinearLayoutManager(this)

        plantViewModel.allPlants.observe(this) { plants ->
            plants.let {
                // Set the plants list which should get searched
                allPlants = it
                executeSearch()
            }
        }

        //Setup Spinner for Difficulty
        val difficultySpinner: Spinner = binding.searchDifficultySpinner
        // Create Array with spinner items
        val difficultyList: ArrayList<String> = ArrayList<String>()
        difficultyList.add("All")
        difficultyList.addAll(plantDifficultiesToStringArray())
        // Create an ArrayAdapter a default spinner layout
        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            difficultyList
        ).also { difficultyAdapter ->
            // Specify the layout to use when the list of choices appears
            difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            difficultySpinner.adapter = difficultyAdapter
        }

        // Register Listener for DifficultySpinner
        difficultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                executeSearch()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        // Register Listener for SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                executeSearch()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                executeSearch()
                return false
            }
        })
        binding.bottomNavigation.setupMenuBinding(R.id.action_search, this)

        //Register Listener for Dialog Button
        binding.testButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val dialogFragment: SearchCategoryDialogFragment =
                    SearchCategoryDialogFragment(selectedCategories)
                dialogFragment.show(supportFragmentManager, "Search Category Dialog Fragment")
            }

        })
    }

    fun executeSearch() {
        val results = searchForPlantsInList()
        if (results.isEmpty()) {
            binding.recyclerviewSearchedPlants.visibility = View.GONE
            binding.noResultsFound.visibility = View.VISIBLE
        } else {
            binding.recyclerviewSearchedPlants.visibility = View.VISIBLE
            binding.noResultsFound.visibility = View.GONE
        }
        adapter!!.submitList(
            results
        )
        binding.numberResultsFound.text = getString(R.string.results_found, results.size)
    }

    //Search for plants that contain the query as a substring. Not case-sensitive!
    fun searchForPlantsInList(
    ): List<Plant> {
        val query = binding.searchView.query.toString()
        val difficulty = binding.searchDifficultySpinner.selectedItem.toString()
        // Filter for the query, difficulty and category
        return allPlants.filter { plant: Plant ->
            plant.plantName.contains(
                query,
                ignoreCase = true
            ) && //If no category is selected, it counts as all were selected
                    (if (selectedCategories.isNotEmpty()) {
                        containsAny(
                            selectedCategories,
                            plant.plantCategory
                        )
                    } else true)
                    && (if (difficulty != "All") plant.difficulty.toString()
                .lowercase() == difficulty.lowercase() else true)
        }
    }

    fun containsAny(
        selectedCategories: List<PlantCategory>,
        plantCategories: List<PlantCategory>
    ): Boolean {
        for (category in plantCategories) {
            if (selectedCategories.contains(category)) {
                return true
            }
        }
        return false
    }

    override fun onDialogPositiveClick(
        dialog: DialogFragment,
        selectedItems: List<PlantCategory>
    ) {
        // Set current Categories
        selectedCategories = selectedItems
        executeSearch()
    }

    override fun onDialogNegativeClick(
        dialog: DialogFragment,
        selectedItems: List<PlantCategory>
    ) {
        // Current Categories stay the same
    }

    private fun plantDifficultiesToStringArray(): Array<String> {
        return PlantDifficulty.values().map { plantDifficulty ->
            plantDifficulty.name.substring(0, 1).uppercase() + plantDifficulty.name.substring(1)
                .lowercase().replace('_', ' ')
        }.toTypedArray()
    }
}
