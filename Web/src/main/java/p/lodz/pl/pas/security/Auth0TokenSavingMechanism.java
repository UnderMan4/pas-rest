package p.lodz.pl.pas.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nimbusds.jwt.SignedJWT;
import p.lodz.pl.pas.services.LoginService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

// @Alternative
// @ApplicationScoped
public class Auth0TokenSavingMechanism implements HttpAuthenticationMechanism {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());


    @Inject
    LoginService loginService;


    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) {

        if (request.getRequestURL().toString().endsWith("/api/authenticate") || request.getRequestURL().toString().endsWith("/login.xhtml")) {
            return context.doNothing();
        }

        try {
            String token = loginService.getToken();
            SignedJWT jwt = SignedJWT.parse(token);
            String login = jwt.getJWTClaimsSet().getSubject();
            String groups = jwt.getJWTClaimsSet().getStringClaim("role");
            LOGGER.log(Level.INFO, "ClaimSet: " + jwt.getJWTClaimsSet().getClaims().toString());

            return context.notifyContainerAboutLogin(login, new HashSet<>(Arrays.asList(groups.split(","))));
        } catch (JWTVerificationException | ParseException e) {
            e.printStackTrace();
            return context.responseUnauthorized();
        }
    }

    @Override
    public AuthenticationStatus secureResponse(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        return HttpAuthenticationMechanism.super.secureResponse(request, response, httpMessageContext);
    }

    @Override
    public void cleanSubject(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        HttpAuthenticationMechanism.super.cleanSubject(request, response, httpMessageContext);
    }
}
