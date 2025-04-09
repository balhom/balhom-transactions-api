package org.balhom.transactionsapi.modules.transactions.infrastructure.rest.data.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.balhom.transactionsapi.common.data.models.AuditableData
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionCategoryEnum
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionTypeEnum
import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class TransactionPostRequest(
    @field:NotNull
    val currencyProfileId: UUID?,

    @field:NotBlank
    @field:Size(max = 30)
    val title: String?,

    @field:Size(max = 200)
    val description: String?,

    @field:NotNull
    val type: TransactionTypeEnum?,

    @field:NotNull
    val amount: BigDecimal?,

    @field:NotNull
    val date: LocalDateTime?,

    @field:NotNull
    val category: TransactionCategoryEnum?,
) {
    fun toDomain(
        userId: UUID
    ): Transaction = Transaction(
        UUID.randomUUID(),
        currencyProfileId!!,
        title!!,
        description,
        type!!,
        amount!!,
        date!!,
        category!!,
        ArrayList(),
        AuditableData(
            LocalDateTime.now(),
            userId.toString(),
            null,
            null,
        ),
    )
}
