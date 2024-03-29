package com.todo.api.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(val id: Int, val firstname: String, val lastname: String, val email: String)
