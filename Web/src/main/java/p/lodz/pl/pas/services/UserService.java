package p.lodz.pl.pas.services;

import com.google.gson.Gson;
import p.lodz.pl.pas.model_web.User;
import p.lodz.pl.pas.model_web.UserDTO;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class UserService implements Serializable {
    private final User newUser = new User();

    public UserService() {

    }

    public User getNewUser() {
        return newUser;
    }

    private WebTarget getUserWebTarget(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("user");
    }

    public List<User> getAllUsers() {
        return getUserWebTarget().path("list").request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {
        });
    }

    public Response saveEditedUser(User user) {
        return getUserWebTarget().path("editUserWithUUID").request()
                .post(Entity.json(new Gson().toJson(user)));
    }

    public Response createUser(UserDTO newUser) {
        return getUserWebTarget().path("create").request()
                .post(Entity.json(new Gson().toJson(newUser)));
    }

    public Response setUserActive(UUID uuid, boolean status) {
        return getUserWebTarget().path("setUserActive").queryParam("UUID", uuid)
                .queryParam("status", status).request().get();
    }
}
