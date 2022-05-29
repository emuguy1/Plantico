package de.othr.plantico.ui.plantDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.othr.plantico.database.entities.PlantCategory
import de.othr.plantico.databinding.ViewCategoriesItemBinding

class CategorieListAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = mutableListOf<PlantCategory>()
    lateinit var itemList: RecyclerView



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as CastegoriesItemViewHolder).bindTo(item)
    }

    override fun getItemCount() = items.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        itemList = recyclerView
    }

    fun setList(list: List<PlantCategory>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastegoriesItemViewHolder {
        val binding = ViewCategoriesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CastegoriesItemViewHolder(binding)
    }


    inner class CastegoriesItemViewHolder(binding: ViewCategoriesItemBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ),
        View.OnClickListener {
        private val itemBinding = binding

        fun bindTo(item: PlantCategory) {
            itemBinding.itemText.text = item.name.replace("_"," ")
        }

        override fun onClick(p0: View?) {

        }
    }
}