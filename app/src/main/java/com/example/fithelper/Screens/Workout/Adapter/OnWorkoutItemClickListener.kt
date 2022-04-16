package com.example.fithelper.Screens.Workout.Adapter

import com.example.fithelper.Models.Workout

interface OnWorkoutItemClickListener {
    fun getDetails(workout: Workout)
    fun deleteById(workoutId: String)
}