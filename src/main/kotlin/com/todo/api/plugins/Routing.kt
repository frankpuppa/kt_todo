package com.todo.api.plugins

import com.todo.api.routing.taskRouting
import com.todo.api.routing.userRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        userRouting()
        taskRouting()
    }
}
