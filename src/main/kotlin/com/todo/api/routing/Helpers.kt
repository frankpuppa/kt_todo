package com.todo.api.routing

import com.todo.api.response.*
import com.todo.api.response.HttpResponse
import com.todo.shared_types.exceptions.*
import io.ktor.server.application.*
import io.ktor.server.plugins.BadRequestException
import io.ktor.util.logging.*

internal val logger = KtorSimpleLogger("main")

fun mapError(e: Exception): HttpResponse<ErrorResponse> {
    return when (e) {
        is NotFoundError -> HttpResponse.notFound(ErrorResponse(e.message))
        is ConflictError -> HttpResponse.conflict(ErrorResponse(e.message))
        is BadRequestError,
        is BadRequestException,
        -> HttpResponse.badRequest(ErrorResponse(e.message ?: "invalid request"))
        else -> {
            logger.error("${e.message}")
            HttpResponse.generic(ErrorResponse("internal server error"))
        }
    }
}
