package org.balhom.transactionsapi.modules.transactions.domain.producers

import org.balhom.transactionsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileReference
import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction
import java.math.BigDecimal
import java.util.*

interface TransactionChangeEventProducer {
    fun sendCreateEvent(
        transaction: Transaction,
        currencyProfile: CurrencyProfileReference,
        userId: UUID
    )

    fun sendUpdateEvent(
        newTransaction: Transaction,
        oldTransactionAmount: BigDecimal,
        currencyProfile: CurrencyProfileReference,
        userId: UUID
    )

    fun sendDeleteEvent(
        transaction: Transaction,
        currencyProfile: CurrencyProfileReference,
        userId: UUID
    )
}
