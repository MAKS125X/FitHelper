package com.example.fithelper.Screens.Exercise.CreateExercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateExerciseViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    val numberOfApproaches = MutableLiveData<Int>()
    val numberOfRepetitions = MutableLiveData<Int>()
    val weight = MutableLiveData<Int>()

    init {
        name.value = ""
        numberOfApproaches.value = 0
        numberOfRepetitions.value = 0
        weight.value = 0
    }

    fun setName(name: String) {
        this.name.value = name
    }

    fun decreaseNumberOfApproaches() {
        numberOfApproaches.dec()
    }
    fun increaseNumberOfApproaches() {
        numberOfApproaches.inc()
    }

    fun decreaseNumberOfRepetitions() {
        numberOfRepetitions.dec()
    }
    fun increaseNumberOfRepetitions() {
        numberOfRepetitions.inc()
    }
    fun fastDecreaseNumberOfRepetitions() {
        numberOfRepetitions.minus(5, 0)
    }
    fun fastIncreaseNumberOfRepetitions() {
        numberOfRepetitions.plus(5)
    }

    fun decreaseWeight() {
        weight.dec()
    }
    fun increaseWeight() {
        weight.inc()
    }
    fun fastDecreaseWeight() {
        weight.minus(5, 0)
    }
    fun fastIncreaseWeight() {
        weight.plus(5)
    }
}

fun MutableLiveData<Int>.inc() {
    this.value = this.value?.inc()
}

fun MutableLiveData<Int>.dec(lowerBound: Int = 0) {
    this.value = this.value?.dec()

    if (this.value!! < lowerBound)
        this.value = lowerBound
}

fun MutableLiveData<Int>.minus(value: Int, lowerBound: Int) {
    this.value = this.value?.minus(value)

    if (this.value!! < lowerBound)
        this.value = lowerBound
}

fun MutableLiveData<Int>.plus(value: Int) {
    this.value = this.value?.plus(value)
}
