package de.othr.plantico.ui.homescreen

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.othr.plantico.R
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.databinding.ViewWateringItemBinding
import de.othr.plantico.ui.PlantActivity

class WateringAdapter : ListAdapter<Plant, WateringAdapter.WateringViewHolder>(PlantsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WateringViewHolder {
        val binding = ViewWateringItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return WateringViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WateringViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.plantName)

    }


    inner class WateringViewHolder(binding: ViewWateringItemBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ),
        View.OnClickListener {
        private val itemBinding = binding

        fun bind(text: String?) {
            itemBinding.wateringPlantText.text= text
            itemBinding.wateringCard.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (p0 != null) {
                if (p0.id == R.id.card) {
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

    class PlantsComparator : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.plantName == newItem.plantName
        }
    }


}
