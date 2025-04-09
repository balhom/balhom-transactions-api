package org.balhom.transactionsapi.modules.currencyprofilechanges.domain.models

import org.balhom.transactionsapi.common.utils.TestDataUtils.Companion.randomBigDecimal
import java.util.*

class MockCurrencyProfileReferenceFactory {
    companion object {
        fun create(): CurrencyProfileReference {
            return CurrencyProfileReference(
                id = UUID.randomUUID(),
                balance = randomBigDecimal(0.0, 10000.0),
                goalMonthlySaving = randomBigDecimal(0.0, 1000.0),
                goalYearlySaving = randomBigDecimal(0.0, 1000.0),
                sharedUsers = emptyList(),
                userId = UUID.randomUUID()
            )
        }
    }
}