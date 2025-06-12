package org.balhom.transactionsapi.modules.transactions.domain.props

import org.balhom.transactionsapi.common.data.props.ObjectIdUserProps
import java.io.File

data class UploadTransactionDocumentProps(
    val transactionIdUserProps: ObjectIdUserProps,
    val name: String,
    val document: File,
    val mimetype: String,
)
