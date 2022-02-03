package p.lodz.pl.pas.services;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
public class ChangePasswordService implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());


    @Inject
    LoginService loginService;

    private WebTarget getClientWebTarget() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("user");

    }

    public FacesMessage changePassword(String oldPassword, String newPassword) {
        LOGGER.log(Level.INFO, "Changing password");
        Response response = getClientWebTarget()
                .path("changePassword")
                .request()
                .header("Authorization", "Bearer " + loginService.getToken())
                .post(Entity.text(newPassword));
        LOGGER.log(Level.INFO, "Changed password to " + newPassword);
        LOGGER.log(Level.INFO, "Response: " + response.getStatus());

        if (response.getStatus() == 202) {
            return new FacesMessage("Changed password");
        } else {
            return new FacesMessage("Error: " + response.getStatus());
        }
    }
}
