package com.example.fithelper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.Models.Exercise
import com.example.fithelper.Models.Workout

open class WorkoutViewModel : ViewModel(){

    val exercise: MutableLiveData<Exercise> by lazy {
        MutableLiveData<Exercise>()
    }

    val createdWorkout: MutableLiveData<Workout> by lazy {
        MutableLiveData<Workout>()
    }

    val changedWorkout: MutableLiveData<Workout> by lazy {
        MutableLiveData<Workout>()
    }

}