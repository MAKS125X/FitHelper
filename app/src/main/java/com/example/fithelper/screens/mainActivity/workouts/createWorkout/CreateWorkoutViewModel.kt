package com.example.fithelper.screens.mainActivity.workouts.createWorkout

import android.app.DatePickerDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.models.Exercise
import com.example.fithelper.models.Workout
import com.example.fithelper.repositories.WorkoutRepository
import com.example.fithelper.services.UserService

class CreateWorkoutViewModel(datePickerDialog: DatePickerDialog) : ViewModel() {
    private val dpd: DatePickerDialog = datePickerDialog

    val name = MutableLiveData<String>()
    val dateInMilliseconds = MutableLiveData<Long?>()

    init {
        name.value = ""
        dateInMilliseconds.value = null
    }

    fun setName(name: String?) {
        this.name.value = name
    }

    fun setDate(date: Long?) {
        this.dateInMilliseconds.value = date
    }

    fun create(exercises: MutableList<Exercise>) {
        val workout = Workout(
            UserService.getUserId(),
            name.value,
            dateInMilliseconds.value,
            exercises
        )

        WorkoutRepository.createWorkout(workout)
    }

    fun changeDate() {
        dpd.show()
    }
}

