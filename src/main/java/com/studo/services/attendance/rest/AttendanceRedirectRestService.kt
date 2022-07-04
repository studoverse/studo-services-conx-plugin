package com.studo.services.attendance.rest

import at.campusonline.pub.auth.api.authinfo.AuthInfo
import at.campusonline.pub.auth.api.jaxrs.UserSessionDisabled
import at.campusonline.pub.auth.api.subject.SecuritySubject
import io.quarkus.security.Authenticated
import io.smallrye.jwt.build.Jwt
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.jwt.JsonWebToken
import java.net.URLDecoder
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriBuilder

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

    @ConfigProperty(name = "studo-services.token-secret")
    lateinit var secret: String

    @ConfigProperty(name = "studo-services.dal-base-url")
    lateinit var dalBaseUrl: String

    @ConfigProperty(name = "conx.public-api-url")
    lateinit var coPublicApiUrl: String

    /**
     * ensure the secret has the right length
     */
    private val sanitizedSecret get() = secret.substring(0, 32)

    /**
     * Redirect to the studo dal endpoint.
     */
    @GET
    @Authenticated
    fun redirect(@DefaultValue("") @QueryParam(value = "redirect") redirect: String): Response {
        if (authInfo.isAnonymous) {
            return Response.ok("this should not happen!!!!").build()
        }

        val redirectUrl = if (redirect.isEmpty()) null else URLDecoder.decode(redirect, "UTF-8")

        //We use a signed token, so the studo dal application can verify the token.
        val signedStudoCrossAuthJwtToken = Jwt.subject(subject.obfuscatedIdentityId)
                .preferredUserName(subject.identity.name)
                .jws()
                .signWithSecret(sanitizedSecret)

        // this uri redirect to the studo dal application
        // currently we use a fake endpoint to verify data is transported correctly
        val uri = UriBuilder
                .fromUri(dalBaseUrl)
                .queryParam(TOKEN_PARAMETER, signedStudoCrossAuthJwtToken)
                .apply { if (redirectUrl != null) queryParam("redirect", redirectUrl) }
                .build()
        return Response.temporaryRedirect(uri).build()
    }

    companion object {
        private const val TOKEN_PARAMETER = "token"
    }
}