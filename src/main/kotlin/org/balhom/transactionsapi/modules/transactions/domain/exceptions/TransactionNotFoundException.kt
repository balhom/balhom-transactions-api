package org.balhom.transactionsapi.modules.transactions.domain.exceptions

import org.balhom.transactionsapi.common.data.exceptions.ApiCodeException

class TransactionNotFoundException : ApiCodeException(
    errorCode = 100,
    message = "Transaction not found"
)
