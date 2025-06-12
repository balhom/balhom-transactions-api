package org.balhom.transactionsapi.modules.transactions.infrastructure.persistence

import jakarta.enterprise.context.ApplicationScoped
import org.balhom.transactionsapi.common.clients.storage.ObjectStorageClient
import org.balhom.transactionsapi.common.data.models.FileReferenceData
import org.balhom.transactionsapi.modules.transactions.domain.repositories.TransactionDocumentRepository
import org.balhom.transactionsapi.modules.transactions.infrastructure.persistence.sql.TransactionDocumentSqlRepository
import org.balhom.transactionsapi.modules.transactions.infrastructure.persistence.sql.TransactionSqlRepository
import org.balhom.transactionsapi.modules.transactions.infrastructure.persistence.sql.data.TransactionDocumentSqlEntity
import org.balhom.transactionsapi.modules.transactions.infrastructure.persistence.sql.data.TransactionSqlEntity
import java.util.*


@ApplicationScoped
class TransactionDocumentRepositoryImpl(
    private val transactionDocumentSqlRepository: TransactionDocumentSqlRepository,
    private val transactionSqlRepository: TransactionSqlRepository,
    private val objectStorageClient: ObjectStorageClient
) : TransactionDocumentRepository {

    override fun findByTransactionIdAndId(
        id: UUID,
        transactionId: UUID,
    ): FileReferenceData? {
        return transactionDocumentSqlRepository
            .find(
                "${TransactionDocumentSqlEntity.ID_FIELD} = :id " +
                        "AND ${TransactionDocumentSqlEntity.TRANSACTION_FIELD}.${TransactionSqlEntity.ID_FIELD}" +
                        " = :transactionId",
                mutableMapOf<String, Any>(
                    "id" to id,
                    "transactionId" to transactionId,
                )
            )
            .firstResult()
            ?.toDomain()
    }

    override fun save(
        fileReferenceData: FileReferenceData,
        transactionId: UUID
    ): FileReferenceData {
        val transactionEntity = transactionSqlRepository
            .find(
                "${TransactionDocumentSqlEntity.ID_FIELD} = :id",
                mutableMapOf<String, Any>(
                    "id" to transactionId
                )
            )
            .firstResult()

        val docEntity = transactionDocumentSqlRepository
            .find("id", fileReferenceData.id)
            .firstResult()?.apply {
                name = fileReferenceData.name
                path = fileReferenceData.filePath
                createdAt = fileReferenceData.auditableData?.createdAt
                createdBy = fileReferenceData.auditableData?.createdBy
                updatedAt = fileReferenceData.auditableData?.updatedAt
                updatedBy = fileReferenceData.auditableData?.updatedBy
            } ?: TransactionDocumentSqlEntity
            .fromDomain(
                fileReferenceData,
                transactionEntity!!
            )

        transactionDocumentSqlRepository.persist(docEntity)

        return docEntity.toDomain()
    }

    override fun delete(fileReferenceData: FileReferenceData) {
        transactionDocumentSqlRepository
            .delete(
                TransactionDocumentSqlEntity.ID_FIELD,
                fileReferenceData.id
            )

        // Remove all documents from objectStorageClient
        objectStorageClient.deleteObject(
            fileReferenceData.filePath
        )
    }
}