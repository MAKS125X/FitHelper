package com.example.fithelper.models

import java.lang.IllegalArgumentException

data class Exercise(
    val name: String? = null,
    var numberOfApproaches: Int? = null,
    var numberOfRepetitions: Int? = null,
    var weight: Int? = null,
    @field:JvmField
    var isComplete: Boolean? = null
) {
    constructor(
        name: String,
        numberOfApproaches: Int,
        numberOfRepetitions: Int,
        weight: Int
    ) : this(name, numberOfApproaches, numberOfRepetitions, weight, false) {
        when {
            name.isNullOrEmpty() -> throw IllegalArgumentException("Упражнение должно содержать название.")
            numberOfApproaches <= 0 -> throw IllegalArgumentException("Число подходов должно быть положительным!")
            numberOfRepetitions <= 0 -> throw IllegalArgumentException("Число повторений должно быть положительным!")
        }
    }


    override fun toString(): String {
        return name ?: "Упражнение"
    }
}