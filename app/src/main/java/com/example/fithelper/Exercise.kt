package com.example.fithelper

data class Exercise(val name: String? = null,
               var numberOfSets: Int? = null,
               var numberOfReps: Int? = null,
               var weight: Int? = null,
               @field:JvmField
               var isComplete: Boolean? = null) {

    override fun toString(): String {
        return name ?: ""
    }

}