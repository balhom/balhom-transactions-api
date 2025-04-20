package org.balhom.transactionsapi.common.data.sql

import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import org.balhom.transactionsapi.common.data.exceptions.ApiPageNotFoundException
import org.balhom.transactionsapi.common.data.models.ApiPage
import org.balhom.transactionsapi.common.data.props.ApiPageProps
import kotlin.math.ceil
import kotlin.math.max

class PageSqlMapper {

    companion object {
        fun <T : Any> queryToApiPage(
            query: PanacheQuery<T>,
            pageProps: ApiPageProps
        ): ApiPage<T> {
            val totalElements = query.count()
            val totalPages = ceil(
                totalElements.toDouble() / pageProps.pageSize.toDouble()
            ).toInt()

            if (pageProps.pageNum != 0 && totalPages < pageProps.pageNum + 1) {
                throw ApiPageNotFoundException(
                    pageProps.pageNum
                )
            }

            return ApiPage(
                totalElements = query.count(),
                pageSize = pageProps.pageSize,
                pageIndex = pageProps.pageNum,
                firstPage = 0,
                lastPage = max(0, totalPages - 1),
                query.list()
            )
        }
    }
}