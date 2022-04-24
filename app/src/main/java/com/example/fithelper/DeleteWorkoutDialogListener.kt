package com.example.fithelper

import androidx.fragment.app.DialogFragment

interface DeleteWorkoutDialogListener {

    //fun onDialogPositiveClick(dialog: DialogFragment)
    fun onDialogPositiveClick(dialog: DialogFragment, workoutId: String)
    //fun onDialogPositiveClick(dialog: DialogFragment, workoutId: Int)
    fun onDialogNegativeClick(dialog: DialogFragment)
}