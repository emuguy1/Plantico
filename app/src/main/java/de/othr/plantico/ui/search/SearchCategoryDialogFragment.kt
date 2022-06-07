package de.othr.plantico.ui.search

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import de.othr.plantico.R
import de.othr.plantico.database.entities.PlantCategory

class SearchCategoryDialogFragment(initialItems: List<PlantCategory>) : DialogFragment() {

    private lateinit var listener: SearchCategoryDialogListener
    private var selectedItemsAsCategory = initialItems
    private val selectedItems = ArrayList<Int>()

    interface SearchCategoryDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, selectedItems: List<PlantCategory>)
        fun onDialogNegativeClick(dialog: DialogFragment, selectedItems: List<PlantCategory>)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        try {
            // Instantiate the SearchCategoryDialogListener so we can send events to the host
            listener = context as SearchCategoryDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement SearchCategoryDialogListener"))
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Set the dialog title
            builder.setTitle(R.string.search_category_dialog_title)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(
                    plantCategoriesToStringList().toTypedArray(), initialCategoryConfig().toBooleanArray(),
                    DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedItems.add(which)
                        } else if (selectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            selectedItems.remove(which)
                        }
                    })
                // Set the action buttons
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK, so save the selectedItems results somewhere
                        // or return them to the component that opened the dialog
                        // Trigger events of the host
                        listener.onDialogPositiveClick(this, mapSelectedItemsToCategories())
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Trigger events of the host
                        listener.onDialogNegativeClick(this, selectedItemsAsCategory)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun plantCategoriesToStringList(): List<String> {
        return PlantCategory.values().map { plantCategory ->
            plantCategory.name.substring(0, 1).uppercase() + plantCategory.name.substring(1)
                .lowercase().replace('_', ' ')
        }
    }

    fun initialCategoryConfig() : List<Boolean> {
        val initialBoolList = PlantCategory.values().map { plantCategory ->
            selectedItemsAsCategory.contains(plantCategory)
        }
        for(plantCategory in selectedItemsAsCategory) {
            if(selectedItemsAsCategory.contains(plantCategory)) {
                selectedItems.add(plantCategory.ordinal)
            }
        }
        System.out.println("INITIAL BOOLEAN LIST: " + initialBoolList)
        return initialBoolList
    }

    fun mapSelectedItemsToCategories(): List<PlantCategory> {
        return selectedItems.map { item ->
            PlantCategory.values().get(item)
        }
    }
}