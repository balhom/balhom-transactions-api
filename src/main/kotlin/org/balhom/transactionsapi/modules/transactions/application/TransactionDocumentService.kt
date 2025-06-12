package org.balhom.transactionsapi.modules.transactions.application

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.balhom.transactionsapi.common.clients.storage.ObjectStorageClient
import org.balhom.transactionsapi.common.data.models.AuditableData
import org.balhom.transactionsapi.common.data.models.FileData
import org.balhom.transactionsapi.common.data.models.FileReferenceData
import org.balhom.transactionsapi.common.data.props.DocIdAndObjectIdUserProps
import org.balhom.transactionsapi.modules.transactions.domain.exceptions.TransactionDocumentNotFoundException
import org.balhom.transactionsapi.modules.transactions.domain.exceptions.TransactionDocumentsExceededException
import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction.Companion.MAX_ALLOWED_DOCUMENTS
import org.balhom.transactionsapi.modules.transactions.domain.props.UploadTransactionDocumentProps
import org.balhom.transactionsapi.modules.transactions.domain.repositories.TransactionDocumentRepository
import java.time.LocalDateTime
import java.util.*

@ApplicationScoped
@Transactional
class TransactionDocumentService(
    private val transactionService: TransactionService,
    private val transactionDocumentRepository: TransactionDocumentRepository,
    private val objectStorageClient: ObjectStorageClient,
) {
    fun getTransactionDocumentUrl(props: DocIdAndObjectIdUserProps): String {
        // Get and validate transaction ownership
        val transaction = transactionService.getTransaction(
            props.objectIdUserProps
        )

        val transactionDocument = transactionDocumentRepository.findByTransactionIdAndId(
            id = props.docId,
            transactionId = transaction.id
        ) ?: throw TransactionDocumentNotFoundException()

        return objectStorageClient.getObjectUrl(
            transactionDocument.filePath,
            10L // 10 minutes
        )
    }

    fun uploadTransactionDocument(props: UploadTransactionDocumentProps) {
        // Get and validate transaction ownership
        val transaction = transactionService.getTransaction(
            props.transactionIdUserProps
        )

        if (transaction.documents.size >= MAX_ALLOWED_DOCUMENTS) {
            throw TransactionDocumentsExceededException()
        }

        val docId = UUID.randomUUID()
        val docPath = "${transaction.getDocumentsPath()}/${docId}"

        val fileReferenceData = FileReferenceData(
            docId,
            props.name,
            docPath,
            AuditableData(
                createdAt = LocalDateTime.now(),
                createdBy = props.transactionIdUserProps.userId.toString(),
            )
        )

        transactionDocumentRepository.save(
            fileReferenceData,
            transaction.id
        )

        objectStorageClient.uploadObject(
            FileData(
                props.document,
                docPath,
                props.mimetype,
            )
        )
    }

    fun deleteTransactionDocument(props: DocIdAndObjectIdUserProps) {
        // Get and validate transaction ownership
        val transaction = transactionService.getTransaction(
            props.objectIdUserProps
        )

        val transactionDocument = transactionDocumentRepository.findByTransactionIdAndId(
            id = props.docId,
            transactionId = transaction.id
        ) ?: throw TransactionDocumentNotFoundException()

        transactionDocumentRepository.delete(
            transactionDocument
        )
    }
}
