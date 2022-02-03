package p.lodz.pl.pas.services;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.Serializable;

@SessionScoped
public class ChangePasswordService implements Serializable {

    @Inject
    LoginService loginService;

    private WebTarget getClientWebTarget() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("job");

    }

    public void changePassword(String oldPassword, String newPassword) {
        
    }
}
