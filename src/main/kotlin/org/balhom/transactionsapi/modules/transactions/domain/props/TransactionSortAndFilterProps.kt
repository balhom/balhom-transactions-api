package org.balhom.transactionsapi.modules.transactions.domain.props

import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionSortEnum
import java.math.BigDecimal

data class TransactionSortAndFilterProps(
    val month: Int,
    val year: Int,
    val minAmount: BigDecimal?,
    val maxAmount: BigDecimal?,
    val textSearch: String?,
    val sortBy: TransactionSortEnum,
)
