package org.balhom.transactionsapi.common.data.models

import java.time.LocalDateTime

data class AuditableData(
    var createdAt: LocalDateTime?,
    var createdBy: String?,
    var updatedAt: LocalDateTime?,
    var updatedBy: String?,
)
