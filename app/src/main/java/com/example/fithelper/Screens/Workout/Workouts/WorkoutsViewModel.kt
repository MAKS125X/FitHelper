package com.example.fithelper.Screens.Workout.Workouts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.Models.Workout
import com.example.fithelper.Repositories.WorkoutRepository

class WorkoutsViewModel: ViewModel() {
    val workouts = MutableLiveData<MutableList<Workout>>()

    init {
        workouts.value = mutableListOf()
    }

    fun initializeWorkouts() {
        var list = mutableListOf<Workout>()
        list.add(Workout("1", "1", 123))
        list.add(Workout("2", "3", 123))
        list.add(Workout("2", "2", 123))
        list.add(Workout("4", "3", 123))

        workouts.value = list
    }
}