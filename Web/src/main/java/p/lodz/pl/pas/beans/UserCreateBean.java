package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.AccessLevel;
import p.lodz.pl.pas.model_web.UserDTO;
import p.lodz.pl.pas.services.UserService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserCreateBean implements Serializable {

    @Inject
    UserService userService;
    private final Logger LOGGER = Logger.getLogger(getClass().getName());
    private final UserDTO newUser = new UserDTO();

    public UserCreateBean() {
        // default access level
        newUser.setAccessLevel(AccessLevel.User);
        newUser.setActive(true);
    }

    public UserDTO getNewUser() {
        return newUser;
    }

    public String createNewUser() {
        if (newUser.getLogin() != null) {
            LOGGER.log(Level.INFO, newUser.toString());
            Response response = userService.createUser(newUser);
            LOGGER.log(Level.INFO, response.toString());
        } else {
            throw new IllegalArgumentException("Name is null");
        }
        return "user";
    }
}
