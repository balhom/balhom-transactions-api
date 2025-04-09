package org.balhom.transactionsapi.modules.currencyprofilechanges.domain.exceptions

import org.balhom.transactionsapi.common.data.exceptions.ApiCodeException

class CurrencyProfileReferenceNotFoundException : ApiCodeException(
    errorCode = 200,
    message = "Currency profile reference not found"
)
