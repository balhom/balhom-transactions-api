package org.balhom.transactionsapi.modules.transactions.infrastructure.rest.data.responses

import com.fasterxml.jackson.annotation.JsonFormat
import org.balhom.transactionsapi.common.config.TimeConfig
import org.balhom.transactionsapi.common.data.responses.FileReferenceDataResponse
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionCategoryEnum
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionTypeEnum
import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class TransactionResponse(
    val id: UUID,
    val currencyProfileId: UUID,
    val title: String,
    val description: String?,
    val type: TransactionTypeEnum,
    val amount: BigDecimal,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeConfig.DATE_TIME_ISO_FORMAT)
    val date: LocalDateTime,
    val category: TransactionCategoryEnum,
    var documents: List<FileReferenceDataResponse>,
) {
    companion object {
        fun fromDomain(domain: Transaction): TransactionResponse = TransactionResponse(
            domain.id,
            domain.currencyProfileId,
            domain.title,
            domain.description,
            domain.type,
            domain.amount,
            domain.date,
            domain.category,
            domain.documents
                .map { FileReferenceDataResponse.fromDomain(it) },
        )
    }
}
