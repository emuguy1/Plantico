package de.othr.plantico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.asFlow
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.database.entities.Plant

class PlantActivity : AppCompatActivity() {
    private val testViewModel: TestViewModel by viewModels {
        TestViewModelFactory((application as PlantApplication).repository)
    }
    private val plantList = ArrayList<Plant>()
    private lateinit var plantHeader: TextView
    private var plantID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant)
        plantHeader = findViewById(R.id.plant_detail_header)
        testViewModel.allPlants.observe(this) { plants ->
            // Update the cached copy of the words in the adapter.
            plants.let { plantList.clear()
                        plantList.addAll(it)
                val plant = plantList.find { plant ->  plant.id == plantID}
                if (plant != null) {
                    plantHeader.text = plant.plantName
                }}
        }
        //testViewModel.
        //plantHeader.setText("lol")
        val bundle = savedInstanceState?:intent.extras
        if(bundle != null) {
            plantID = bundle.getInt(SELECTED_PLANT)


        }
    }

    companion object {
        const val SELECTED_PLANT = "selected_plant"

    }
}