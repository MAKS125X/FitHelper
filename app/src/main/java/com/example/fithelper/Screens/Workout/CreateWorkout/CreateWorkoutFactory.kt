package com.example.fithelper.Screens.Workout.CreateWorkout

import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.*

class CreateWorkoutFactory(context: Context, listener: DatePickerDialog.OnDateSetListener) :
    ViewModelProvider.Factory {

    private val calendar = Calendar.getInstance()
    private val dpd: DatePickerDialog = DatePickerDialog(
        context,
        listener,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateWorkoutViewModel(dpd) as T
    }
}