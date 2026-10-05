package com.example.lifffter.feature_dashboard.presentation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.SimpleTimeZone

@SuppressLint("SimpleDateFormat")
fun convertToDate(timeStamp: Long): String {
    return SimpleDateFormat("MMM dd, yyyy").format(timeStamp)
}

fun convertToDuration(durationMillis: Long): String {
    val totalMinutes = durationMillis / 1000 / 60
    return "${totalMinutes}m"
}

fun convertToTime(timeStamp: Long): String {
    return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(timeStamp)
}