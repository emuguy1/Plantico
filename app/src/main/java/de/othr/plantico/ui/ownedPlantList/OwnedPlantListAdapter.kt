@file:Suppress("SENSELESS_COMPARISON")

package de.othr.plantico.ui.ownedPlantList

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.othr.plantico.R
import de.othr.plantico.addDays
import de.othr.plantico.database.entities.OwnedPlant
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.database.entities.WateringLevel
import de.othr.plantico.databinding.ViewOwnedPlantItemBinding
import de.othr.plantico.nowUTC
import de.othr.plantico.ui.PlantActivity
import java.time.LocalDate
import java.util.*


class OwnedPlantListAdapter(context: Context) :
    ListAdapter<OwnedPlant, OwnedPlantListAdapter.OwnedPlantViewHolder>(OwnedPlantsComparator()) {
    private val con: Context = context
    private var plantList: List<Plant>? = null

    fun addPlants(plants: List<Plant>) {
        plantList = plants
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnedPlantViewHolder {
        val binding = ViewOwnedPlantItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return OwnedPlantViewHolder(binding, con)
    }

    override fun onBindViewHolder(holder: OwnedPlantViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, position)
    }


    inner class OwnedPlantViewHolder(binding: ViewOwnedPlantItemBinding, context: Context) :
        RecyclerView.ViewHolder(
            binding.root
        ),
        View.OnClickListener {

        private val itemBinding = binding

        @SuppressLint("SetTextI18n")
        fun bind(plant: OwnedPlant, position: Int) {
            itemBinding.plantTitleText.text = plant.plantName
            itemBinding.Room.text = plant.location ?: "-"
            if (position == 0 || getItem(position - 1).location?.lowercase() == plant.location?.lowercase()) {
                itemBinding.Room.visibility = View.GONE
            }
            if (plantList != null) {
                val realPlant = plantList!![plant.plantID]
                //Watering
                val waterdropplettFull = ContextCompat
                    .getDrawable(con, R.drawable.ic_waterdropplett_full_icon)
                val waterdropplettEmpty = ContextCompat
                    .getDrawable(con, R.drawable.ic_waterdropplett_empty_icon)

                when (realPlant.wateringLevel) {

                    WateringLevel.LOW -> {
                        itemBinding.wateringLight.setImageDrawable(waterdropplettFull)
                        itemBinding.wateringMedium.setImageDrawable(waterdropplettEmpty)
                        itemBinding.wateringHeavy.setImageDrawable(waterdropplettEmpty)
                    }
                    WateringLevel.MEDIUM -> {
                        itemBinding.wateringLight.setImageDrawable(waterdropplettFull)
                        itemBinding.wateringMedium.setImageDrawable(waterdropplettFull)
                        itemBinding.wateringHeavy.setImageDrawable(waterdropplettEmpty)
                    }
                    WateringLevel.HIGH -> {
                        itemBinding.wateringLight.setImageDrawable(waterdropplettFull)
                        itemBinding.wateringMedium.setImageDrawable(waterdropplettFull)
                        itemBinding.wateringHeavy.setImageDrawable(waterdropplettFull)
                    }
                }

                val currentDate = Date().nowUTC()

                if (plant.lastWatered != null) {


                    var wateringDate = plant.lastWatered
                    wateringDate = if (plant.customWateringCycle != null) {
                        wateringDate.addDays(plant.customWateringCycle)
                    } else {
                        wateringDate.addDays(realPlant.wateringCycleDays)
                    }
                    val diff: Long = wateringDate.time - currentDate.time
                    val days = (((diff / 1000) / 60) / 60) / 24
                    itemBinding.plantWateringTimeText.text = "$days days"

                }

                if(plant.birthday != null){
                    val currentDate = Date().nowUTC()

                    val diff: Long = currentDate.time - plant.birthday.time
                    val seconds = diff / 1000
                    val minutes = seconds / 60
                    val hours = minutes / 60
                    val days = hours / 24
                    val years = days / 365

                    val ageText = if(years>0){
                        "$years years old"
                    }else{
                        "$days days old"
                    }
                    itemBinding.plantAgeText.text = ageText
                }
                else{
                    itemBinding.plantAgeText.text = "-"
                }
            }

        }

        override fun onClick(p0: View?) {
            if (p0 != null) {
                if (p0.id == R.id.watering_card) {
                    val context = p0.context
                    val intent = Intent(context, PlantActivity::class.java).putExtra(
                        PlantActivity.SELECTED_PLANT,
                        currentList[layoutPosition].id
                    )
                    context.startActivity(intent)
                }
            }
        }


    }


    class OwnedPlantsComparator : DiffUtil.ItemCallback<OwnedPlant>() {
        override fun areItemsTheSame(oldItem: OwnedPlant, newItem: OwnedPlant): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OwnedPlant, newItem: OwnedPlant): Boolean {
            return oldItem.plantName == newItem.plantName
        }
    }

}