package com.example.fithelper

class Workout(var name: String?,
              var dateInMilliseconds: Long?,
              var exerciseList: MutableList<Exercise>?){

    override fun toString(): String {
        return name ?: ""
    }


}