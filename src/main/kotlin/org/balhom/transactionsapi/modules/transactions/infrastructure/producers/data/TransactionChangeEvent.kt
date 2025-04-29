package org.balhom.transactionsapi.modules.transactions.infrastructure.producers.data

import io.quarkus.runtime.annotations.RegisterForReflection
import java.math.BigDecimal
import java.util.*

@RegisterForReflection
data class TransactionChangeEvent(
    var action: String,
    var id: UUID,
    var amount: BigDecimal,
    var oldAmount: BigDecimal?,
    var cpGoalMonthlySaving: BigDecimal,
    var cpGoalYearlySaving: BigDecimal,
    var currencyProfileId: UUID,
    var userId: UUID,
) {
    constructor() : this(
        "",
        UUID.randomUUID(),
        BigDecimal(0),
        null,
        BigDecimal(0),
        BigDecimal(0),
        UUID.randomUUID(),
        UUID.randomUUID()
    )
}
