package com.example.fithelper.screens.mainActivity.workouts.adapters.workoutAdapter

import com.example.fithelper.models.Workout

interface OnWorkoutItemClickListener {
    fun getDetails(workout: Workout)
    fun deleteById(workoutId: String)
}