package com.todo.api.routing

import com.todo.api.controllers.tasks.*
import com.todo.api.request.*
import com.todo.api.response.ErrorResponse
import com.todo.api.response.HttpResponse
import com.todo.shared_types.exceptions.BadRequestError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.taskRouting() {
    val taskController by inject<TaskController>()
    route("/tasks") {
        get {
            try {
                val tasks = taskController.getTasks()
                call.respond(HttpStatusCode.OK, tasks)
            } catch (e: Exception) {
                val response: HttpResponse<ErrorResponse> = mapError(e)
                call.respond(response.code, response.body)
            }
        }
        get("/{id?}") {
            try {
                val id = call.parameters["id"]?.toInt() ?: throw BadRequestError("invalid id")
                val task = taskController.getTask(id)
                call.respond(HttpStatusCode.OK, task)
            } catch (e: Exception) {
                val response: HttpResponse<ErrorResponse> = mapError(e)
                call.respond(response.code, response.body)
            }
        }
        put("/{id?}") {
            try {
                val id: Int = call.parameters["id"]?.toInt() ?: throw BadRequestError("invalid id")
                val updateTask = call.receive<UpdateTaskRequest>()
                val task = taskController.updateTask(id, updateTask)
                call.respond(HttpStatusCode.OK, task)
            } catch (e: Exception) {
                val response: HttpResponse<ErrorResponse> = mapError(e)
                call.respond(response.code, response.body)
            }
        }

        post {
            try {
                val addTask = call.receive<AddTaskRequest>()
                val task = taskController.addTask(addTask)
                call.respond(HttpStatusCode.OK, task)
            } catch (e: Exception) {
                val response: HttpResponse<ErrorResponse> = mapError(e)
                call.respond(response.code, response.body)
            }
        }

        delete("/{id?}") {
            try {
                val id = call.parameters["id"]?.toInt() ?: throw BadRequestError("invalid id")
                taskController.deleteTask(id)
                call.respond(HttpStatusCode.OK)
            } catch (e: Exception) {
                val response: HttpResponse<ErrorResponse> = mapError(e)
                call.respond(response.code, response.body)
            }
        }
    }

    route("/users/{id?}/tasks") {
        get {
            try {
                val uId = call.parameters["id"]?.toInt() ?: throw BadRequestError("invalid id")
                val tasks = taskController.getUserTasks(uId)
                call.respond(HttpStatusCode.OK, tasks)
            } catch (e: Exception) {
                val response: HttpResponse<ErrorResponse> = mapError(e)
                call.respond(response.code, response.body)
            }
        }
    }
}
