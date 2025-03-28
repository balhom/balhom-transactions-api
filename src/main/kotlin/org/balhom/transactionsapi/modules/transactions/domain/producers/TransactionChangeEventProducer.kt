package org.balhom.transactionsapi.modules.transactions.domain.producers

import org.balhom.transactionsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileReference
import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction

interface TransactionChangeEventProducer {
    fun sendCreateEvent(
        transaction: Transaction,
        currencyProfile: CurrencyProfileReference
    )

    fun sendUpdateEvent(
        newTransaction: Transaction,
        oldTransaction: Transaction,
        currencyProfile: CurrencyProfileReference
    )

    fun sendDeleteEvent(
        transaction: Transaction,
        currencyProfile: CurrencyProfileReference
    )
}
