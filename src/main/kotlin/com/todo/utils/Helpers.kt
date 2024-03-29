package com.todo.utils

import io.ktor.util.logging.KtorSimpleLogger
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val logger = KtorSimpleLogger("utils")

fun getCurrentDate(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val current = LocalDateTime.now().format(formatter)
    return current
}

fun getCurrentDateAsTimestamp(): Timestamp {
    val timestamp = Timestamp.from(Instant.now())
    return timestamp
}

fun getDateTimeAsTimestamp(datetime: String): java.sql.Timestamp {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val parsedDate = dateFormat.parse(datetime)
    val timestamp = java.sql.Timestamp(parsedDate.getTime())
    return timestamp
}
