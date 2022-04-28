package com.example.fithelper.screens.common.confirmationDialogFragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fithelper.R
import java.io.Serializable

class ConfirmationDialogFragment : DialogFragment() {
    private val args by navArgs<ConfirmationDialogFragmentArgs>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(args.message)

        val listener = args.listener
        if (listener != null) {
            builder
                .setPositiveButton(getString(R.string.confirm)) { dialogInterface, buttonClicked ->
                    listener.confirmButtonClicked(
                        dialogInterface,
                        buttonClicked
                    )
                    findNavController().navigateUp()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialogInterface, buttonClicked ->
                    listener.cancelButtonClicked(
                        dialogInterface,
                        buttonClicked
                    )
                    findNavController().navigateUp()
                }
        }

        return builder.create()
    }

    interface OnConfirmationListener : Serializable {
        fun confirmButtonClicked(dialogInterface: DialogInterface, buttonClicked: Int)
        fun cancelButtonClicked(dialogInterface: DialogInterface, buttonClicked: Int)
    }
}