package org.balhom.transactionsapi.modules.currencyprofilechanges.infrastructure.consumers

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.transactionsapi.modules.currencyprofilechanges.infrastructure.consumers.data.CurrencyProfileChangeEvent
import org.balhom.transactionsapi.modules.transactions.application.TransactionService
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class CurrencyProfileChangeEventConsumer(
    private val transactionService: TransactionService
) {
    @Incoming("currency-profile-events")
    fun receive(event: CurrencyProfileChangeEvent) {
        Log.debug("Consuming Kafka currency profile event: " + event.id)

        // TODO Call transactionService.deleteAll
    }
}
