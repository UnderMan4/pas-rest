package p.lodz.pl.pas.controller;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import p.lodz.pl.pas.security.InMemoryIdentityStore;
import p.lodz.pl.pas.security.JWTAuthenticator;

import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("authenticate")
public class LoginController {

    public LoginController() {
    }

    @Inject
    private InMemoryIdentityStore identityStoreHandler;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces
    public Response authenticate(@NotNull String body) {
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        String login = jsonObject.get("login").getAsString();
        String password = jsonObject.get("password").getAsString();
        Credential credential = new UsernamePasswordCredential(login, password);
        CredentialValidationResult result = identityStoreHandler.validate(credential);

        if (result.getStatus() == CredentialValidationResult.Status.VALID) {
            return Response.accepted()
                    .type("application/jwt")
                    .entity(JWTAuthenticator.generateJWT(result))
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
