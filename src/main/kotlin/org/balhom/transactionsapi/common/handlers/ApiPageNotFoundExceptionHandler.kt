package org.balhom.transactionsapi.common.handlers

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.balhom.transactionsapi.common.data.exceptions.ApiPageNotFoundException
import org.balhom.transactionsapi.common.data.responses.ApiErrorResponse

@Provider
class ApiPageNotFoundExceptionHandler : ExceptionMapper<ApiPageNotFoundException> {
    override fun toResponse(exception: ApiPageNotFoundException): Response {
        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(
                ApiErrorResponse(exception.message)
            )
            .build()
    }
}
