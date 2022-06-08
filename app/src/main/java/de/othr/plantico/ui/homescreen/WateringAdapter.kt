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
import de.othr.plantico.databinding.ViewWateringItemBinding
import de.othr.plantico.ownedPlant.OwnedPlantActivity
import de.othr.plantico.toPlanticoString

class WateringAdapter(context: Context) :
    ListAdapter<OwnedPlant, WateringAdapter.WateringViewHolder>(OwnedPlantsComparator()) {
    private val con: Context = context
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


    inner class WateringViewHolder(binding: ViewWateringItemBinding, context: Context) :
        RecyclerView.ViewHolder(
            binding.root
        ),
        View.OnClickListener {
        private val itemBinding = binding

        fun bind(plant: OwnedPlant) {
            //reset item to standard
            itemBinding.wateringMedium.setImageDrawable(
                ContextCompat.getDrawable(
                    con,
                    R.drawable.ic_waterdropplett_empty_icon
                )
            )
            itemBinding.wateringHeavy.setImageDrawable(
                ContextCompat.getDrawable(
                    con,
                    R.drawable.ic_waterdropplett_empty_icon
                )
            )
            itemBinding.wateringPlantText.text = plant.plantName
            itemBinding.wateringLocationText.text = plant.location ?: "-"
            //TODO: Get Watering Level from Plant
            //TODO: Should already be eliminated in the query
            if (plant.lastWatered != null) {
                var wateringDate = plant.lastWatered
                if (wateringDate != null) {
                    wateringDate = wateringDate.addDays(plant.customWateringCycle)
                    itemBinding.wateringDateText.text = wateringDate.toPlanticoString()

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

