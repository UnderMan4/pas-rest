package p.lodz.pl.pas.services;

import p.lodz.pl.pas.model_web.User;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

public class UserService implements Serializable {
    private User newUser = new User();

    public UserService() {

    }

    public User getNewUser() {
        return newUser;
    }

    public List<User> getAllJobs() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("user").path("list").request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {
        });
    }
}
