package de.othr.plantico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val testViewModel: TestViewModel by viewModels {
        TestViewModelFactory((application as PlantApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = PlantListAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        testViewModel.allPlants.observe(this) { plants ->
            // Update the cached copy of the words in the adapter.
            plants.let { adapter.submitList(it) }
        }
    }
}
