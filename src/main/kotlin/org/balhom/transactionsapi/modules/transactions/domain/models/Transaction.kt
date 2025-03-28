package org.balhom.transactionsapi.modules.transactions.domain.models

import org.balhom.transactionsapi.common.data.models.AuditableData
import org.balhom.transactionsapi.common.data.models.FileReferenceData
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionCategoryEnum
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionTypeEnum
import org.balhom.transactionsapi.modules.transactions.domain.exceptions.TransactionDocumentsExceededException
import org.balhom.transactionsapi.modules.transactions.domain.props.UpdateTransactionProps
import java.time.LocalDateTime
import java.util.UUID

data class Transaction(
    var id: UUID,
    var userId: UUID,
    var currencyProfileId: UUID,
    var title: String,
    var description: String,
    var type: TransactionTypeEnum,
    var amount: Double,
    var date: LocalDateTime,
    var category: TransactionCategoryEnum,
    var documents: MutableList<FileReferenceData>,
    var auditableData: AuditableData,
) {
    companion object {
        const val MAX_ALLOWED_DOCUMENTS = 3
    }

    fun update(props: UpdateTransactionProps) {
        id = props.id
        userId = props.userId

        title = props.title
        description = props.description
        amount = props.amount
        date = props.date
        category = props.category

        // Auditable section
        auditableData.updatedAt = LocalDateTime.now()
        auditableData.updatedBy = userId.toString()

        validate()
    }

    fun validate() {
        if (documents.size >= MAX_ALLOWED_DOCUMENTS) {
            throw TransactionDocumentsExceededException()
        }
    }
}
