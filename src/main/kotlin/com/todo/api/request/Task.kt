package com.todo.api.request

import kotlinx.serialization.Serializable

@Serializable
data class TaskRequest(val id: Int, var title: String, var date: String, var status: Int, var is_active: Boolean)

@Serializable
data class UpdateTaskRequest(val title: String, val status: Int, val is_active: Boolean)

@Serializable
data class AddTaskRequest(val title: String, val status: Int, val is_active: Boolean, val user_id: Int)
