package com.example.fithelper.Screens.Workout.CreateWorkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.Models.Exercise
import com.example.fithelper.Models.Workout
import com.example.fithelper.Repositories.WorkoutRepository
import com.example.fithelper.Services.UserService
import java.util.UUID.randomUUID

class CreateWorkoutViewModel : ViewModel() {

    val name = MutableLiveData<String>()
    val dateInMilliseconds = MutableLiveData<Long>()
    val exercises = MutableLiveData<MutableList<Exercise>>()

    fun create() {
        val workout = Workout(
            randomUUID().toString(),
            UserService.getUserId(),
            name.value,
            dateInMilliseconds.value,
            exercises.value
        )

        WorkoutRepository.createWorkout(workout)
    }

    fun initialiseExerciseList(){
        exercises.value = mutableListOf()
    }

    fun setName(name: String?) {
        this.name.value = name
    }

    fun setDate(date: Long?) {
        this.dateInMilliseconds.value = date
    }

    fun addExercise(exercise: Exercise){
        this.exercises.value?.add(exercise)
    }
}