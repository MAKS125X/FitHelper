package com.example.fithelper.screens.mainActivity.workouts.createWorkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.extensions.notifyObserver
import com.example.fithelper.models.Exercise
import com.example.fithelper.models.Workout
import com.example.fithelper.repositories.WorkoutRepository
import com.example.fithelper.services.UserService

class CreateWorkoutViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    val dateInMilliseconds = MutableLiveData<Long?>()
    val exercises = MutableLiveData<MutableList<Exercise>>()

    init {
        name.value = ""
        dateInMilliseconds.value = null
        exercises.value = mutableListOf()
    }

    fun setName(name: String?) {
        this.name.value = name
    }

    fun setDate(date: Long?) {
        this.dateInMilliseconds.value = date
    }

    fun addExercise(exercise: Exercise) {
        exercises.value?.add(exercise)
        exercises.notifyObserver()
    }

    fun create() {
        val workout = Workout(
            UserService.getUserId()!!,
            name.value,
            dateInMilliseconds.value,
            exercises.value ?: mutableListOf()
        )

        WorkoutRepository.createWorkout(workout)
    }
}

