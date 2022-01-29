package p.lodz.pl.pas.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;

import static p.lodz.pl.pas.security.JWTAuthenticator.validateJWT;

@ApplicationScoped
public class Auth0LoginAuthenticationMechanism implements HttpAuthenticationMechanism {

    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String BEARER = "Bearer ";


    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException {

        if (request.getRequestURL().toString().endsWith("/api/authenticate")) {
            return context.doNothing();
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            return context.responseUnauthorized();
        }

        String jwtSerializedToken = authorizationHeader.substring(BEARER.length()).trim();

        try {
            DecodedJWT jwt = validateJWT(jwtSerializedToken);
            String login = jwt.getSubject();
            String groups = jwt.getClaim("auth").asString();

            return context.notifyContainerAboutLogin(login, new HashSet<>(Arrays.asList(groups.split(","))));
        } catch (JWTVerificationException e) {
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
