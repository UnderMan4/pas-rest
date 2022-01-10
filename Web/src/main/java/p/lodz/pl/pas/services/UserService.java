package p.lodz.pl.pas.services;

import com.google.gson.Gson;
import p.lodz.pl.pas.model_web.User;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

public class UserService implements Serializable {
    private User newUser = new User();

    public UserService() {

    }

    public User getNewUser() {
        return newUser;
    }

    public List<User> getAllUsers() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("user").path("list").request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {
        });
    }

    public Response saveEditedUser(User user) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("user").path("editUserWithUUID").request()
                .post(Entity.json(new Gson().toJson(user)));
    }
}
