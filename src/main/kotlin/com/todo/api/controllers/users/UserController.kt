package com.todo.api.controllers.users

import com.todo.api.request.*
import com.todo.api.response.UserResponse
import com.todo.database.repositories.UserRepository
import com.todo.shared_types.exceptions.*
import com.todo.shared_types.mappers.UserMapper
import com.todo.shared_types.mappers.mapAddUserRequestToAddUser
import com.todo.shared_types.mappers.mapUserToUserResponse
import com.todo.shared_types.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.logging.*

class UserController(val userRepo: UserRepository) {
    internal val logger = KtorSimpleLogger("user_logger")
    internal val userMapper = UserMapper()

    fun getUsers(): List<UserResponse> {
        logger.info("getting all users")
        val users = userRepo.getUsers()
        val usersR = users.map { userMapper.mapUserToUserResponse(it) }
        return usersR
    }

    fun getUser(id: Int): UserResponse {
        logger.info("getting user with id: ($id)")
        val user = userRepo.getUserById(id)
        return userMapper.mapUserToUserResponse(user)
    }

    fun updateUser(
        id: Int,
        updateUser: UpdateUserRequest,
    ): UserResponse {
        logger.info("updating user with id: ($id) with data: $updateUser")
        val currentUser = userRepo.getUserById(id)
        val newUser =
            User(
                id = currentUser.id,
                firstname = updateUser.firstname,
                lastname = updateUser.lastname,
                email = updateUser.email,
            )
        val user = userRepo.updateUser(newUser)
        return userMapper.mapUserToUserResponse(user)
    }

    fun deleteUser(id: Int) {
        logger.info("deleting user with id: $id")
        userRepo.deleteUser(id)
    }

    fun addUser(newUserRequest: AddUserRequest): UserResponse {
        logger.info("add new user: ")
        val newUser = userMapper.mapAddUserRequestToAddUser(newUserRequest)
        val user = userRepo.addUser(newUser)
        return userMapper.mapUserToUserResponse(user)
    }
}
