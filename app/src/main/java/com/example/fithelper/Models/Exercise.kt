package com.example.fithelper.Models

data class Exercise(val name: String? = null,
                    var numberOfApproaches: Int? = null,
                    var numberOfRepetitions: Int? = null,
                    var weight: Int? = null,
                    @field:JvmField
               var isComplete: Boolean? = null) {

    override fun toString(): String {
        return name ?: "Упражнение"
    }

}