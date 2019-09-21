package de.bitb.spacerace.utils

import java.text.SimpleDateFormat
import java.util.*

const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm"
val DATE_FORMATTER = SimpleDateFormat(DATE_PATTERN)

fun Calendar.getDate() = DATE_FORMATTER.format(time)