package org.balhom.transactionsapi.modules.transactions.domain.enums

enum class EventChangeTypeEnum {
    CREATE, UPDATE, DELETE;

    companion object {
        fun fromAction(action: String): EventChangeTypeEnum =
            when (action) {
                "C" -> CREATE
                "U" -> UPDATE
                "D" -> DELETE
                else -> throw UnsupportedOperationException()
            }
    }
}
