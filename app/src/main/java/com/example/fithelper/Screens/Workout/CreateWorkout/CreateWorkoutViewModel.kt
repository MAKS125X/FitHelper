package com.example.fithelper.Screens.Workout.CreateWorkout

import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.Extensions.*
import com.example.fithelper.Models.Exercise
import com.example.fithelper.Models.Workout
import com.example.fithelper.Repositories.WorkoutRepository
import com.example.fithelper.Services.UserService
import java.util.*

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

    fun create(exercises: MutableList<Exercise>?) {
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

