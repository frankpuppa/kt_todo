package com.todo.api

import com.todo.api.plugins.*
import com.todo.database.setupDbConnection
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.logging.*

val dotenv = dotenv()
val logger = KtorSimpleLogger("main")
var server: io.ktor.server.netty.NettyApplicationEngine? = null

fun main() {
    val port = dotenv["APP_PORT"]?.toInt() ?: 8080
    val host = dotenv["APP_HOST"] ?: "127.0.0.1"

    server = embeddedServer(Netty, port = port, host = host, module = Application::module)
    server?.start(wait = true)
}

fun Application.module() {
    val dbPort = dotenv["DB_PORT"] ?: "5432"
    val dbHost = dotenv["DB_HOST"] ?: "127.0.0.1"
    val dbName = dotenv["DB_NAME"] ?: "todo"
    val dbUser = dotenv["DB_USER"] ?: "todo"
    val dbPsw = dotenv["DB_PSW"] ?: "todo"

    val dbConn = setupDbConnection(dbHost, dbPort, dbName, dbUser, dbPsw)
    logger.info("db successfully connected")
    configureRouting()
    configureOpenAPI()
    configureKoin(dbConn)
    configureSerialization()
}
