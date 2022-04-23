package com.example.fithelper.models

import java.lang.IllegalArgumentException

data class Exercise(
    val name: String? = null,
    var numberOfApproaches: Int = 1,
    var numberOfRepetitions: Int = 1,
    var weight: Int? = null,
    @field:JvmField
    var isComplete: Boolean = false
) {

    init {
        when {
            numberOfApproaches <= 0 -> throw IllegalArgumentException("Число подходов должно быть положительным!")
            numberOfRepetitions <= 0 -> throw IllegalArgumentException("Число повторений должно быть положительным!")
        }
    }

    override fun toString(): String {
        return name ?: "Упражнение"
    }
}