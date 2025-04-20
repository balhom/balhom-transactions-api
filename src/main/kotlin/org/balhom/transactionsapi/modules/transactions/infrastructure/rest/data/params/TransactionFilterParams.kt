package org.balhom.transactionsapi.modules.transactions.infrastructure.rest.data.params

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.jboss.resteasy.reactive.RestQuery
import java.math.BigDecimal
import java.time.LocalDate

data class TransactionFilterParams(
    @field:RestQuery
    @field:Min(1)
    @field:Max(12)
    var month: Int,

    @field:RestQuery
    @field:Min(0)
    @field:Max(5000)
    var year: Int,

    @field:RestQuery
    var minAmount: BigDecimal?,

    @field:RestQuery
    var maxAmount: BigDecimal?,

    @field:RestQuery
    @field:Size(max = 15)
    var textSearch: String?,
) {
    constructor() : this(
        LocalDate.now().monthValue,
        LocalDate.now().year,
        null,
        null,
        null
    )
}
