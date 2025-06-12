package org.balhom.transactionsapi.common.data.props

import java.util.*

data class DocIdAndObjectIdUserProps(
    val docId: UUID,
    val objectIdUserProps: ObjectIdUserProps,
)
