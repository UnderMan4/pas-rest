package p.lodz.pl.pas.services;

import com.google.gson.Gson;
import p.lodz.pl.pas.model_web.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

import static p.lodz.pl.pas.model_web.AccessLevel.UserAdministrator;

public class UserService implements Serializable {

    public UserService() {

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

    public Response createUser(UserDTO user) {
        String requestString;
        User u = new User(user.getLogin(), user.getName(), user.getSurname(), user.getActive());
        switch (user.getAccessLevel())
        {
            case Admin -> requestString = "createAdmin";
            case NormalUser -> requestString = "createNormalUser";
            case ResourceAdministrator -> requestString = "createResourceAdministrator";
            case UserAdministrator -> requestString = "createUserAdministrator";
            default -> throw new IllegalStateException("Unexpected value: " + user.getAccessLevel());
        }
        return getUserWebTarget().path(requestString).request()
                .post(Entity.json(new Gson().toJson(u)));
    }

    public Response setUserActive(UUID uuid, boolean status) {
        return getUserWebTarget().path("setUserActive").queryParam("UUID", uuid)
                .queryParam("status", status).request().get();
    }

    public List<User> searchByLogin(String login) {
        return getUserWebTarget().path("searchByLogin").queryParam("login", login).request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {
        });
    }

    public List<User> searchByUUID(String uuid) {
        return getUserWebTarget().path("searchByUUID").queryParam("UUID", uuid).request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {
        });
    }
}
