package org.balhom.transactionsapi.modules.transactions.infrastructure.producers.data

import io.quarkus.runtime.annotations.RegisterForReflection
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionCategoryEnum
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionTypeEnum
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@RegisterForReflection
data class TransactionChangeEvent(
    var action: String,
    var id: UUID,
    var type: TransactionTypeEnum,
    var date: LocalDateTime,
    var category: TransactionCategoryEnum,
    var amount: BigDecimal,
    var oldAmount: BigDecimal?,
    var cpGoalMonthlySaving: BigDecimal,
    var cpGoalYearlySaving: BigDecimal,
    var currencyProfileId: UUID,
    var userId: UUID,
)
