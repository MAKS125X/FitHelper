package com.example.fithelper.screens.common.createExerciseDialogFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fithelper.screens.common.createExerciseDialogFragment.CreateExerciseDialogFragment.OnExerciseCreatedListener

class CreateExerciseFactory(private val listener: OnExerciseCreatedListener?) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(OnExerciseCreatedListener::class.java)
            .newInstance(listener)
    }
}