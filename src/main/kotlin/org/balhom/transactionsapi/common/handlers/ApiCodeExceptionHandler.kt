package org.balhom.transactionsapi.common.handlers

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.balhom.transactionsapi.common.data.exceptions.ApiCodeException
import org.balhom.transactionsapi.common.data.responses.ApiCodeErrorResponse

@Provider
class ApiCodeExceptionHandler : ExceptionMapper<ApiCodeException> {
    override fun toResponse(exception: ApiCodeException): Response {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(
                ApiCodeErrorResponse(
                    exception.errorCode,
                    exception.message,
                )
            )
            .build()
    }
}
