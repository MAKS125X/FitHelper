package com.example.fithelper.repositories

import android.util.Log
import com.example.fithelper.models.Workout
import com.google.firebase.firestore.FirebaseFirestore

object WorkoutRepository {
    private val workouts = FirebaseFirestore.getInstance().collection("Workouts")

    fun getWorkoutByUserId(userId: String) =
        workouts.whereEqualTo("userId", userId)


    fun createWorkout(workout: Workout) =
        workouts
            .document(workout.id)
            .set(workout)
            .addOnSuccessListener {
                Log.i("Workout", "Workout ${workout.id} created")
            }
            .addOnFailureListener { ex ->
                Log.e("Database", ex.message.toString())
            }


    fun updateWorkout(workout: Workout) =
        workouts.document(workout.id)
            .set(workout)
            .addOnSuccessListener {
                Log.i("Workout", "Workout ${workout.id} updated")
            }
            .addOnFailureListener { ex ->
                Log.e("Database", ex.message.toString())
            }


    fun deleteWorkoutByUser(workoutId: String) =
        workouts.document(workoutId)
            .delete()
            .addOnSuccessListener {
                Log.i("Workout", "Workout $workoutId deleted")
            }
            .addOnFailureListener { ex ->
                Log.e("Database", ex.message.toString())
            }
}