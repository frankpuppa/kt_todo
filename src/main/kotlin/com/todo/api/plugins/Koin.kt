package com.todo.api.plugins

import com.todo.api.controllers.tasks.*
import com.todo.api.controllers.users.*
import com.todo.database.repositories.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.ktor.plugin.*
import org.koin.logger.slf4jLogger

fun Application.configureKoin(dbConn: java.sql.Connection) {
    install(Koin) {
        slf4jLogger(level = Level.ERROR)
        modules(
            module {
                single<UserController> { UserController(UserRepository(dbConn)) }
                single<TaskController> { TaskController(TaskRepository(dbConn), UserRepository(dbConn)) }
            },
        )
    }
}
