package com.example.fithelper

import com.example.fithelper.Models.Workout

interface OnWorkoutItemClickListener {
    fun getDetails(position: Int)
    fun deleteById(workoutId: String)
}