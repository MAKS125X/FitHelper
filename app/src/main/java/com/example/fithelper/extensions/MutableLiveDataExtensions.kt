package com.example.fithelper.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

fun MutableLiveData<Int>.change(value: Int, lowerBound: Int) {
    this.value = this.value?.minus(value)

    if (this.value ?: lowerBound < lowerBound)
        this.value = lowerBound
}