package com.stebitto.commonexception

sealed class HttpException : IllegalStateException() {
    open val code: Int = -1
    open val text: String = ""
}

object InternalServerException : HttpException() {
    override val code: Int = 500
    override val text: String = "Server error"
}

object BadRequestException : HttpException() {
    override val code: Int = 400
    override val text: String = "Bad request"
}

object UnauthorizedException : HttpException() {
    override val code: Int = 401
    override val text: String = "Unauthorized"
}

object ForbiddenException : HttpException() {
    override val code: Int = 403
    override val text: String = "Forbidden"
}

object NotFoundException : HttpException() {
    override val code: Int = 404
    override val text: String = "Not found"
}

object NetworkException : HttpException() {
    override val code: Int = 1
    override val text: String = "Connection unavailable"
}

object GenericException : HttpException() {
    override val code: Int = 0
    override val text: String = "Generic error"
}