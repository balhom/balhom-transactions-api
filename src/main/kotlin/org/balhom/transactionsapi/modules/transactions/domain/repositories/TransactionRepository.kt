package org.balhom.transactionsapi.modules.transactions.domain.repositories

import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction
import java.util.*

interface TransactionRepository {
    fun findById(id: UUID): Transaction?

    fun save(transaction: Transaction): Transaction

    fun delete(transaction: Transaction)

    fun deleteAllByCurrencyProfileId(currencyProfileId: UUID)
}
