package com.todo.api.response

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(val id: Int, var title: String, var date: String, var status: Int, var is_active: Boolean)
