package org.balhom.transactionsapi.modules.transactions.domain.props

import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionCategoryEnum
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class UpdateTransactionProps(
    val id: UUID,
    val userId: UUID,
    val title: String?,
    val description: String?,
    val amount: BigDecimal?,
    val date: LocalDateTime?,
    val category: TransactionCategoryEnum?,
)
