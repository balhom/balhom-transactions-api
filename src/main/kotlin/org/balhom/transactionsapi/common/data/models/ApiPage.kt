package org.balhom.transactionsapi.common.data.models

data class ApiPage<T>(
    val totalElements: Long,
    val pageSize: Int,
    val pageIndex: Int,
    val firstPage: Int,
    val lastPage: Int,
    val results: List<T>
) {
    fun <U> map(converter: (T) -> U): ApiPage<U> {
        return ApiPage(
            totalElements,
            pageSize,
            pageIndex,
            firstPage,
            lastPage,
            results.map(converter)
        )
    }
}