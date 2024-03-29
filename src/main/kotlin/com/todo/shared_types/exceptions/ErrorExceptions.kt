package com.todo.shared_types.exceptions

class NotFoundError(override val message: String) : Exception(message)

class BadRequestError(override val message: String) : Exception(message)

class Unauthorized(override val message: String) : Exception(message)

class GenericError(override val message: String) : Exception(message)

class ConflictError(override val message: String) : Exception(message)
