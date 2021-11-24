package com.studo.services.attendance.rest

import at.campusonline.pub.auth.api.authinfo.AuthInfo
import at.campusonline.pub.auth.api.jaxrs.UserSessionDisabled
import at.campusonline.pub.auth.api.subject.SecuritySubject
import org.eclipse.microprofile.jwt.JsonWebToken
import javax.ws.rs.GET
import at.campusonline.pub.auth.rest.api.identities.AuthIdentityResource
import javax.ws.rs.core.UriBuilder
import com.studo.services.attendance.rest.AttendanceRedirectRestService
import io.quarkus.security.Authenticated
import io.smallrye.jwt.build.Jwt
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.inject.Inject
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Produces("application/json")
@UserSessionDisabled
@Path("attendance/redirect")
class AttendanceRedirectRestService {
    @Inject
    lateinit var subject: SecuritySubject

    @Inject
    lateinit var authInfo: AuthInfo

    @Inject
    lateinit var token: JsonWebToken

    @ConfigProperty(name = "studo-service.token-secret")
    lateinit var secret: String

    @ConfigProperty(name = "studo-service.dal-base-url")
    lateinit var dalBaseUrl: String

    @ConfigProperty(name = "conx.public-api-url")
    lateinit var coPublicApiUrl: String

    // example: https://trunkline.tugraz.at/trunk_dev/co
    val coBaseUrl: String get() = coPublicApiUrl.removeSuffix("/public")

    /**
     * Url of the studo-services [AttendanceRedirectRestService.redirect] endpoint
     */
    val loginRedirectUrl: String get() = "$coBaseUrl/studo/services/api/attendance/redirect"

    val keycloakAuthorizationUrl: String get() = "$coPublicApiUrl/auth/api/session/authorization"

    /**
     * ensure the secret has the right length
     */
    private val sanitizedSecret get() = secret.substring(0, 32)

    /**
     * Redirect to the studo dal endpoint.
     */
    @GET
    @Authenticated
    fun redirect(): Response {
        if (authInfo.isAnonymous) {
            return Response.ok("this should not happen!!!!").build()
        }

        //We use a signed token, so the studo dal application can verify the token.
        val signedStudoCrossAuthJwtToken = if (token.rawToken == null) {
            "anonymous"
        } else {
            Jwt.subject(subject.obfuscatedIdentityId)
                    .preferredUserName(subject.identity.name)
                    .jws()
                    .signWithSecret(sanitizedSecret)
        }

        // this uri redirect to the studo dal application
        // currently we use a fake endpoint to verify data is transported correctly
        val uri = UriBuilder
                .fromUri(dalBaseUrl)
                .queryParam(TOKEN_PARAMETER, signedStudoCrossAuthJwtToken)
                .build()
        return Response.temporaryRedirect(uri).build()
    }

    companion object {
        private const val TOKEN_PARAMETER = "token"
    }
}