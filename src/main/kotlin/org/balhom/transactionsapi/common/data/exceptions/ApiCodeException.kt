package org.balhom.transactionsapi.common.data.exceptions

open class ApiCodeException(
    val errorCode: Int,
    message: String
) : RuntimeException(message)
