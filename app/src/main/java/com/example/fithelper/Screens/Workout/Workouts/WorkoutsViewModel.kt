package com.example.fithelper.Screens.Workout.Workouts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.Models.Exercise
import com.example.fithelper.Models.Workout
import com.example.fithelper.Repositories.WorkoutRepository

class WorkoutsViewModel : ViewModel() {
    val workouts = MutableLiveData<MutableList<Workout>>()

    init {
        workouts.value = mutableListOf()
    }

    fun initializeWorkouts() {
        workouts.value?.add(Workout("1", "1", 123, mutableListOf(Exercise("1", 1, 1, 11))))
        workouts.value?.add(Workout("2", "2", 123, mutableListOf(Exercise("1", 21, 1, 1))))
        workouts.value?.add(Workout("3", "3", 123, mutableListOf(Exercise("1", 1, 21, 11))))
        workouts.value?.add(
            Workout(
                "4",
                "4",
                123,
                mutableListOf(
                    Exercise("1", 1, 1, 31),
                    Exercise("2", 1, 1, 31),
                    Exercise("3", 1, 1, 31),
                    Exercise("4", 1, 1, 31),
                    Exercise("4", 1, 1, 31),
                    Exercise("5", 1, 1, 31),
                    Exercise("1", 1, 1, 31),
                    Exercise("1", 1, 1, 31),
                    Exercise("1", 1, 1, 31),
                    Exercise("1", 1, 1, 31),
                )
            )
        )

        workouts.value = workouts.value
    }
}