package com.example.fithelper

data class Workout(var name: String? = null,
              var dateInMilliseconds: Long? = null,
              var exerciseList: MutableList<Exercise>? = null){
    //constructor() : this (null, null, null)

    override fun toString(): String {
        return name ?: ""
    }
    
}