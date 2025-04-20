package org.balhom.transactionsapi.common.data.exceptions

open class ApiPageNotFoundException(
    pageNum: Int
) : RuntimeException("Page $pageNum not found")
