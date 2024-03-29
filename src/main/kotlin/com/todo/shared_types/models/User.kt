package com.todo.shared_types.models

data class User(val id: Int, val firstname: String, val lastname: String, val email: String)

data class AddUser(val firstname: String, val lastname: String, val email: String)

// public val userStorage = mutableListOf<User>()
// val userStorage = listOf(
//    User(1, "tony", "test1", "test@test.com"),
//    User(2, "tony2", "test2", "test2@test.com")
// )
