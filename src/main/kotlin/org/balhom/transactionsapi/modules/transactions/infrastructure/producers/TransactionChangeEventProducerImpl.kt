package org.balhom.transactionsapi.modules.transactions.infrastructure.producers

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.transactionsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileReference
import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction
import org.balhom.transactionsapi.modules.transactions.domain.producers.TransactionChangeEventProducer
import org.balhom.transactionsapi.modules.transactions.infrastructure.producers.data.TransactionChangeEvent
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import java.math.BigDecimal
import java.util.*

@ApplicationScoped
class TransactionChangeEventProducerImpl(
    @Channel("transaction-events") private val emitter: Emitter<TransactionChangeEvent>
) : TransactionChangeEventProducer {
    fun send(event: TransactionChangeEvent) {
        Log.debug("Producing Kafka transaction change event: " + event.id)

        emitter
            .send(event)
            .toCompletableFuture()
            .get()
    }

    override fun sendCreateEvent(
        transaction: Transaction,
        currencyProfile: CurrencyProfileReference,
        userId: UUID
    ) {
        send(
            TransactionChangeEvent(
                "C",
                transaction.id,
                transaction.amount,
                null,
                currencyProfile.goalMonthlySaving,
                currencyProfile.goalYearlySaving,
                currencyProfile.id,
                userId,
            )
        )
    }

    override fun sendUpdateEvent(
        newTransaction: Transaction,
        oldTransactionAmount: BigDecimal,
        currencyProfile: CurrencyProfileReference,
        userId: UUID
    ) {
        send(
            TransactionChangeEvent(
                "U",
                newTransaction.id,
                newTransaction.amount,
                oldTransactionAmount,
                currencyProfile.goalMonthlySaving,
                currencyProfile.goalYearlySaving,
                currencyProfile.id,
                userId,
            )
        )
    }

    override fun sendDeleteEvent(
        transaction: Transaction,
        currencyProfile: CurrencyProfileReference,
        userId: UUID
    ) {
        send(
            TransactionChangeEvent(
                "D",
                transaction.id,
                BigDecimal(0.0),
                transaction.amount,
                currencyProfile.goalMonthlySaving,
                currencyProfile.goalYearlySaving,
                currencyProfile.id,
                userId,
            )
        )
    }
}