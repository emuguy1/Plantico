package de.othr.plantico.ui.search

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
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.database.entities.PlantCategory
import de.othr.plantico.database.entities.PlantDifficulty
import de.othr.plantico.database.entities.WateringLevel
import de.othr.plantico.databinding.ViewPlantItemSearchBinding
import de.othr.plantico.ui.PlantActivity

class SearchPlantAdapter(context: Context) :
    ListAdapter<Plant, SearchPlantAdapter.SearchPlantHistoryViewHolder>(SearchPlantsComparator()) {
    private val con: Context = context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchPlantHistoryViewHolder {
        val binding = ViewPlantItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SearchPlantHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchPlantHistoryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)

    }


    inner class SearchPlantHistoryViewHolder(binding: ViewPlantItemSearchBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ),
        View.OnClickListener {
        private val itemBinding = binding

        private fun stringifyCategoryList(categoryList: List<PlantCategory>): String {
            var transformedList: String = ""
            for (category in categoryList) {
                transformedList += category.name.lowercase().replace('_', ' ')
                if (categoryList.last() != category) {
                    transformedList += ", "
                }
            }
            return transformedList
        }

        fun bind(plant: Plant) {

            // Set Text values for Name and Category
            itemBinding.plantTitleText.text = plant.plantName
            itemBinding.plantCategoryText.text = stringifyCategoryList(plant.plantCategory)

            // Set displayed difficulty level
            when (plant.difficulty) {
                PlantDifficulty.EASY -> {
                    itemBinding.plantDifficultyEasy.visibility = View.VISIBLE
                    itemBinding.plantDifficultyMedium.visibility = View.GONE
                    itemBinding.plantDifficultyHard.visibility = View.GONE
                }
                PlantDifficulty.INTERMEDIATE -> {
                    itemBinding.plantDifficultyEasy.visibility = View.VISIBLE
                    itemBinding.plantDifficultyMedium.visibility = View.VISIBLE
                    itemBinding.plantDifficultyHard.visibility = View.GONE
                }
                PlantDifficulty.ADVANCED -> {
                    itemBinding.plantDifficultyEasy.visibility = View.VISIBLE
                    itemBinding.plantDifficultyMedium.visibility = View.VISIBLE
                    itemBinding.plantDifficultyHard.visibility = View.VISIBLE
                }
            }

            // Set displayed watering level
            when (plant.wateringLevel) {
                WateringLevel.LOW -> {
                    itemBinding.wateringLight.setImageDrawable(
                        ContextCompat.getDrawable(
                            con,
                            R.drawable.ic_waterdropplett_full_icon
                        )
                    )
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
                }
                WateringLevel.MEDIUM -> {
                    itemBinding.wateringLight.setImageDrawable(
                        ContextCompat.getDrawable(
                            con,
                            R.drawable.ic_waterdropplett_full_icon
                        )
                    )
                    itemBinding.wateringMedium.setImageDrawable(
                        ContextCompat.getDrawable(
                            con,
                            R.drawable.ic_waterdropplett_full_icon
                        )
                    )
                    itemBinding.wateringHeavy.setImageDrawable(
                        ContextCompat.getDrawable(
                            con,
                            R.drawable.ic_waterdropplett_empty_icon
                        )
                    )
                }
                WateringLevel.HIGH -> {
                    itemBinding.wateringLight.setImageDrawable(
                        ContextCompat.getDrawable(
                            con,
                            R.drawable.ic_waterdropplett_full_icon
                        )
                    )
                    itemBinding.wateringMedium.setImageDrawable(
                        ContextCompat.getDrawable(
                            con,
                            R.drawable.ic_waterdropplett_full_icon
                        )
                    )
                    itemBinding.wateringHeavy.setImageDrawable(
                        ContextCompat.getDrawable(
                            con,
                            R.drawable.ic_waterdropplett_full_icon
                        )
                    )
                }
            }

            itemBinding.plantCard.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (p0 != null) {
                if (p0.id == R.id.plant_card) {
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

    class SearchPlantsComparator : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.plantName == newItem.plantName
        }
    }
}
