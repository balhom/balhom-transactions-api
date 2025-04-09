package org.balhom.transactionsapi.modules.currencyprofilechanges.infrastructure.consumers.data

import java.math.BigDecimal
import java.util.*

data class CurrencyProfileChangeEvent(
    val action: String,
    val id: UUID,
    val balance: BigDecimal,
    val monthlyGoal: BigDecimal,
    val yearlyGoal: BigDecimal,
    val ownerId: UUID,
)
