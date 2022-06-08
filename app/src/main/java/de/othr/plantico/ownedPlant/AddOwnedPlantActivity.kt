package de.othr.plantico.ownedPlant

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import de.othr.plantico.PlantViewModel
import de.othr.plantico.PlantViewModelFactory
import de.othr.plantico.database.PlantApplication
import de.othr.plantico.database.entities.OwnedPlant
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.databinding.ActivityOwnedPlantAddBinding
import de.othr.plantico.parseStringToDate
import de.othr.plantico.ui.homescreen.HomescreenActivity
import de.othr.plantico.ui.plantDetails.PlantDetailActivity
import java.util.*

class AddOwnedPlantActivity : AppCompatActivity() {
    private var plantID = 0
    private val plantList = ArrayList<Plant>()
    private lateinit var binding: ActivityOwnedPlantAddBinding
    private val plantViewModel: PlantViewModel by viewModels {
        PlantViewModelFactory((application as PlantApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnedPlantAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mActionBarToolbar = binding.toolbarPlantView
        mActionBarToolbar.title = ""
        setSupportActionBar(mActionBarToolbar)

        val bundle = savedInstanceState ?: intent.extras
        if (bundle != null) {
            plantID = bundle.getInt(PlantDetailActivity.SELECTED_ADD_PLANT)
        }

        var plant: Plant? = null
        plantViewModel.allPlants.observe(this) { plants ->
            // Update the cached copy of the words in the adapter.
            plants.let {
                plantList.clear()
                plantList.addAll(it)
                plant = plantList.find { plant -> plant.id == plantID }!!
                if (plant != null) {
                    binding.apply {
                        plantName.text = plant!!.plantName
                        inputWatering.editText!!.setText(plant!!.wateringCycleDays.toString())
                    }
                }
            }
        }
        binding.buttonSavePlant.setOnClickListener {

            var wateringCycle = plant!!.wateringCycleDays
            val customName = binding.inputPlantName.editText?.text.toString()
            if (customName == "") {
                return@setOnClickListener;
            }
            val wateringCycleString = binding.inputWatering.editText?.text.toString()
            wateringCycleString.toIntOrNull()?.let { customCycle -> wateringCycle = customCycle }
            val location = binding.inputPlantLocation.editText?.text.toString()
            val birthday = binding.inputPlantBirthday.editText?.text.toString()
            val date: Date? = birthday.parseStringToDate()
            val ownedPlant =
                OwnedPlant(customName, plant!!.id, null, date, wateringCycle, location)
            plantViewModel.insertOwnedPlant(ownedPlant)
            val intent = Intent(this, HomescreenActivity::class.java)
            startActivity(intent)
        }
        binding.buttonCancel.setOnClickListener {
            val intent = plant?.let { it1 ->
                Intent(
                    this,
                    PlantDetailActivity::class.java
                ).putExtra(
                    OwnedPlantActivity.SELECTED_PLANT,
                    it1.id
                )
            }
            startActivity(intent)
        }
    }
}
