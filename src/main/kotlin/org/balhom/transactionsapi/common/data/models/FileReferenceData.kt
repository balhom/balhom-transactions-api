package org.balhom.transactionsapi.common.data.models

import java.util.*

data class FileReferenceData(
    var id: UUID,
    var name: String,
    var filePath: String,
    var auditableData: AuditableData?
) {
    constructor() : this(
        UUID.randomUUID(),
        "",
        "",
        null,
    )

    constructor(id: UUID, name: String, filePath: String) : this(
        id,
        name,
        filePath,
        null,
    )
}
