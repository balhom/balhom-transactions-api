package org.balhom.transactionsapi.modules.transactions.domain.exceptions

import org.balhom.transactionsapi.common.data.exceptions.ApiCodeException

class TransactionDocumentsExceededException : ApiCodeException(
    errorCode = 103,
    message = "Transaction documents max number reached"
)
