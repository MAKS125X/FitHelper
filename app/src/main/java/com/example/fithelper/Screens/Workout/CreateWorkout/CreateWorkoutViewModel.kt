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

class CreateWorkoutViewModel : ViewModel() {

    val name = MutableLiveData<String>()
    val dateInMilliseconds = MutableLiveData<Long?>()

    init {
        name.value = ""
    }

    fun setName(name: String?) {
        this.name.value = name
    }

    fun setDate(date: Long?) {
        this.dateInMilliseconds.value = null
    }

    fun create(exercises: MutableList<Exercise>?) {
        val workout = Workout(
            UserService.getUserId(),
            name.value,
            dateInMilliseconds.value,
            exercises
        )

        WorkoutRepository.createWorkout(workout)
        this.onCleared()
    }

    fun changeDate(context: Context) {
        val calendar = Calendar.getInstance()

        // todo: пересмотреть логику по касту
        val dpd = DatePickerDialog(
            context,
            { _, yearDate, monthOfYear, dayOfMonth ->
                val day1 = if (dayOfMonth / 10 == 0) "0${dayOfMonth}" else "${dayOfMonth}"
                val month1 =
                    if ((monthOfYear + 1) / 10 == 0) "0${monthOfYear + 1}" else "${monthOfYear + 1}"

                val dateString = "$day1.$month1.$yearDate"
                val date = convertDateToLong(dateString, "dd.MM.yyyy")
                this.setDate(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dpd.show()
    }
}

