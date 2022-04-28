package com.example.fithelper.screens.common.datePickerDialogFragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import java.io.Serializable

class DatePickerDialogFragment : DialogFragment() {
    private val args by navArgs<DatePickerDialogFragmentArgs>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(
            requireContext(),
            args.listener,
            args.year,
            args.monthOfYear,
            args.dayOfMonth
        )
    }

    interface OnDateSetListener : Serializable, DatePickerDialog.OnDateSetListener
}