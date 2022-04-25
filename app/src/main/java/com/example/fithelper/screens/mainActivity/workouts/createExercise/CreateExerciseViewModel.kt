package com.example.fithelper.screens.mainActivity.workouts.createExercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.extensions.*

class CreateExerciseViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    val numberOfApproaches = MutableLiveData<Int>()
    val numberOfRepetitions = MutableLiveData<Int>()
    val weight = MutableLiveData<Int>()

    init {
        name.value = ""
        numberOfApproaches.value = 1
        numberOfRepetitions.value = 1
        weight.value = 0
    }

    fun setName(name: String) {
        this.name.value = name
    }

    fun changeNumberOfApproaches(value: Int) {
        numberOfApproaches.change(value, lowerBound = 1)
    }

    fun changeNumberOfRepetitions(value: Int) {
        numberOfRepetitions.change(value, lowerBound = 1)
    }

    fun changeWeight(value: Int) {
        numberOfRepetitions.change(value, lowerBound = 0)
    }
}


