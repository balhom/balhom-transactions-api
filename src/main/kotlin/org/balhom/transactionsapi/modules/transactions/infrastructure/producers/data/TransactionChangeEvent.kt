package org.balhom.transactionsapi.modules.transactions.infrastructure.producers.data

import java.util.UUID

data class TransactionChangeEvent(
    val action: String,
    val id: UUID,
    val amount: Double,
    val oldAmount: Double?,
    val cpGoalMonthlySaving: Double,
    val cpGoalYearlySaving: Double,
    val currencyProfileId: UUID,
    val userId: UUID,
)
