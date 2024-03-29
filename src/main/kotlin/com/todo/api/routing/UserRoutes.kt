package com.todo.api.routing

import com.todo.api.controllers.users.*
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

fun Route.userRouting() {
    val userController by inject<UserController>()
    route("/users") {
        get {
            try {
                val tasks = userController.getUsers()
                call.respond(HttpStatusCode.OK, tasks)
            } catch (e: Exception) {
                val response: HttpResponse<ErrorResponse> = mapError(e)
                call.respond(response.code, response.body)
            }
        }
        get("/{id?}") {
            try {
                val id = call.parameters["id"]?.toInt() ?: throw BadRequestError("invalid id")
                val task = userController.getUser(id)
                call.respond(HttpStatusCode.OK, task)
            } catch (e: Exception) {
                val response: HttpResponse<ErrorResponse> = mapError(e)
                call.respond(response.code, response.body)
            }
        }
        put("/{id?}") {
            try {
                val id: Int = call.parameters["id"]?.toInt() ?: throw BadRequestError("invalid id")
                val updateUser = call.receive<UpdateUserRequest>()
                val task = userController.updateUser(id, updateUser)
                call.respond(HttpStatusCode.OK, task)
            } catch (e: Exception) {
                val response: HttpResponse<ErrorResponse> = mapError(e)
                call.respond(response.code, response.body)
            }
        }

        post {
            try {
                val addUser = call.receive<AddUserRequest>()
                val task = userController.addUser(addUser)
                call.respond(HttpStatusCode.OK, task)
            } catch (e: Exception) {
                val response: HttpResponse<ErrorResponse> = mapError(e)
                call.respond(response.code, response.body)
            }
        }

        delete("/{id?}") {
            try {
                val id = call.parameters["id"]?.toInt() ?: throw BadRequestError("invalid id")
                userController.deleteUser(id)
                call.respond(HttpStatusCode.OK)
            } catch (e: Exception) {
                val response: HttpResponse<ErrorResponse> = mapError(e)
                call.respond(response.code, response.body)
            }
        }
    }
}
