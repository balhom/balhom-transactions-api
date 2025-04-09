package org.balhom.transactionsapi.modules.currencyprofilechanges.infrastructure.clients

import io.quarkus.oidc.token.propagation.common.AccessToken
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import org.balhom.transactionsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileSharedUser
import org.balhom.transactionsapi.modules.currencyprofilechanges.infrastructure.clients.responses.CurrencyProfileResponse
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import java.util.*

@Path("/api/v1/currency-profile")
@RegisterRestClient(configKey = "currency-profiles-api")
@AccessToken
interface CurrencyProfileApiClient {

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: UUID): CurrencyProfileResponse

    @GET
    @Path("/{id}/users")
    fun getSharedUsersById(@PathParam("id") id: UUID): List<CurrencyProfileSharedUser>
}
