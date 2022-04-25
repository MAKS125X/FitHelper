package com.example.fithelper.extensions

import java.text.SimpleDateFormat
import java.util.*

fun getStringDateFromLong(dateInMilliseconds: Long?, dateFormat: String?): String? {
    if(dateInMilliseconds == null)
        return ""

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = dateInMilliseconds

    val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
    return sdf.format(calendar.time)
}