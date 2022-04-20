package com.example.fithelper.Screens.Shared

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithelper.Extensions.*
import com.example.fithelper.Models.Workout
import com.example.fithelper.Repositories.WorkoutRepository
import com.example.fithelper.Services.UserService
import com.google.firebase.firestore.DocumentChange

class WorkoutsViewModel : ViewModel() {
    private val userId by lazy { UserService.getUserId() }

    val workouts = MutableLiveData<MutableList<Workout>>()

    init {
        workouts.value = mutableListOf()

        addSnapshotListener()
    }

    fun deleteWorkout(workoutId: String) {
        WorkoutRepository.deleteWorkoutByUser(workoutId)
    }

    private fun addSnapshotListener() =
        WorkoutRepository.getWorkoutByUserId(userId)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("Database", e.message.toString())
                    return@addSnapshotListener
                }

                if (snapshots == null)
                    return@addSnapshotListener

                for (dc in snapshots.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            val workout = dc.document.toObject(Workout::class.java)
                            workouts.value?.add(workout)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val workout = dc.document.toObject(Workout::class.java)
                            workouts.value?.removeAll { w -> w.id == workout.id }
                            workouts.value?.add(workout)
                        }
                        DocumentChange.Type.REMOVED -> {
                            val workout = dc.document.toObject(Workout::class.java)
                            workouts.value?.remove(workout)
                        }
                    }
                }

                workouts.value?.sortBy { w -> w.dateInMilliseconds }
                workouts.notifyObserver()
            }
}