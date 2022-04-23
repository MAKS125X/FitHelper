package com.example.fithelper.screens.shared

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.models.Exercise
import com.example.fithelper.models.Workout
import com.example.fithelper.repositories.WorkoutRepository

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
        exerciseList.value = workout.exerciseList
    }

    fun updateWorkout() {
        WorkoutRepository.updateWorkout(
            Workout(
                // todo: проверка на null
                id.value ?: "Не найден",
                userId.value ?: "Не найден",
                name.value,
                dateInMilliseconds.value,
                exerciseList.value ?: mutableListOf()
            )
        ).addOnSuccessListener {
            Log.i("Workout", "Workout $id updated")
        }.addOnFailureListener { e ->
            Log.e("Database", "Workout $id cannot updated")
        }
    }
}