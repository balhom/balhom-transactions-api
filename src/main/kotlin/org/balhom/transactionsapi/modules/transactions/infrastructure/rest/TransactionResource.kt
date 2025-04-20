package org.balhom.transactionsapi.modules.transactions.infrastructure.rest

import io.quarkus.security.Authenticated
import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.BeanParam
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.PATCH
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.balhom.transactionsapi.common.data.models.ApiPage
import org.balhom.transactionsapi.common.data.params.ApiPageParams
import org.balhom.transactionsapi.common.data.props.ObjectIdUserProps
import org.balhom.transactionsapi.modules.transactions.application.TransactionService
import org.balhom.transactionsapi.modules.transactions.domain.enums.TransactionSortEnum
import org.balhom.transactionsapi.modules.transactions.domain.props.CreateTransactionProps
import org.balhom.transactionsapi.modules.transactions.domain.props.GetAllTransactionsProps
import org.balhom.transactionsapi.modules.transactions.domain.props.TransactionSortAndFilterProps
import org.balhom.transactionsapi.modules.transactions.infrastructure.rest.data.params.TransactionFilterParams
import org.balhom.transactionsapi.modules.transactions.infrastructure.rest.data.requests.TransactionPatchRequest
import org.balhom.transactionsapi.modules.transactions.infrastructure.rest.data.requests.TransactionPostRequest
import org.balhom.transactionsapi.modules.transactions.infrastructure.rest.data.responses.TransactionResponse
import org.eclipse.microprofile.jwt.JsonWebToken
import org.jboss.resteasy.reactive.RestPath
import org.jboss.resteasy.reactive.RestQuery
import java.util.*

@Path(TransactionResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
class TransactionResource(private val service: TransactionService) {

    companion object {
        const val RESOURCE_PATH = "/api/v1/transaction"
    }

    @Inject
    lateinit var jwt: JsonWebToken

    @GET
    @Path("/{id}")
    fun getById(@RestPath id: UUID):
            TransactionResponse {
        val currencyProfile = service
            .getTransaction(
                ObjectIdUserProps(
                    id,
                    UUID.fromString(jwt.subject)
                )
            )
        return TransactionResponse.fromDomain(
            currencyProfile
        )
    }

    @GET
    fun getAll(
        @RestQuery
        @NotNull
        currencyProfileId: UUID,
        @BeanParam @Valid filterParams: TransactionFilterParams,
        @BeanParam @Valid pageParams: ApiPageParams,
        @RestQuery sortBy: TransactionSortEnum?,
    ): ApiPage<TransactionResponse> {

        val currencyProfileProps = ObjectIdUserProps(
            currencyProfileId,
            UUID.fromString(jwt.subject)
        )
        val sortAndFilterProps = TransactionSortAndFilterProps(
            month = filterParams.month,
            year = filterParams.year,
            minAmount = filterParams.minAmount,
            maxAmount = filterParams.maxAmount,
            textSearch = filterParams.textSearch,
            sortBy = sortBy ?: TransactionSortEnum.DATE_DESC,
        )

        val transactionsPage = service
            .getTransactions(
                GetAllTransactionsProps(
                    currencyProfileProps,
                    pageParams.toProps(),
                    sortAndFilterProps
                )
            )
        return transactionsPage
            .map { TransactionResponse.fromDomain(it) }
    }

    @POST
    fun create(@Valid request: TransactionPostRequest): Response {
        val userId = UUID.fromString(jwt.subject)

        val props = CreateTransactionProps(
            userId,
            request.toDomain(
                userId
            )
        )

        val createdProfile = service
            .createTransaction(props)

        return Response.status(
            Response.Status.CREATED
        )
            .entity(
                TransactionResponse
                    .fromDomain(createdProfile)
            )
            .build()
    }

    @PATCH
    @Path("/{id}")
    fun update(
        @RestPath id: UUID,
        @Valid request: TransactionPatchRequest
    ): Response {
        service.updateTransaction(
            request.toUpdateProps(
                id,
                UUID.fromString(jwt.subject)
            )
        )

        return Response
            .noContent()
            .build()
    }

    @DELETE
    @Path("/{id}")
    fun deleteById(@RestPath id: UUID): Response {
        service.deleteTransaction(
            ObjectIdUserProps(
                id,
                UUID.fromString(jwt.subject)
            )
        )
        return Response
            .noContent()
            .build()
    }

}