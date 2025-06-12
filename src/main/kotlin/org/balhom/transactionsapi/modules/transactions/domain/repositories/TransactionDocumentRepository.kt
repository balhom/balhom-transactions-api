package org.balhom.transactionsapi.modules.transactions.domain.repositories

import org.balhom.transactionsapi.common.data.models.FileReferenceData
import java.util.*

interface TransactionDocumentRepository {
    fun findByTransactionIdAndId(
        id: UUID,
        transactionId: UUID,
    ): FileReferenceData?

    fun save(
        fileReferenceData: FileReferenceData,
        transactionId: UUID
    ): FileReferenceData

    fun delete(fileReferenceData: FileReferenceData)
}
