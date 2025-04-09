package org.balhom.transactionsapi.modules.transactions.infrastructure.persistence

import jakarta.enterprise.context.ApplicationScoped
import org.balhom.transactionsapi.common.clients.storage.ObjectStorageClient
import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction
import org.balhom.transactionsapi.modules.transactions.domain.repositories.TransactionRepository
import org.balhom.transactionsapi.modules.transactions.infrastructure.persistence.sql.TransactionSqlRepository
import org.balhom.transactionsapi.modules.transactions.infrastructure.persistence.sql.data.TransactionSqlEntity
import java.util.*


@ApplicationScoped
class TransactionRepositoryImpl(
    private val transactionSqlRepository: TransactionSqlRepository,
    private val objectStorageClient: ObjectStorageClient,
) : TransactionRepository {

    override fun findById(id: UUID): Transaction? {
        return transactionSqlRepository
            .find("id", id)
            .firstResult()
            ?.toDomain(transactionSqlRepository, objectStorageClient)
    }

    override fun save(transaction: Transaction): Transaction {
        val entity = transactionSqlRepository
            .find("id", transaction.id)
            .firstResult()?.apply {
                currencyProfileId = transaction.currencyProfileId
                title = transaction.title
                description = transaction.description
                type = transaction.type
                amount = transaction.amount
                date = transaction.date
                category = transaction.category
                createdAt = transaction.auditableData.createdAt
                createdBy = transaction.auditableData.createdBy
                updatedAt = transaction.auditableData.updatedAt
                updatedBy = transaction.auditableData.updatedBy
            } ?: TransactionSqlEntity
            .fromDomain(transaction)

        transactionSqlRepository.persist(entity)

        return entity.toDomain(
            transactionSqlRepository,
            objectStorageClient
        )
    }

    override fun delete(transaction: Transaction) {
        // TODO remove all document from objectStorageClient

        transactionSqlRepository
            .delete(
                TransactionSqlEntity.ID_COLUMN_NAME,
                transaction.id
            )
    }

    override fun deleteAllByCurrencyProfileId(currencyProfileId: UUID) {
        // TODO remove all document from objectStorageClient

        transactionSqlRepository
            .delete(
                TransactionSqlEntity.CURRENCY_PROFILE_ID_COLUMN_NAME,
                currencyProfileId
            )
    }
}