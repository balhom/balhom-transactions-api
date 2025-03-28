package org.balhom.transactionsapi.common.data.models

import java.util.UUID

data class IdpUser(
    val id: UUID,
    val email: String
)
