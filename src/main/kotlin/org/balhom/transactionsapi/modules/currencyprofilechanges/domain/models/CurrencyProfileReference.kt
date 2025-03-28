package org.balhom.transactionsapi.modules.currencyprofilechanges.domain.models

import java.util.UUID

data class CurrencyProfileReference(
    val id: UUID,
    val balance: Double,
    val monthlyGoal: Double,
    val yearlyGoal: Double,
    val ownerId: UUID
)
