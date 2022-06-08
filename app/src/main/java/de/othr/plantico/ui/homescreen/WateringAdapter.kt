package de.othr.plantico.ui.homescreen

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
import de.othr.plantico.databinding.ViewWateringItemBinding
import de.othr.plantico.toPlanticoString
import de.othr.plantico.ui.ownedPlant.OwnedPlantActivity

class WateringAdapter(context: Context) :
    ListAdapter<OwnedPlant, WateringAdapter.WateringViewHolder>(OwnedPlantsComparator()) {
    private val con: Context = context
    private lateinit var plantList: List<Plant>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WateringViewHolder {
        val binding = ViewWateringItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return WateringViewHolder(binding, con)
    }

    override fun onBindViewHolder(holder: WateringViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)

    }

    fun addPlants(plants: List<Plant>) {
        plantList = plants
        notifyDataSetChanged()
    }


    inner class WateringViewHolder(binding: ViewWateringItemBinding, context: Context) :
        RecyclerView.ViewHolder(
            binding.root
        ),
        View.OnClickListener {
        private val itemBinding = binding

        fun bind(plant: OwnedPlant) {
            //reset item to standard

            itemBinding.wateringHeavy.setImageDrawable(
                ContextCompat.getDrawable(
                    con,
                    R.drawable.ic_waterdropplett_empty_icon
                )
            )
            itemBinding.wateringPlantText.text = plant.plantName
            itemBinding.wateringLocationText.text = plant.location ?: "-"
            if (plantList != null) {
                var realPlant = plantList[plant.plantID]
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
                if (plant.lastWatered != null) {
                    var wateringDate = plant.lastWatered
                    if (wateringDate != null) {
                        wateringDate = if (plant.customWateringCycle != null) {
                            wateringDate.addDays(plant.customWateringCycle)
                        } else {
                            wateringDate.addDays(realPlant.wateringCycleDays)
                        }
                        itemBinding.wateringDateText.text = wateringDate.toPlanticoString()
                    }
                }
            }

            itemBinding.wateringCard.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (p0 != null) {
                if (p0.id == R.id.watering_card) {
                    val context = p0.context
                    val intent = Intent(context, OwnedPlantActivity::class.java).putExtra(
                        OwnedPlantActivity.SELECTED_OWN_PLANT,
                        currentList[layoutPosition].id
                    ).putExtra(
                        OwnedPlantActivity.SELECTED_PLANT,
                        currentList[layoutPosition].plantID
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

