package com.todo.api.request

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(val id: Int, val firstname: String, val lastname: String, val email: String)

@Serializable
data class UpdateUserRequest(val firstname: String, val lastname: String, val email: String)

@Serializable
data class AddUserRequest(val firstname: String, val lastname: String, val email: String)
