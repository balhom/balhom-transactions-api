package org.balhom.transactionsapi.modules.transactions.domain.props

import org.balhom.transactionsapi.common.data.props.ApiPageProps
import org.balhom.transactionsapi.common.data.props.ObjectIdUserProps
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionTypeEnum

data class GetAllTransactionsProps(
    val currencyProfileProps: ObjectIdUserProps,
    val type: TransactionTypeEnum,
    val pageProps: ApiPageProps,
    val sortAndFilterProps: TransactionSortAndFilterProps,
)
