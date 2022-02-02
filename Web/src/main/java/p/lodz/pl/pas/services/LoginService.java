package p.lodz.pl.pas.services;


import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.logging.Logger;

public class LoginService implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private String token;

    private WebTarget getClientWebTarget() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("authenticate");

    }

    public void login(String login, String password) {
        Response response = getClientWebTarget()
                .request()
                .post(
                        Entity.json(
                                Json.createObjectBuilder()
                                        .add("login", login)
                                        .add("password", password)
                                        .build()
                        )
                );
        if (response.getStatus() == 202) {
            token = response.readEntity(String.class);
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
