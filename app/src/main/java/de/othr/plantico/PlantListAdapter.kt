package de.othr.plantico

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.othr.plantico.PlantActivity.Companion.SELECTED_PLANT
import de.othr.plantico.database.entities.Plant

class PlantListAdapter: ListAdapter<Plant, PlantListAdapter.PlantViewHolder>(PlantsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.plant_item, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.plantName)

    }



    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val plantItemView: TextView = itemView.findViewById(R.id.plantTextView)
        private val plantCard: CardView = itemView.findViewById(R.id.card)

        fun bind(text: String?) {
            plantItemView.text = text
            plantCard.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if(p0 != null) {
                if(p0.id == R.id.card) {
                val context = p0.context
                val intent = Intent(context, PlantActivity::class.java).putExtra(SELECTED_PLANT, currentList[layoutPosition].id)
                context.startActivity(intent)
            }}
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