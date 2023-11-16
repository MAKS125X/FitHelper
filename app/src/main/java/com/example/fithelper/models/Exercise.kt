package com.example.fithelper.models

class Exercise(
    val name: String? = null,
    numberOfApproaches: Int = 1,
    numberOfRepetitions: Int = 1,
    weight: Int? = null,
    @field:JvmField
    var isComplete: Boolean = false
) {

    var numberOfApproaches: Int = numberOfApproaches
        set(value) {
            if (value < LOWER_BOUND_APPROACHES)
                throw IllegalArgumentException("Число подходов должно быть положительным!")

            field = value
        }

    var numberOfRepetitions: Int = numberOfRepetitions
        set(value) {
            if (value < LOWER_BOUND_REPETITIONS)
                throw IllegalArgumentException("Число повторений должно быть положительным!")

            field = value
        }

    var weight: Int? = weight
        set(value) {
            if (value != null && value < LOWER_BOUND_WEIGHT)
                throw IllegalArgumentException("Вес должен быть не отрицательным!")

            field = value
        }

    init {
        when {
            numberOfApproaches < LOWER_BOUND_APPROACHES -> throw IllegalArgumentException("Число подходов должно быть положительным!")
            numberOfRepetitions < LOWER_BOUND_REPETITIONS -> throw IllegalArgumentException("Число повторений должно быть положительным!")
            weight != null && weight < LOWER_BOUND_WEIGHT -> throw IllegalArgumentException("Вес должен быть не отрицательным!")
        }
    }

    override fun toString(): String {
        return name ?: ""
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Exercise) {
            name == other.name &&
                    numberOfApproaches == other.numberOfApproaches &&
                    numberOfRepetitions == other.numberOfRepetitions &&
                    isComplete == other.isComplete &&
                    weight == other.weight
        } else
            false
    }

    companion object {
        const val LOWER_BOUND_WEIGHT = 0
        const val LOWER_BOUND_APPROACHES = 1
        const val LOWER_BOUND_REPETITIONS = 1
    }
}