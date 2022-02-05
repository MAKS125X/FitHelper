package com.example.fithelper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ExerciseViewModel : ViewModel(){

    val exercise: MutableLiveData<Exercise> by lazy {
        MutableLiveData<Exercise>()
    }

    val workout: MutableLiveData<Workout> by lazy {
        MutableLiveData<Workout>()
    }

}