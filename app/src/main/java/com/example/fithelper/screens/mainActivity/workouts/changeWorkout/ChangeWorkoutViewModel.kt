package com.example.fithelper.screens.mainActivity.workouts.changeWorkout

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.models.Exercise
import com.example.fithelper.models.Workout
import com.example.fithelper.repositories.WorkoutRepository
import java.lang.NullPointerException

class ChangeWorkoutViewModel(
    id: String,
    userId: String,
    name: String?,
    dateInMilliseconds: Long?,
    exerciseList: MutableList<Exercise>
) : ViewModel() {
    // todo: а что если сделать id и UserId просто string?
    private val id = MutableLiveData<String>()
    private val userId = MutableLiveData<String>()
    val name = MutableLiveData<String?>()
    val dateInMilliseconds = MutableLiveData<Long?>()
    val exerciseList = MutableLiveData<MutableList<Exercise>>()

    init {
        this.id.value = id
        this.userId.value = userId
        this.name.value = name
        this.dateInMilliseconds.value = dateInMilliseconds
        this.exerciseList.value = exerciseList
    }

    fun updateWorkout() {
        WorkoutRepository.updateWorkout(
            Workout(
                id.value ?: throw NullPointerException("Id can not be null"),
                userId.value ?: throw NullPointerException("User id can not be null"),
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