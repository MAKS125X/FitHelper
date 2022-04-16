package com.example.fithelper.Screens.Workout.Workouts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.Models.Exercise
import com.example.fithelper.Models.Workout
import com.example.fithelper.Repositories.WorkoutRepository
import com.example.fithelper.Screens.Shared.notifyObserver
import com.example.fithelper.Services.UserService

class WorkoutsViewModel : ViewModel() {
    val workouts = MutableLiveData<MutableList<Workout>>()

    init {
        workouts.value = mutableListOf()

        initializeWorkouts()
    }

    fun initializeWorkouts() {
        val userId = UserService.getUserId()
        WorkoutRepository.getWorkoutByUserId(userId)
            .get()
            .addOnSuccessListener {  snapshot ->
                for (workout in snapshot) {
                    workouts.value?.add(workout.toObject(Workout::class.java))
                }

                Log.i("Workouts", "Workouts get by $userId")
                workouts.notifyObserver()
            }
            .addOnFailureListener { ex ->
                Log.e("Database", ex.message.toString())
            }
    }
}