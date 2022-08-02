package com.stebitto.commonexception

import io.ktor.client.plugins.*
import java.net.UnknownHostException

fun Exception.toHttpException() = when (this) {
    is ServerResponseException -> InternalServerException
    is ClientRequestException -> when (response.status.value) {
        400 -> BadRequestException
        401 -> UnauthorizedException
        403 -> ForbiddenException
        404 -> NotFoundException
        else -> GenericException
    }
    is UnknownHostException -> NetworkException
    else -> GenericException
}