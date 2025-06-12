package org.balhom.transactionsapi.modules.transactions.domain.exceptions

import org.balhom.transactionsapi.common.data.exceptions.ApiCodeException

class TransactionDocumentNotFoundException : ApiCodeException(
    errorCode = 101,
    message = "Transaction document not found"
)
