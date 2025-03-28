package org.balhom.transactionsapi.common.data.responses

data class ApiCodeErrorResponse(
    val errorCode: Int,
    val message: String?,
)
