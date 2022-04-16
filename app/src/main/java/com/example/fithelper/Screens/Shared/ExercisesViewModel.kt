package com.example.fithelper.Screens.Shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.Extensions.notifyObserver
import com.example.fithelper.Models.Exercise
import com.example.fithelper.Models.Workout

class ExercisesViewModel : ViewModel() {
    val exercises = MutableLiveData<MutableList<Exercise>>()

    init {
        exercises.value = mutableListOf()
    }

    fun addExercise(
        name: String,
        numberOfApproaches: Int,
        numberOfRepetitions: Int,
        weight: Int
    ) {
        exercises.value?.add((
                    Exercise(
                        name,
                        numberOfApproaches,
                        numberOfRepetitions,
                        weight
                    ))
        )
        exercises.notifyObserver()
    }

    fun setExercises(exercises: MutableList<Exercise>?) {
        this.exercises.value = exercises ?: mutableListOf()
    }

    // todo: DELETE
    val changedWorkout: MutableLiveData<Workout> by lazy {
        MutableLiveData<Workout>()
    }

    // todo: DELETE
    val createdWorkout: MutableLiveData<Workout> by lazy {
        MutableLiveData<Workout>()
    }
}


