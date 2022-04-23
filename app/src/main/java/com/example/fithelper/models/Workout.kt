package com.example.fithelper.models

import java.util.*

data class Workout(
    var id: String = "",
    var userId: String = "",
    var name: String? = null,
    var dateInMilliseconds: Long? = null,
    var exerciseList: MutableList<Exercise> = mutableListOf()
) {
    constructor(userId: String, name: String?, dateInMilliseconds: Long? = null, exerciseList: MutableList<Exercise> = mutableListOf()
    ) : this(UUID.randomUUID().toString(), userId, name, dateInMilliseconds, exerciseList)

    override fun toString(): String {
        return name ?: "Тренировка"
    }
}