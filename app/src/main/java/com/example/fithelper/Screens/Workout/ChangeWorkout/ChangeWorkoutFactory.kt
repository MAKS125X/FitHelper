package com.example.fithelper.Screens.Workout.ChangeWorkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fithelper.Models.Workout

class ChangeWorkoutFactory(val workout: Workout) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChangeWorkoutViewModel(workout) as T
    }
}