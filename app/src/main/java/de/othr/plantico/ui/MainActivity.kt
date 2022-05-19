package de.othr.plantico.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import de.othr.plantico.R
import de.othr.plantico.TestViewModel
import de.othr.plantico.TestViewModelFactory
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.databinding.ActivityMainBinding
import de.othr.plantico.ui.homescreen.HomescreenActivity
import de.othr.plantico.ui.search.SearchableActivity

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

        binding.bottomNavigation.selectedItemId = R.id.action_search

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


}
