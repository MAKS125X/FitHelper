package com.example.fithelper.Extensions

import java.text.SimpleDateFormat
import java.util.*

fun convertDateToLong(date: String, dateFormat: String?): Long {
    val df = SimpleDateFormat(dateFormat)
    return df.parse(date).time
}

fun getStringDateFromLong(dateInMilliseconds: Long?, dateFormat: String?): String? {
    if(dateInMilliseconds == null)
        return null

    val date = Date(dateInMilliseconds)
    val format = SimpleDateFormat(dateFormat)

    return format.format(date)
}