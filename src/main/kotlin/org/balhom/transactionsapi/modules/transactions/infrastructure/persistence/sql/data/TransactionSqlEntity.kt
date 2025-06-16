package org.balhom.transactionsapi.modules.transactions.infrastructure.persistence.sql.data

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.balhom.transactionsapi.common.data.models.AuditableData
import org.balhom.transactionsapi.common.data.sql.AuditableDataSqlEntity
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionCategoryEnum
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionTypeEnum
import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = TransactionSqlEntity.TABLE_NAME)
class TransactionSqlEntity : AuditableDataSqlEntity() {
    @Id
    @Column(name = ID_COLUMN_NAME)
    lateinit var id: UUID

    @Column(name = CURRENCY_PROFILE_ID_COLUMN_NAME, nullable = false)
    lateinit var currencyProfileId: UUID

    @Column(name = TITLE_COLUMN_NAME, nullable = false)
    lateinit var title: String

    @Column(name = DESCRIPTION_COLUMN_NAME)
    var description: String? = null

    @Column(name = TYPE_COLUMN_NAME, nullable = false)
    @Enumerated(EnumType.STRING)
    lateinit var type: TransactionTypeEnum

    @Column(name = AMOUNT_COLUMN_NAME, nullable = false)
    lateinit var amount: BigDecimal

    @Column(name = DATE_COLUMN_NAME, nullable = false)
    lateinit var date: LocalDateTime

    @Column(name = CATEGORY_COLUMN_NAME, nullable = false)
    @Enumerated(EnumType.STRING)
    lateinit var category: TransactionCategoryEnum

    @OneToMany(
        mappedBy = TransactionDocumentSqlEntity.TRANSACTION_FIELD,
        fetch = FetchType.EAGER,
        targetEntity = TransactionDocumentSqlEntity::class,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    lateinit var documents: List<TransactionDocumentSqlEntity>

    fun toDomain(): Transaction {
        return Transaction(
            id,
            currencyProfileId,
            title,
            description,
            type,
            amount,
            date,
            category,
            documents.map { it.toDomain() },
            AuditableData(
                createdAt = createdAt,
                createdBy = createdBy,
                updatedAt = updatedAt,
                updatedBy = updatedBy
            ),
        )
    }

    companion object {
        const val TABLE_NAME = "app_transactions"

        const val ID_COLUMN_NAME = "id"
        const val CURRENCY_PROFILE_ID_COLUMN_NAME = "currency_profile_id"
        const val TITLE_COLUMN_NAME = "title"
        const val DESCRIPTION_COLUMN_NAME = "description"
        const val TYPE_COLUMN_NAME = "transaction_type"
        const val AMOUNT_COLUMN_NAME = "amount"
        const val DATE_COLUMN_NAME = "transaction_date"
        const val CATEGORY_COLUMN_NAME = "category"

        const val ID_FIELD = "id"
        const val CURRENCY_PROFILE_ID_FIELD = "currencyProfileId"
        const val TITLE_FIELD = "title"
        const val TYPE_FIELD = "type"
        const val DATE_FIELD = "date"
        const val AMOUNT_FIELD = "amount"

        fun fromDomain(domain: Transaction): TransactionSqlEntity {
            val entity = TransactionSqlEntity()

            entity.id = domain.id
            entity.currencyProfileId = domain.currencyProfileId
            entity.title = domain.title
            entity.description = domain.description
            entity.type = domain.type
            entity.amount = domain.amount
            entity.date = domain.date
            entity.category = domain.category
            entity.documents = domain
                .documents
                .map {
                    TransactionDocumentSqlEntity.fromDomain(
                        it,
                        entity
                    )
                }

            entity.createdAt = domain.auditableData.createdAt
            entity.createdBy = domain.auditableData.createdBy
            entity.updatedAt = domain.auditableData.updatedAt
            entity.updatedBy = domain.auditableData.updatedBy

            return entity
        }
    }
}