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
import de.othr.plantico.databinding.ViewPlantItemHomescreenBinding
import de.othr.plantico.ui.ownedPlant.OwnedPlantActivity
import de.othr.plantico.ui.plantDetails.PlantDetailActivity

class PlantAdapter : ListAdapter<Plant, PlantAdapter.PlantHistoryViewHolder>(PlantsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantHistoryViewHolder {
        val binding = ViewPlantItemHomescreenBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PlantHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantHistoryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)

    }


    inner class PlantHistoryViewHolder(binding: ViewPlantItemHomescreenBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ),
        View.OnClickListener {
        private val itemBinding = binding

        fun bind(plant: Plant) {
            //reset item to Default
            itemBinding.plantDifficultyMedium.visibility = View.GONE
            itemBinding.plantDifficultyHard.visibility = View.GONE

            itemBinding.plantTitleText.text = plant.plantName
            itemBinding.plantFamilyText.text = plant.family
            itemBinding.plantSubfamilyText.text = plant.subfamily
            itemBinding.plantGenusText.text = plant.genus
            //1 equals PlantDifficulty.INTERMEDIATE
            if (plant.difficulty.ordinal >= 1) {
                itemBinding.plantDifficultyMedium.visibility = View.VISIBLE
            }
            //2 equals PlantDifficulty.ADVANCED
            if (plant.difficulty.ordinal >= 2) {
                itemBinding.plantDifficultyHard.visibility = View.VISIBLE
            }
            itemBinding.plantCard.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (p0 != null) {
                if (p0.id == R.id.plant_card) {
                    val context = p0.context
                    val intent = Intent(context, PlantDetailActivity::class.java).putExtra(
                        OwnedPlantActivity.SELECTED_PLANT,
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
