package org.balhom.transactionsapi.modules.transactions.infrastructure.producers

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.transactionsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileReference
import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction
import org.balhom.transactionsapi.modules.transactions.domain.producers.TransactionChangeEventProducer
import org.balhom.transactionsapi.modules.transactions.infrastructure.producers.data.TransactionChangeEvent
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter

@ApplicationScoped
class TransactionChangeEventProducerImpl(
    @Channel("transactions-events") private val emitter: Emitter<TransactionChangeEvent>
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
        currencyProfile: CurrencyProfileReference
    ) {
        send(
            TransactionChangeEvent(
                "C",
                transaction.id,
                transaction.amount,
                null,
                currencyProfile.monthlyGoal,
                currencyProfile.yearlyGoal,
                currencyProfile.id,
                transaction.userId,
            )
        )
    }

    override fun sendUpdateEvent(
        newTransaction: Transaction,
        oldTransaction: Transaction,
        currencyProfile: CurrencyProfileReference
    ) {
        send(
            TransactionChangeEvent(
                "U",
                newTransaction.id,
                newTransaction.amount,
                oldTransaction.amount,
                currencyProfile.monthlyGoal,
                currencyProfile.yearlyGoal,
                currencyProfile.id,
                newTransaction.userId,
            )
        )
    }

    override fun sendDeleteEvent(
        transaction: Transaction,
        currencyProfile: CurrencyProfileReference
    ) {
        send(
            TransactionChangeEvent(
                "D",
                transaction.id,
                0.0,
                transaction.amount,
                currencyProfile.monthlyGoal,
                currencyProfile.yearlyGoal,
                currencyProfile.id,
                transaction.userId,
            )
        )
    }
}