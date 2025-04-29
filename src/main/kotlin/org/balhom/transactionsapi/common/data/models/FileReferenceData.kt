package org.balhom.transactionsapi.common.data.models

import java.time.LocalDateTime
import java.util.*

data class FileReferenceData(
    var id: UUID,
    var name: String,
    var filePath: String,
    var url: String?,
    var urlExpiration: LocalDateTime?,
    var auditableData: AuditableData?
) {
    constructor() : this(
        UUID.randomUUID(),
        "",
        "",
        null,
        null,
        null
    )

    constructor(id: UUID, name: String, filePath: String) : this(
        id,
        name,
        filePath,
        null,
        null,
        null
    )
}
