package com.example.fithelper

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResultListener

class DeleteWorkoutDialog : DialogFragment(){
//private val listener: NoticeDialogListener
    internal lateinit var listener: DeleteWorkoutDialogListener


    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = parentFragment as DeleteWorkoutDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Build the dialog and set up the button click handlers
            val builder = AlertDialog.Builder(it)

            val arg = arguments?.getString("workoutId")

            builder.setMessage("Вы действительно хотите удалить данную тренировку?")
                .setPositiveButton("Да"
                ) { dialog, id ->
                    // Send the positive button event back to the host activity
                    if (arg != null) {
                        listener.onDialogPositiveClick(this, arg)
                    }
                }
                .setNegativeButton("Нет"
                ) { dialog, id ->
                    // Send the negative button event back to the host activity
                    listener.onDialogNegativeClick(this)
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}