package org.balhom.transactionsapi.common.data.models

import java.time.LocalDateTime
import java.util.*

data class FileReferenceData(
    var id: UUID,
    var filePath: String,
    var url: String?,
    var expiration: LocalDateTime?,
) {
    constructor() : this(UUID.randomUUID(), "", null, null)

    constructor(id: UUID, filePath: String) :
            this(id, filePath, null, null)
}
