package com.example.fithelper.extensions

fun Int.add(value: Int, lowerBound: Int): Int {
    val result = this + value
    return if (result < lowerBound) lowerBound else result
}