package org.balhom.transactionsapi.modules.transactions.infrastructure.rest

import io.quarkus.security.Authenticated
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.balhom.transactionsapi.common.data.props.DocIdAndObjectIdUserProps
import org.balhom.transactionsapi.common.data.props.ObjectIdUserProps
import org.balhom.transactionsapi.modules.transactions.application.TransactionDocumentService
import org.balhom.transactionsapi.modules.transactions.domain.props.UploadTransactionDocumentProps
import org.balhom.transactionsapi.modules.transactions.infrastructure.rest.data.responses.TransactionDocumentResponse
import org.eclipse.microprofile.jwt.JsonWebToken
import org.jboss.resteasy.reactive.RestForm
import org.jboss.resteasy.reactive.RestPath
import java.io.File
import java.util.*

@Path(TransactionDocumentResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
class TransactionDocumentResource(private val service: TransactionDocumentService) {

    companion object {
        const val RESOURCE_PATH = "/api/v1/transaction"
    }

    @Inject
    lateinit var jwt: JsonWebToken

    @GET
    @Path("/{transactionId}/doc/{docId}")
    fun getById(
        @RestPath transactionId: UUID,
        @RestPath docId: UUID
    ): TransactionDocumentResponse {
        val transactionProps = ObjectIdUserProps(
            transactionId,
            UUID.fromString(jwt.subject)
        )

        val docUrl = service
            .getTransactionDocumentUrl(
                DocIdAndObjectIdUserProps(
                    docId,
                    transactionProps
                )
            )
        return TransactionDocumentResponse(
            docUrl
        )
    }


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/{transactionId}/doc")
    fun upload(
        @RestPath transactionId: UUID,
        @RestForm("file") document: File,
        @RestForm name: String,
        @RestForm mimetype: String
    ): Response {
        val transactionProps = ObjectIdUserProps(
            transactionId,
            UUID.fromString(jwt.subject)
        )

        val props = UploadTransactionDocumentProps(
            transactionProps,
            name,
            document,
            mimetype
        )

        service.uploadTransactionDocument(props)

        return Response
            .noContent()
            .build()
    }

    @DELETE
    @Path("/{transactionId}/doc/{docId}")
    fun deleteById(
        @RestPath transactionId: UUID,
        @RestPath docId: UUID,
    ): Response {
        val transactionProps = ObjectIdUserProps(
            transactionId,
            UUID.fromString(jwt.subject)
        )

        service.deleteTransactionDocument(
            DocIdAndObjectIdUserProps(
                docId,
                transactionProps
            )
        )

        return Response
            .noContent()
            .build()
    }
}