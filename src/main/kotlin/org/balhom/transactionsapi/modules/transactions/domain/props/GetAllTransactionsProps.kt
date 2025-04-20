package org.balhom.transactionsapi.modules.transactions.domain.props

import org.balhom.transactionsapi.common.data.props.ApiPageProps
import org.balhom.transactionsapi.common.data.props.ObjectIdUserProps

data class GetAllTransactionsProps(
    val currencyProfileProps: ObjectIdUserProps,
    val pageProps: ApiPageProps,
    val sortAndFilterProps: TransactionSortAndFilterProps,
)
