package com.todo.shared_types.models
import com.todo.utils.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

data class Task(val id: Int, val title: String, val status: Int, val is_active: Boolean, val user_id: Int, val date: Timestamp)

fun Task.getCurrentTimestamp(): Timestamp {
    return getCurrentDateAsTimestamp()
}

fun Task.timestampToDateString(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val parsedDate = dateFormat.format(Date(this.date.getTime()))
    return parsedDate
}

data class AddTask(val title: String, val status: Int, val is_active: Boolean, val user_id: Int) {
    val date: Timestamp

    init {
        date = getCurrentDateAsTimestamp()
    }
}
