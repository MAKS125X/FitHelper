package com.example.fithelper.Repositories

import android.util.Log
import com.example.fithelper.Models.Workout
import com.google.firebase.firestore.FirebaseFirestore

object WorkoutRepository {
    private val workouts = FirebaseFirestore.getInstance().collection("Workouts")

    fun getWorkoutByUserId(userId: String): MutableList<Workout> {
        val result = mutableListOf<Workout>()
        workouts.whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { snapshot ->
                for (workout in snapshot) {
                    result.add(workout.toObject(Workout::class.java))
                }

                Log.i("Database", "Workouts get by $userId")
            }
            .addOnFailureListener { ex ->
                Log.e("Database", ex.message.toString())
            }

        return result
    }

    fun createWorkout(workout: Workout) {
        workouts
            .document(workout.id!!)
            .set(workout)
            .addOnSuccessListener {
                Log.i("Database", "Workout created")
            }
            .addOnFailureListener { ex ->
                Log.e("Database", ex.message.toString())
            }
    }

    fun updateWorkout(workout: Workout) {
        workouts.document(workout.id!!)
            .set(workout)
    }

    fun deleteWorkoutByUser(workoutId: String) {
        workouts.document(workoutId).delete()
    }

}