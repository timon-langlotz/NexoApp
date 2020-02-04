package com.adyen.nexoapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val UTC_TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss+00:00"
private val dateFormat by lazy {
    SimpleDateFormat(UTC_TIMESTAMP_FORMAT, Locale.ROOT)
        .apply { timeZone = TimeZone.getTimeZone("UTC") }
}

fun Date.toUtcTimeStamp(): String {
    return dateFormat.format(this)
}
