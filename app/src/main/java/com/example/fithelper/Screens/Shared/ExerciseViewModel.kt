package com.example.fithelper.Screens.Shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.Models.Exercise
import com.example.fithelper.Models.Workout

class ExerciseViewModel: ViewModel() {
    val exercise = MutableLiveData<Exercise>()

    fun setExercise(
        name: String,
        numberOfApproaches: Int,
        numberOfRepetitions: Int,
        weight: Int
    ) {
        exercise.value = Exercise(name, numberOfApproaches, numberOfRepetitions, weight)
    }

    fun setExercise(exercise: Exercise) {
        this.exercise.value = exercise
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