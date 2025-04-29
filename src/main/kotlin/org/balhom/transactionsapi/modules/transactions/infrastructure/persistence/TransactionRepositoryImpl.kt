package org.balhom.transactionsapi.modules.transactions.infrastructure.persistence

import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.transactionsapi.common.data.models.ApiPage
import org.balhom.transactionsapi.common.data.props.ApiPageProps
import org.balhom.transactionsapi.common.data.sql.PageSqlMapper
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionSortEnum
import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction
import org.balhom.transactionsapi.modules.transactions.domain.props.TransactionSortAndFilterProps
import org.balhom.transactionsapi.modules.transactions.domain.repositories.TransactionRepository
import org.balhom.transactionsapi.modules.transactions.infrastructure.persistence.sql.TransactionSqlRepository
import org.balhom.transactionsapi.modules.transactions.infrastructure.persistence.sql.data.TransactionSqlEntity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*


@ApplicationScoped
class TransactionRepositoryImpl(
    private val transactionSqlRepository: TransactionSqlRepository
) : TransactionRepository {

    override fun findById(id: UUID): Transaction? {
        return transactionSqlRepository
            .find("id", id)
            .firstResult()
            ?.toDomain()
    }

    override fun findAll(
        currencyProfileId: UUID,
        sortAndFilterProps: TransactionSortAndFilterProps,
        pageProps: ApiPageProps,
    ): ApiPage<Transaction> {
        val firstDayOfMonth = LocalDate.of(
            sortAndFilterProps.year,
            sortAndFilterProps.month,
            1
        )
        val lastDayOfMonth = firstDayOfMonth
            .plusMonths(1)
            .minusDays(1)

        // Sql query parameters
        val parameters = mutableMapOf<String, Any>(
            "currencyProfileId" to currencyProfileId,
            "startDate" to LocalDateTime.of(firstDayOfMonth, LocalTime.MIN),
            "endDate" to LocalDateTime.of(lastDayOfMonth, LocalTime.MAX)
        )

        // Sql query builder
        val queryBuilder = StringBuilder(
            "${TransactionSqlEntity.CURRENCY_PROFILE_ID_FIELD} = :currencyProfileId " +
                    "AND ${TransactionSqlEntity.DATE_FIELD} >= :startDate " +
                    "AND ${TransactionSqlEntity.DATE_FIELD} <= :endDate"
        )

        // Min amount filter
        sortAndFilterProps.minAmount?.let { minAmount ->
            queryBuilder.append(
                " AND ${TransactionSqlEntity.AMOUNT_FIELD} >= :minAmount"
            )
            parameters["minAmount"] = minAmount.setScale(2)
        }
        // Max amount filter
        sortAndFilterProps.maxAmount?.let { maxAmount ->
            queryBuilder.append(
                " AND ${TransactionSqlEntity.AMOUNT_FIELD} <= :maxAmount"
            )
            parameters["maxAmount"] = maxAmount.setScale(2)
        }

        // Text search filter
        sortAndFilterProps.textSearch?.let { textSearch ->
            if (textSearch.isNotBlank()) {
                queryBuilder.append(
                    " AND (LOWER(${TransactionSqlEntity.TITLE_FIELD}) " +
                            "LIKE :textSearch)"
                )
                parameters["textSearch"] = "%${textSearch.lowercase()}%"
            }
        }

        // Sorting switch
        val sort = when (sortAndFilterProps.sortBy) {
            TransactionSortEnum.DATE_ASC -> Sort.by(
                TransactionSqlEntity.DATE_FIELD
            ).ascending()

            TransactionSortEnum.DATE_DESC -> Sort.by(
                TransactionSqlEntity.DATE_FIELD
            ).descending()

            TransactionSortEnum.AMOUNT_ASC -> Sort.by(
                TransactionSqlEntity.AMOUNT_FIELD
            ).ascending()

            TransactionSortEnum.AMOUNT_DESC -> Sort.by(
                TransactionSqlEntity.AMOUNT_FIELD
            ).descending()
        }

        val query = transactionSqlRepository
            .find(
                queryBuilder.toString(),
                sort,
                parameters
            )
            .page(
                Page.of(
                    pageProps.pageNum,
                    pageProps.pageSize
                )
            )

        return PageSqlMapper.queryToApiPage(
            query,
            pageProps
        ).map {
            it.toDomain()
        }
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

        return entity.toDomain()
    }

    override fun delete(transaction: Transaction) {
        // TODO remove all document from objectStorageClient

        transactionSqlRepository
            .delete(
                TransactionSqlEntity.ID_FIELD,
                transaction.id
            )
    }

    override fun deleteAllByCurrencyProfileId(currencyProfileId: UUID) {
        // TODO remove all document from objectStorageClient

        transactionSqlRepository
            .delete(
                TransactionSqlEntity.CURRENCY_PROFILE_ID_FIELD,
                currencyProfileId
            )
    }
}