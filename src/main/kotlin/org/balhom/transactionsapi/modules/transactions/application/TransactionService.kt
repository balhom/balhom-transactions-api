package org.balhom.transactionsapi.modules.transactions.application

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.balhom.transactionsapi.common.data.models.ApiPage
import org.balhom.transactionsapi.common.data.props.ObjectIdUserProps
import org.balhom.transactionsapi.modules.currencyprofilechanges.application.CurrencyProfileService
import org.balhom.transactionsapi.modules.transactions.domain.exceptions.TransactionNotFoundException
import org.balhom.transactionsapi.modules.transactions.domain.models.Transaction
import org.balhom.transactionsapi.modules.transactions.domain.producers.TransactionChangeEventProducer
import org.balhom.transactionsapi.modules.transactions.domain.props.CreateTransactionProps
import org.balhom.transactionsapi.modules.transactions.domain.props.GetAllTransactionsProps
import org.balhom.transactionsapi.modules.transactions.domain.props.UpdateTransactionProps
import org.balhom.transactionsapi.modules.transactions.domain.repositories.TransactionRepository
import java.util.*

@ApplicationScoped
@Transactional
class TransactionService(
    private val currencyProfileService: CurrencyProfileService,
    private val transactionRepository: TransactionRepository,
    private val transactionChangeEventProducer: TransactionChangeEventProducer,
) {
    fun getTransaction(props: ObjectIdUserProps): Transaction {
        val transaction = transactionRepository.findById(
            props.id
        ) ?: throw TransactionNotFoundException()

        currencyProfileService.getCurrencyProfileReferenceAndValidate(
            props.userId,
            transaction.currencyProfileId
        )

        return transaction
    }

    fun getTransactions(props: GetAllTransactionsProps): ApiPage<Transaction> {
        val currencyProfileReference = currencyProfileService
            .getCurrencyProfileReferenceAndValidate(
                props.currencyProfileProps.userId,
                props.currencyProfileProps.id
            )

        val transactionsPage = transactionRepository.findAll(
            currencyProfileReference.id,
            props.sortAndFilterProps,
            props.pageProps
        )

        return transactionsPage
    }

    fun createTransaction(props: CreateTransactionProps): Transaction {
        props.transaction.validate()

        val currencyProfileReference = currencyProfileService
            .getCurrencyProfileReferenceAndValidate(
                props.userId,
                props.transaction.currencyProfileId
            )

        val newTransaction = transactionRepository
            .save(props.transaction)

        transactionChangeEventProducer.sendCreateEvent(
            newTransaction,
            currencyProfileReference,
            props.userId
        )

        return newTransaction
    }

    fun updateTransaction(props: UpdateTransactionProps) {
        val transaction = getTransaction(
            ObjectIdUserProps(
                props.id,
                props.userId
            )
        )

        val currencyProfileReference = currencyProfileService
            .getCurrencyProfileReferenceAndValidate(
                props.userId,
                transaction.currencyProfileId
            )

        val oldTransactionAmount = transaction.amount

        transaction.update(props)

        transaction.validate()

        val newTransaction = transactionRepository
            .save(transaction)

        transactionChangeEventProducer.sendUpdateEvent(
            newTransaction,
            oldTransactionAmount,
            currencyProfileReference,
            props.userId
        )
    }

    fun deleteTransaction(props: ObjectIdUserProps) {
        val transaction = getTransaction(props)

        val currencyProfileReference = currencyProfileService
            .getCurrencyProfileReferenceAndValidate(
                props.userId,
                transaction.currencyProfileId
            )

        transactionChangeEventProducer.sendDeleteEvent(
            transaction,
            currencyProfileReference,
            props.userId
        )

        transactionRepository
            .delete(transaction)
    }

    fun deleteAllTransactions(currencyProfileId: UUID) {
        transactionRepository.deleteAllByCurrencyProfileId(
            currencyProfileId
        )
    }
}
