package de.othr.plantico.ui.search

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import de.othr.plantico.R
import de.othr.plantico.database.entities.Plant
import de.othr.plantico.database.entities.PlantCategory

class SearchCategoryDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val selectedItems = ArrayList<Int>() // Where we track the selected items
            val builder = AlertDialog.Builder(it)
            // Set the dialog title
            builder.setTitle(R.string.search_category_dialog_title)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(
                    arrayOf(PlantCategory.values().toString()), null,
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
                        //...
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        //...
                    })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}