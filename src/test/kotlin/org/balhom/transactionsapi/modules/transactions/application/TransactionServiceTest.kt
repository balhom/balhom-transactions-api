package org.balhom.transactionsapi.modules.transactions.application

import org.balhom.transactionsapi.common.data.props.ObjectIdUserProps
import org.balhom.transactionsapi.modules.currencyprofilechanges.application.CurrencyProfileService
import org.balhom.transactionsapi.modules.currencyprofilechanges.domain.models.MockCurrencyProfileReferenceFactory
import org.balhom.transactionsapi.modules.transactions.domain.models.MockTransactionFactory
import org.balhom.transactionsapi.modules.transactions.domain.producers.TransactionChangeEventProducer
import org.balhom.transactionsapi.modules.transactions.domain.repositories.TransactionRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.util.*

class TransactionServiceTest {

    private lateinit var currencyProfileService: CurrencyProfileService
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var transactionChangeEventProducer: TransactionChangeEventProducer

    private lateinit var transactionService: TransactionService

    @BeforeEach
    fun setUp() {
        currencyProfileService = mock(
            CurrencyProfileService::class.java
        )
        transactionRepository = mock(
            TransactionRepository::class.java
        )
        transactionChangeEventProducer = mock(
            TransactionChangeEventProducer::class.java
        )

        transactionService = TransactionService(
            currencyProfileService,
            transactionRepository,
            transactionChangeEventProducer
        )
    }

    @Test
    fun `when Transaction exists then getTransaction should return a valid Transaction`() {
        val userId = UUID.randomUUID()
        val transactionId = UUID.randomUUID()

        val expectedTransaction = MockTransactionFactory.create()

        val expectedCurrencyProfileReference = MockCurrencyProfileReferenceFactory
            .create()
        expectedCurrencyProfileReference.id = expectedTransaction
            .currencyProfileId
        expectedCurrencyProfileReference.userId = userId

        `when`(
            transactionRepository
                .findById(transactionId)
        ).thenReturn(expectedTransaction)

        val result = transactionService.getTransaction(
            ObjectIdUserProps(
                transactionId,
                userId
            )
        )

        assertEquals(expectedTransaction, result)
        verify(transactionRepository)
            .findById(transactionId)
    }
}