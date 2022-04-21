package com.example.fithelper.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

fun MutableLiveData<Int>.inc() {
    this.value = this.value?.inc()
}

fun MutableLiveData<Int>.dec(lowerBound: Int = 0) {
    this.value = this.value?.dec()

    if (this.value!! < lowerBound)
        this.value = lowerBound
}

fun MutableLiveData<Int>.minus(value: Int, lowerBound: Int) {
    this.value = this.value?.minus(value)

    if (this.value!! < lowerBound)
        this.value = lowerBound
}

fun MutableLiveData<Int>.plus(value: Int) {
    this.value = this.value?.plus(value)
}