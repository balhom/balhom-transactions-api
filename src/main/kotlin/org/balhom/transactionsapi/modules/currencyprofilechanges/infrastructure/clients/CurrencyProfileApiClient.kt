package org.balhom.transactionsapi.modules.currencyprofilechanges.infrastructure.clients

import io.quarkus.oidc.token.propagation.common.AccessToken
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import org.balhom.transactionsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileSharedUser
import org.balhom.transactionsapi.modules.currencyprofilechanges.infrastructure.clients.responses.CurrencyProfileResponse
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.jboss.resteasy.reactive.RestPath
import java.util.*

@Path("/api/v1/currency-profile")
@RegisterRestClient(configKey = "currency-profiles-api")
@AccessToken
interface CurrencyProfileApiClient {

    @GET
    @Path("/{id}")
    fun getById(@RestPath id: UUID): CurrencyProfileResponse

    @GET
    @Path("/{id}/users")
    fun getSharedUsersById(@RestPath id: UUID): List<CurrencyProfileSharedUser>
}
