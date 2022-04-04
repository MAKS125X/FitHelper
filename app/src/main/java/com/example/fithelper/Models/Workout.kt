package com.example.fithelper.Models

import java.util.*

data class Workout(
    var id: String? = null,
    var userId: String? = null,
    var name: String? = null,
    var dateInMilliseconds: Long? = null,
    var exerciseList: MutableList<Exercise>? = null
) {
    constructor(
        userId: String? = null,
        name: String? = null,
        dateInMilliseconds: Long? = null,
        exerciseList: MutableList<Exercise>? = null
    ) : this(UUID.randomUUID().toString(), userId, name, dateInMilliseconds, exerciseList) {
        if (name.isNullOrEmpty())
            throw IllegalArgumentException("Тренировка должна содержать название.")
    }

    override fun toString(): String {
        return name ?: "Тренировка"
    }
}