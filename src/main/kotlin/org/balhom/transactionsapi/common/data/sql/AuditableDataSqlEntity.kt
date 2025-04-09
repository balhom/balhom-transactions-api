package org.balhom.transactionsapi.common.data.sql

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
open class AuditableDataSqlEntity {
    @Column(name = CREATED_AT_COLUMN_NAME)
    var createdAt: LocalDateTime? = null

    @Column(name = CREATED_BY_COLUMN_NAME)
    var createdBy: String? = null

    @Column(name = UPDATED_AT_COLUMN_NAME)
    var updatedAt: LocalDateTime? = null

    @Column(name = UPDATED_BY_COLUMN_NAME)
    var updatedBy: String? = null

    companion object {
        const val CREATED_AT_COLUMN_NAME = "created_at"
        const val CREATED_BY_COLUMN_NAME = "created_by"
        const val UPDATED_AT_COLUMN_NAME = "updated_at"
        const val UPDATED_BY_COLUMN_NAME = "updated_by"
    }
}
