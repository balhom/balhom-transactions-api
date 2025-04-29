package org.balhom.transactionsapi.common.data.responses

import com.fasterxml.jackson.annotation.JsonFormat
import org.balhom.transactionsapi.common.config.TimeConfig
import org.balhom.transactionsapi.common.data.models.FileReferenceData
import java.time.LocalDateTime
import java.util.*

data class FileReferenceDataResponse(
    var id: UUID,
    var name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeConfig.DATE_TIME_ISO_FORMAT)
    var createdAt: LocalDateTime?,
) {
    companion object {
        fun fromDomain(data: FileReferenceData): FileReferenceDataResponse {
            return FileReferenceDataResponse(
                data.id,
                data.name,
                data.auditableData?.createdAt
            )
        }
    }
}
