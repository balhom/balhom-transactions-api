package org.balhom.transactionsapi.common.data.responses

data class ApiFieldErrorResponse(
    val field: String,
    val message: String,
)
