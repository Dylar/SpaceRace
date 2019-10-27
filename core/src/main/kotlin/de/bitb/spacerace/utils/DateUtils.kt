package de.bitb.spacerace.utils

import java.text.SimpleDateFormat
import java.util.*

const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm"
const val TIME_PATTERN = "HH:mm:ss:SSS"
val DATE_FORMATTER = SimpleDateFormat(DATE_PATTERN)
val TIME_FORMATTER = SimpleDateFormat(TIME_PATTERN)

fun Calendar.getDateString() = DATE_FORMATTER.format(time)
fun Calendar.getTimeString() = TIME_FORMATTER.format(time)
fun timestampNowDate():String = Calendar.getInstance().getDateString()
fun timestampNow():String = Calendar.getInstance().getTimeString()