package com.todo.api.response

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
class ErrorResponse(val details: String)

/**
 * Represents HTTP response which will be exposed via API.
 */
sealed class HttpResponse<ErrorResponse> {
    abstract val body: ErrorResponse
    abstract val code: HttpStatusCode

    data class NotFound(override val body: ErrorResponse) : HttpResponse<ErrorResponse>() {
        override val code: HttpStatusCode = HttpStatusCode.NotFound
    }

    data class BadRequest(override val body: ErrorResponse) : HttpResponse<ErrorResponse>() {
        override val code: HttpStatusCode = HttpStatusCode.BadRequest
    }

    data class Unauthorized(override val body: ErrorResponse) : HttpResponse<ErrorResponse>() {
        override val code: HttpStatusCode = HttpStatusCode.Unauthorized
    }

    data class Conflict(override val body: ErrorResponse) : HttpResponse<ErrorResponse>() {
        override val code: HttpStatusCode = HttpStatusCode.Conflict
    }

    data class Generic(override val body: ErrorResponse) : HttpResponse<ErrorResponse>() {
        override val code: HttpStatusCode = HttpStatusCode.InternalServerError
    }

    companion object {
        fun notFound(response: ErrorResponse) = NotFound(body = response)

        fun badRequest(response: ErrorResponse) = BadRequest(body = response)

        fun unauth(response: ErrorResponse) = Unauthorized(body = response)

        fun conflict(response: ErrorResponse) = Conflict(body = response)

        fun generic(response: ErrorResponse) = Generic(body = response)
    }
}
