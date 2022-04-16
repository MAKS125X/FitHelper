package com.example.fithelper.Screens.Shared

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.Models.Exercise
import com.example.fithelper.Models.Workout
import com.example.fithelper.Repositories.WorkoutRepository

class WorkoutViewModel : ViewModel() {

    val id = MutableLiveData<String?>()
    val userId = MutableLiveData<String?>()
    val name = MutableLiveData<String?>()
    val dateInMilliseconds = MutableLiveData<Long?>()
    val exerciseList = MutableLiveData<MutableList<Exercise>>()

    init {
        exerciseList.value = mutableListOf()
    }

    fun setWorkout(workout: Workout) {
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
        ).addOnSuccessListener {
            Log.i("Workout", "Workout $id updated")
        }.addOnFailureListener { e ->
            Log.e("Database", "Workout $id cannot updated")
        }
    }
}