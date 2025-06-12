package org.balhom.transactionsapi.modules.transactions.infrastructure.persistence.sql.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.balhom.transactionsapi.common.data.models.AuditableData
import org.balhom.transactionsapi.common.data.models.FileReferenceData
import org.balhom.transactionsapi.common.data.sql.AuditableDataSqlEntity
import java.util.*

@Entity
@Table(name = TransactionDocumentSqlEntity.TABLE_NAME)
class TransactionDocumentSqlEntity : AuditableDataSqlEntity() {
    @Id
    @Column(name = ID_COLUMN_NAME)
    lateinit var id: UUID

    @ManyToOne
    @JoinColumn(name = TRANSACTION_ID_COLUMN_NAME, nullable = false)
    lateinit var transaction: TransactionSqlEntity

    @Column(name = NAME_COLUMN_NAME, nullable = false)
    lateinit var name: String

    @Column(name = PATH_COLUMN_NAME, nullable = false)
    lateinit var path: String

    fun toDomain(): FileReferenceData {
        return FileReferenceData(
            id,
            name,
            path,
            AuditableData(
                createdAt = createdAt,
                createdBy = createdBy,
                updatedAt = updatedAt,
                updatedBy = updatedBy
            ),
        )
    }

    companion object {
        const val TABLE_NAME = "app_transaction_documents"

        const val ID_COLUMN_NAME = "id"
        const val TRANSACTION_ID_COLUMN_NAME = "app_transaction_id"
        const val NAME_COLUMN_NAME = "name"
        const val PATH_COLUMN_NAME = "path"

        const val ID_FIELD = "id"

        const val TRANSACTION_FIELD = "transaction"

        fun fromDomain(
            domain: FileReferenceData,
            transaction: TransactionSqlEntity
        ): TransactionDocumentSqlEntity {
            val entity = TransactionDocumentSqlEntity()

            entity.id = domain.id
            entity.transaction = transaction
            entity.name = domain.name
            entity.path = domain.filePath

            entity.createdAt = domain.auditableData?.createdAt
            entity.createdBy = domain.auditableData?.createdBy
            entity.updatedAt = domain.auditableData?.updatedAt
            entity.updatedBy = domain.auditableData?.updatedBy

            return entity
        }
    }
}