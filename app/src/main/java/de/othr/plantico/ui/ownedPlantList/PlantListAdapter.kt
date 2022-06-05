package de.othr.plantico.ui.ownedPlantList

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
import de.othr.plantico.ui.PlantActivity.Companion.SELECTED_PLANT
import de.othr.plantico.ui.PlantActivity

class PlantListAdapter : ListAdapter<OwnedPlant, PlantListAdapter.PlantViewHolder>(PlantsComparator()) {

    var allOwnedPlant = mutableListOf<OwnedPlant>()

    fun setPlants(plants: List<OwnedPlant>){
        allOwnedPlant.clear()
        allOwnedPlant.addAll(plants)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding = ViewOwnedPlantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val current = getItem(position)
        holder.bindTo(current, position)
    }

    inner class PlantViewHolder(binding: ViewOwnedPlantItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val itemBinding = binding

        fun bindTo(item: OwnedPlant, position: Int){
             itemBinding.plantTitleText.text= item.plantName
             if (position == 0 || allOwnedPlant[position - 1].location == item.location)   {
                itemBinding.Room.visibility = View.VISIBLE

                 itemBinding.Room.text = item.location?: "-"
            }
        }


         fun onClick(p0: View?) {
            if (p0 != null) {
                if (p0.id == R.id.card) {
                    val context = p0.context
                    val intent = Intent(context, PlantActivity::class.java).putExtra(
                        SELECTED_PLANT,
                        currentList[layoutPosition].id
                    )
                    context.startActivity(intent)
                }
            }
        }
    }

    class PlantsComparator : DiffUtil.ItemCallback<OwnedPlant>() {
        override fun areItemsTheSame(oldItem: OwnedPlant, newItem: OwnedPlant): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OwnedPlant, newItem: OwnedPlant): Boolean {
            return oldItem.plantName == newItem.plantName
        }
    }
}
