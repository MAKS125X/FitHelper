package com.example.fithelper.Screens.Workout.ChangeWorkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.Models.Exercise
import com.example.fithelper.Models.Workout
import com.example.fithelper.Repositories.WorkoutRepository

class ChangeWorkoutViewModel(workout: Workout) : ViewModel() {

    val id = MutableLiveData<String?>()
    val userId = MutableLiveData<String?>()
    val name = MutableLiveData<String?>()
    val dateInMilliseconds = MutableLiveData<Long?>()
    val exerciseList = MutableLiveData<MutableList<Exercise>>()

    init {
        id.value = workout.id
        userId.value = workout.userId
        name.value = workout.name
        dateInMilliseconds.value = workout.dateInMilliseconds
        exerciseList.value = workout.exerciseList ?: mutableListOf()
    }

    fun updateWorkout() {
        WorkoutRepository.updateWorkout(
            Workout(
                id.value,
                userId.value,
                name.value,
                dateInMilliseconds.value,
                exerciseList.value
            )
        )
    }
}