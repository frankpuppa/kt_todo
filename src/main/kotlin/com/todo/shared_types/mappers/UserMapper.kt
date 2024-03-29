package com.todo.shared_types.mappers

import com.todo.api.request.AddUserRequest
import com.todo.api.response.UserResponse
import com.todo.shared_types.models.AddUser
import com.todo.shared_types.models.User

class UserMapper

fun UserMapper.mapAddUserRequestToAddUser(addUserRequest: AddUserRequest): AddUser {
    return AddUser(
        firstname = addUserRequest.firstname,
        lastname = addUserRequest.lastname,
        email = addUserRequest.email,
    )
}

// fun UserMapper.mapUpdateUserRequestToUpdateUser(updateUserRequest:UpdateUserRequest): UpdateUser {
//    return UpdateUser(
//        firstname = updateUserRequest.firstname,
//        lastname = updateUserRequest.lastname,
//        email = updateUserRequest.email
//    )
// }

fun UserMapper.mapUserToUserResponse(user: User): UserResponse {
    return UserResponse(
        id = user.id,
        firstname = user.firstname,
        lastname = user.lastname,
        email = user.email,
    )
}
