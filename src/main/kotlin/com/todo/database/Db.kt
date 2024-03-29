package com.todo.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.util.logging.*
import java.sql.Connection as SQLConnection

val logger = KtorSimpleLogger("utils")

fun setupDbConnection(
    db_host: String,
    db_port: String,
    db_name: String,
    db_user: String,
    db_psw: String,
): SQLConnection {
    val config = HikariConfig()
    config.maximumPoolSize = 5
    config.jdbcUrl = "jdbc:postgresql://$db_host:$db_port/$db_name"
    config.username = db_user
    config.password = db_psw
    val dataSource = HikariDataSource(config)
    logger.info("connecting to db: ${dataSource.jdbcUrl}")
    return dataSource.connection
}
