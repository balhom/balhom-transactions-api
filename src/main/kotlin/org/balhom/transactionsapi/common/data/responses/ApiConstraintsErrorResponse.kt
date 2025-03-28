package org.balhom.transactionsapi.common.data.responses

data class ApiConstraintsErrorResponse(
    val fields: List<ApiFieldErrorResponse>,
)
