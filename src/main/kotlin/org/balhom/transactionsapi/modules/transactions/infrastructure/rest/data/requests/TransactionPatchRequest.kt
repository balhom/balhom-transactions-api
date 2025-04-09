package org.balhom.transactionsapi.modules.transactions.infrastructure.rest.data.requests

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers
import jakarta.validation.constraints.Size
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionCategoryEnum
import org.balhom.transactionsapi.modules.transactions.domain.props.UpdateTransactionProps
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class TransactionPatchRequest(
    @field:Size(max = 30)
    val title: String?,

    @field:Size(max = 200)
    val description: String?,

    @JsonDeserialize(using = NumberDeserializers.BigDecimalDeserializer::class)
    val amount: BigDecimal?,

    val date: LocalDateTime?,

    val category: TransactionCategoryEnum?,
) {
    fun toUpdateProps(
        id: UUID,
        userId: UUID,
    ): UpdateTransactionProps = UpdateTransactionProps(
        id,
        userId,
        title,
        description,
        amount,
        date,
        category,
    )
}
