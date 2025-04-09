package org.balhom.transactionsapi.modules.transactions.domain.props

import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction
import java.util.*

data class CreateTransactionProps(
    val userId: UUID,
    val transaction: Transaction,
)
