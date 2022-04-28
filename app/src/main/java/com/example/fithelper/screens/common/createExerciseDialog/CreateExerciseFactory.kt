package com.example.fithelper.screens.common.createExerciseDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fithelper.screens.common.createExerciseDialog.CreateExerciseDialogFragment.OnExerciseCreatedListener

class CreateExerciseFactory(private val listener: OnExerciseCreatedListener?) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(OnExerciseCreatedListener::class.java)
            .newInstance(listener)
    }
}