package de.othr.plantico

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.othr.plantico.database.entities.Plant

class PlantListAdapter: ListAdapter<Plant, PlantListAdapter.PlantViewHolder>(PlantsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.plantName)
    }



    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val plantItemView: TextView = itemView.findViewById(R.id.plantTextView)

        fun bind(text: String?) {
            plantItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): PlantViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.plant_item, parent, false)
                return PlantViewHolder(view)
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