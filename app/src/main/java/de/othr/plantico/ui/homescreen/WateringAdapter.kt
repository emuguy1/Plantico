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
import de.othr.plantico.database.entities.OwnedPlant
import de.othr.plantico.databinding.ViewWateringItemBinding
import de.othr.plantico.ui.PlantActivity
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class WateringAdapter(context: Context) : ListAdapter<OwnedPlant, WateringAdapter.WateringViewHolder>(OwnedPlantsComparator()) {
    private val con: Context  = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WateringViewHolder {
        val binding = ViewWateringItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return WateringViewHolder(binding,con)
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
            itemBinding.wateringMedium.setImageDrawable(ContextCompat.getDrawable(con,R.drawable.ic_waterdropplett_empty_icon))
            itemBinding.wateringHeavy.setImageDrawable(ContextCompat.getDrawable(con,R.drawable.ic_waterdropplett_empty_icon))
            itemBinding.wateringPlantText.text= plant.plantName
            itemBinding.wateringLocationText.text = plant.location ?: "-"
            //TODO: Get Watering Level from Plant
            //TODO: Should already be eliminated in the query
            if (plant.lastWatered != null){
                var dt = Date()
                val c: Calendar = Calendar.getInstance()
                c.setTime(plant.lastWatered)
                if(plant.customWateringCycle != null){
                    c.add(Calendar.DATE, plant.customWateringCycle)
                }
                //TODO: Get Watering cycle from the plant type
                else{
                    c.add(Calendar.DATE, 1)
                }
                dt = c.getTime()
                itemBinding.wateringDateText.text = dt.toPlanticoString()
                //val n = plant.lastWatered.toInstant().plus(1, ChronoUnit.DAYS)
            }
            itemBinding.wateringCard.setOnClickListener(this)
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

fun Date.toPlanticoString():String{
    val suffixes = arrayOf(
        "th", "st", "nd", "rd", "th", "th", "th", "th",
        "th", "th","th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th", "st")
    val c = Calendar.getInstance()
    c.time = this
    val day = c[Calendar.DAY_OF_MONTH]
    val month_name = SimpleDateFormat("MMMM", Locale.ENGLISH).format(c.getTime())
    return ""+day + suffixes[day] + " " + month_name

}
