package com.example.fithelper.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Workout(
    var id: String = "",
    var userId: String = "",
    var name: String? = null,
    var dateInMilliseconds: Long? = null,
    var exerciseList: MutableList<Exercise> = mutableListOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
        exerciseList = mutableListOf()
        parcel.readList(exerciseList, Exercise::class.java.classLoader)
    }

    constructor(userId: String, name: String?, dateInMilliseconds: Long? = null, exerciseList: MutableList<Exercise> = mutableListOf()
    ) : this(UUID.randomUUID().toString(), userId, name, dateInMilliseconds, exerciseList)

    override fun toString(): String {
        return name ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(userId)
        parcel.writeString(name)
        parcel.writeValue(dateInMilliseconds)
        parcel.writeList(exerciseList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Workout> {
        override fun createFromParcel(parcel: Parcel): Workout {
            return Workout(parcel)
        }

        override fun newArray(size: Int): Array<Workout?> {
            return arrayOfNulls(size)
        }
    }
}