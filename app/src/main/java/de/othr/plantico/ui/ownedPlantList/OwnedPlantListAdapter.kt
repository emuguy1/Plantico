package de.othr.plantico.ui.ownedPlantList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.othr.plantico.R
import de.othr.plantico.database.entities.OwnedPlant
import de.othr.plantico.databinding.ViewOwnedPlantItemBinding
import de.othr.plantico.ui.PlantActivity


class OwnedPlantListAdapter(context: Context) : ListAdapter<OwnedPlant, OwnedPlantListAdapter.OwnedPlantViewHolder>(OwnedPlantsComparator()) {
    private val con: Context = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): OwnedPlantViewHolder{
        val binding = ViewOwnedPlantItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return OwnedPlantViewHolder(binding,con)
    }

    override fun onBindViewHolder(holder: OwnedPlantViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current,position)
    }


    inner class OwnedPlantViewHolder(binding: ViewOwnedPlantItemBinding, context: Context) :
        RecyclerView.ViewHolder(
            binding.root
        ),
        View.OnClickListener {

        private val itemBinding = binding

        fun bind(plant: OwnedPlant, position: Int) {
            itemBinding.plantTitleText.text= plant.plantName
            itemBinding.Room.text = plant.location?: "-"
            if (position == 0 || getItem(position - 1).location?.lowercase() == plant.location?.lowercase())   {
                itemBinding.Room.visibility = View.GONE
            }
        //reset item to standard
//            itemBinding.wateringMedium.setImageDrawable(
//                ContextCompat.getDrawable(
//                    con,
//                    R.drawable.ic_waterdropplett_empty_icon
//                )
//            )
//            itemBinding.wateringHeavy.setImageDrawable(
//                ContextCompat.getDrawable(
//                    con,
//                    R.drawable.ic_waterdropplett_empty_icon
//                )
//            )
//            itemBinding.wateringPlantText.text = plant.plantName
//            itemBinding.wateringLocationText.text = plant.location ?: "-"
            //TODO: Get Watering Level from Plant
            //TODO: Should already be eliminated in the query
//            if (plant.lastWatered != null) {
//                var wateringDate = plant.lastWatered
//                wateringDate = if (plant.customWateringCycle != null) {
//                    wateringDate.addDays(plant.customWateringCycle)
//                }
//                //TODO: Get Watering cycle from the plant type
//                else {
//                    wateringDate.addDays(1)
//                }
//                //itemBinding.wateringDateText.text = wateringDate.toPlanticoString()
//            }
            //itemBinding.wateringCard.setOnClickListener(this)
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