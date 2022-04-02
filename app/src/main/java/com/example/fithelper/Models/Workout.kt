package com.example.fithelper.Models

data class Workout(
    var id: String? = null,
    var userId: String? = null,
    var name: String? = null,
    var dateInMilliseconds: Long? = null,
    var exerciseList: MutableList<Exercise>? = null
) {
    override fun toString(): String {
        return name ?: "Тренировка"
    }
}