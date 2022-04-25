package com.example.fithelper.screens.mainActivity.workouts.changeWorkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fithelper.models.Workout
import java.lang.NullPointerException

class ChangeWorkoutFactory(private val workout: Workout?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (workout == null)
            throw NullPointerException("Can not found this value")

        return modelClass.getConstructor(Workout::class.java).newInstance(workout)
    }
}