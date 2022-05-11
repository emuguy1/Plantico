package de.othr.plantico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import de.othr.plantico.database.PlantApplication

class PlantActivity : AppCompatActivity() {
    private val testViewModel: TestViewModel by viewModels {
        TestViewModelFactory((application as PlantApplication).repository)
    }
    private lateinit var plantHeader: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant)
        plantHeader = findViewById(R.id.plant_detail_header)
        //testViewModel.
        //plantHeader.setText("lol")
        val bundle = savedInstanceState?:intent.extras
        if(bundle != null) {
            val plantID = bundle.getInt(SELECTED_PLANT)

            val plant = testViewModel.allPlants.value?.find { plant ->  plant.id == plantID}
            if (plant != null) {
                plantHeader.text = plant.plantName
            }
        }
    }

    companion object {
        const val SELECTED_PLANT = "selected_plant"

    }
}