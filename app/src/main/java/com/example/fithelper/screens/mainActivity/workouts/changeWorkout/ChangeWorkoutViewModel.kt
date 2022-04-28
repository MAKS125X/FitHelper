package com.example.fithelper.screens.mainActivity.workouts.changeWorkout

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.models.Exercise
import com.example.fithelper.models.Workout
import com.example.fithelper.repositories.WorkoutRepository

class ChangeWorkoutViewModel(workout: Workout) : ViewModel() {
    private val id = workout.id
    private val userId = workout.userId

    val name = MutableLiveData<String?>()
    val dateInMilliseconds = MutableLiveData<Long?>()
    val exerciseList = MutableLiveData<MutableList<Exercise>>()

    init {
        this.name.value = workout.name
        this.dateInMilliseconds.value = workout.dateInMilliseconds
        this.exerciseList.value = workout.exerciseList
    }

    fun updateWorkout() {
        WorkoutRepository.updateWorkout(
            Workout(
                id,
                userId,
                name.value,
                dateInMilliseconds.value,
                exerciseList.value ?: mutableListOf()
            )
        ).addOnSuccessListener {
            Log.i("Workout", "Workout $id updated")
        }.addOnFailureListener { e ->
            Log.e("Database", e.message.toString())
        }
    }
}