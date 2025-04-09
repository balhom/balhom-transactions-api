package org.balhom.transactionsapi.modules.transactions.infrastructure.producers.data

import java.math.BigDecimal
import java.util.UUID

data class TransactionChangeEvent(
    val action: String,
    val id: UUID,
    val amount: BigDecimal,
    val oldAmount: BigDecimal?,
    val cpGoalMonthlySaving: BigDecimal,
    val cpGoalYearlySaving: BigDecimal,
    val currencyProfileId: UUID,
    val userId: UUID,
)
