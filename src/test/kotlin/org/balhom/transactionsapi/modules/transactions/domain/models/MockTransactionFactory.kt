package org.balhom.transactionsapi.modules.transactions.domain.models

import org.balhom.transactionsapi.common.data.models.MockAuditableDataFactory
import org.balhom.transactionsapi.common.utils.TestDataUtils.Companion.randomBigDecimal
import org.balhom.transactionsapi.common.utils.TestDataUtils.Companion.randomPastDateTime
import org.balhom.transactionsapi.common.utils.TestDataUtils.Companion.randomText
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionCategoryEnum
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionTypeEnum
import java.util.*

class MockTransactionFactory {
    companion object {
        fun create(): Transaction {
            val transactionId = UUID.randomUUID()
            val currencyProfileId = UUID.randomUUID()

            return Transaction(
                id = transactionId,
                currencyProfileId = currencyProfileId,
                title = "Transaction_${randomText(5)}",
                description = randomText(100),
                type = TransactionTypeEnum
                    .entries
                    .toTypedArray()
                    .random(),
                amount = randomBigDecimal(0.0, 10000.0),
                date = randomPastDateTime(),
                category = TransactionCategoryEnum
                    .entries
                    .toTypedArray()
                    .random(),
                documents = ArrayList(),
                auditableData = MockAuditableDataFactory
                    .create()
            )
        }
    }
}