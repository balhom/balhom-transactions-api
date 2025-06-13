package org.balhom.transactionsapi.common.handlers

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.balhom.transactionsapi.common.data.exceptions.ApiPageNotFoundException

@Provider
class ApiPageNotFoundExceptionHandler : ExceptionMapper<ApiPageNotFoundException> {
    override fun toResponse(exception: ApiPageNotFoundException): Response {
        val response = mapOf(
            "message" to exception.message
        )

        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(response)
            .build()
    }
}
