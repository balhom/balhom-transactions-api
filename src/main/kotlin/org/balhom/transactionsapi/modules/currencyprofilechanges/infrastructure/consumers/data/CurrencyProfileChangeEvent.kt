package org.balhom.transactionsapi.modules.currencyprofilechanges.infrastructure.consumers.data

import io.quarkus.runtime.annotations.RegisterForReflection
import java.math.BigDecimal
import java.util.*

@RegisterForReflection
data class CurrencyProfileChangeEvent(
    var action: String,
    var id: UUID,
    var balance: BigDecimal,
    var monthlyGoal: BigDecimal,
    var yearlyGoal: BigDecimal,
    var ownerId: UUID,
)
