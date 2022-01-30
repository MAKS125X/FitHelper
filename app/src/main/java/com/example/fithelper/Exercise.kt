package com.example.fithelper

class Exercise(val name: String? = null,
               var numberOfSets: Int? = null,
               var numberOfReps: Int? = null,
               var weight: Int? = null) {

    override fun toString(): String {
        return name ?: ""
    }

}