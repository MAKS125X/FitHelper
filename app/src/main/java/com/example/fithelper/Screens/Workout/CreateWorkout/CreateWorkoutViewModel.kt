package com.example.fithelper.Screens.Workout.CreateWorkout

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.Models.Exercise
import com.example.fithelper.Models.Workout
import com.example.fithelper.Repositories.WorkoutRepository
import com.example.fithelper.Screens.Exercise.CreateExercise.CreateExerciseFragment
import com.example.fithelper.Services.UserService
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*
import java.util.UUID.randomUUID

class CreateWorkoutViewModel : ViewModel() {

    val name = MutableLiveData<String>()
    val dateInMilliseconds = MutableLiveData<Long>()
    val exercises = MutableLiveData<MutableList<Exercise>>()

    init {
        name.value = ""
        exercises.value = mutableListOf()
    }

    fun setName(name: String?) {
        this.name.value = name
    }

    fun setDate(date: Long?) {
        this.dateInMilliseconds.value = date
    }

    fun addExercise(exercise: Exercise) {
        this.exercises.value?.add(exercise)
    }

    fun create() {
        val workout = Workout(
            UserService.getUserId(),
            name.value,
            dateInMilliseconds.value,
            exercises.value
        )

        WorkoutRepository.createWorkout(workout)
        exercises.value?.clear()
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
                val date = convertDateToLong(dateString)
                this.setDate(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dpd.show()
    }
    //todo: пересмотреть логику по касту
    private fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("dd.MM.yyyy")
        return df.parse(date).time
    }


}

