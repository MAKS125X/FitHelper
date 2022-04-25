package com.example.fithelper.screens.mainActivity.workouts.changeWorkout

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.models.Exercise
import com.example.fithelper.models.Workout
import com.example.fithelper.repositories.WorkoutRepository
import java.lang.NullPointerException

class ChangeWorkoutViewModel(
    private var id: String,
    private var userId: String,
    name: String?,
    dateInMilliseconds: Long?,
    exerciseList: MutableList<Exercise>
) : ViewModel() {

    val name = MutableLiveData<String?>()
    val dateInMilliseconds = MutableLiveData<Long?>()
    val exerciseList = MutableLiveData<MutableList<Exercise>>()

    init {
        this.name.value = name
        this.dateInMilliseconds.value = dateInMilliseconds
        this.exerciseList.value = exerciseList
    }

    fun updateWorkout() {
        WorkoutRepository.updateWorkout(
            Workout(
                id,
                userId ,
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