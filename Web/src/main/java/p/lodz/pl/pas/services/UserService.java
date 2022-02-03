package p.lodz.pl.pas.services;

import com.google.gson.Gson;
import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.model_web.User;
import p.lodz.pl.pas.model_web.UserDTO;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
public class UserService implements Serializable {

    @Inject
    LoginService loginService;

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    public UserService() {

    }

    private WebTarget getUserWebTarget() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("user");
    }

    public List<User> getAllUsers() {
        return getUserWebTarget()
                .path("list")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + loginService.getToken())
                .get(new GenericType<List<User>>() {
                });
    }

    public FacesMessage saveEditedUser(User user) throws RESTException {
        Response response = getUserWebTarget()
                .path("update")
                .request()
                .header("Authorization", "Bearer " + loginService.getToken())
                .post(Entity.json(new Gson().toJson(user)));
        LOGGER.log(Level.INFO, user.toString());
        LOGGER.log(Level.INFO, response.toString());
        if (response.getStatus() != 202) {
            return new FacesMessage(response.readEntity(String.class));
        } else {
            throw new RESTException(response.readEntity(String.class));
        }
    }

    public FacesMessage createUser(UserDTO user) throws RESTException {
        String requestString;
        User u = new User(
                user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getActive()
        );
        LOGGER.log(Level.INFO, "Creating user " + user.toString());
        switch (user.getAccessLevel()) {
            case Admin -> requestString = "createAdmin";
            case NormalUser -> requestString = "createNormalUser";
            case ResourceAdministrator -> requestString = "createResourceAdministrator";
            case UserAdministrator -> requestString = "createUserAdministrator";
            default -> throw new IllegalStateException("Unexpected value: " + user.getAccessLevel());
        }
        Response response = getUserWebTarget()
                .path(requestString)
                .request()
                .header("Authorization", "Bearer " + loginService.getToken())
                .post(Entity.json(new Gson()
                        .toJson(u)));
        LOGGER.log(Level.INFO, user.toString());
        LOGGER.log(Level.INFO, response.toString());
        if (response.getStatus() != 202) {
            return new FacesMessage(response.readEntity(String.class));
        } else {
            throw new RESTException(response.readEntity(String.class));
        }
    }

    public boolean setUserActive(UUID uuid, boolean status) throws RESTException {
        Response response = getUserWebTarget()
                .path("setUserActive")
                .queryParam("UUID", uuid)
                .queryParam("status", status)
                .request()
                .header("Authorization", "Bearer " + loginService.getToken())
                .get();
        if (response.getStatus() == 202) {
            LOGGER.log(Level.INFO, "Setting user " + uuid + " status active");
        } else {
            LOGGER.log(Level.WARNING, "Cannot set user " + uuid + " status active");

            throw new RESTException("Cannot set user " + uuid + " status active");
        }
        return true;
    }

    public List<User> searchByLogin(String login) {
        return getUserWebTarget()
                .path("searchByLogin")
                .queryParam("login", login)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + loginService.getToken())
                .get(new GenericType<List<User>>() {
                });
    }

    public List<User> searchByUUID(String uuid) {
        return getUserWebTarget()
                .path("searchByUUID")
                .queryParam("UUID", uuid)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + loginService.getToken())
                .get(new GenericType<List<User>>() {
                });
    }

    public List<User> search(String s) {
        return getUserWebTarget()
                .path("search")
                .queryParam("s", s)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + loginService.getToken())
                .get(new GenericType<List<User>>() {
                });
    }

}
