package org.balhom.transactionsapi.modules.transactions.domain.models

import org.balhom.transactionsapi.common.data.models.AuditableData
import org.balhom.transactionsapi.common.data.models.FileReferenceData
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionCategoryEnum
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionTypeEnum
import org.balhom.transactionsapi.modules.transactions.domain.exceptions.TransactionDocumentsExceededException
import org.balhom.transactionsapi.modules.transactions.domain.props.UpdateTransactionProps
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Transaction(
    var id: UUID,
    var currencyProfileId: UUID,
    var title: String,
    var description: String?,
    var type: TransactionTypeEnum,
    var amount: BigDecimal,
    var date: LocalDateTime,
    var category: TransactionCategoryEnum,
    var documents: List<FileReferenceData>,
    var auditableData: AuditableData,
) {
    companion object {
        const val MAX_ALLOWED_DOCUMENTS = 3
    }

    fun update(props: UpdateTransactionProps) {
        id = props.id

        if (props.title != null) {
            title = props.title
        }
        if (props.description != null) {
            description = props.description
        }
        if (props.amount != null) {
            amount = props.amount
        }
        if (props.date != null) {
            date = props.date
        }
        if (props.category != null) {
            category = props.category
        }

        // Auditable section
        auditableData.updatedAt = LocalDateTime.now()
        auditableData.updatedBy = props.userId.toString()

        validate()
    }

    fun validate() {
        if (documents.size >= MAX_ALLOWED_DOCUMENTS) {
            throw TransactionDocumentsExceededException()
        }
    }
}
