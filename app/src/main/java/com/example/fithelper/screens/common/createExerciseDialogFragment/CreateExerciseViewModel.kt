package com.example.fithelper.screens.common.createExerciseDialogFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.models.Exercise
import java.lang.IllegalArgumentException

class CreateExerciseViewModel(private var listener: CreateExerciseDialogFragment.OnExerciseCreatedListener? = null) :
    ViewModel() {

    val name = MutableLiveData("")
    val numberOfApproaches = MutableLiveData(1)
    val numberOfRepetitions = MutableLiveData(1)
    val weight = MutableLiveData(0)

    fun setName(value: String) {
        name.value = value
    }

    fun setNumberOfApproaches(value: Int?) {
        numberOfApproaches.value = value
    }

    fun setNumberOfRepetitions(value: Int?) {
        numberOfRepetitions.value = value
    }

    fun setWeight(value: Int?) {
        weight.value = value
    }

    fun completeCreateExercise() {
        val numberOfApproaches = numberOfApproaches.value
            ?: throw IllegalArgumentException("Количество подходов не может быть null")
        val numberOfRepetitions = numberOfRepetitions.value
            ?: throw IllegalArgumentException("Количество повторений не может быть null")

        val exercise = Exercise(
            name.value,
            numberOfApproaches,
            numberOfRepetitions,
            weight.value,
            isComplete = false
        )

        listener?.onExerciseCreated(exercise)
    }
}