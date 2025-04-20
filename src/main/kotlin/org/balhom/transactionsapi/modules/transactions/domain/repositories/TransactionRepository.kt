package org.balhom.transactionsapi.modules.transactions.domain.repositories

import org.balhom.transactionsapi.common.data.models.ApiPage
import org.balhom.transactionsapi.common.data.props.ApiPageProps
import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction
import org.balhom.transactionsapi.modules.transactions.domain.props.TransactionSortAndFilterProps
import java.util.*

interface TransactionRepository {
    fun findById(id: UUID): Transaction?

    fun findAll(
        currencyProfileId: UUID,
        sortAndFilterProps: TransactionSortAndFilterProps,
        pageProps: ApiPageProps,
    ): ApiPage<Transaction>

    fun save(transaction: Transaction): Transaction

    fun delete(transaction: Transaction)

    fun deleteAllByCurrencyProfileId(currencyProfileId: UUID)
}
