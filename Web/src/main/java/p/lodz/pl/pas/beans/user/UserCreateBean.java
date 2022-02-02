package p.lodz.pl.pas.beans.user;

import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.AccessLevel;
import p.lodz.pl.pas.model_web.UserDTO;
import p.lodz.pl.pas.services.UserService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
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
        newUser.setAccessLevel(AccessLevel.NormalUser);
        newUser.setActive(true);
    }

    public UserDTO getNewUser() {
        return newUser;
    }

    public String createNewUser() {
        try {
            FacesMessage message = userService.createUser(newUser);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (RESTException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
        }

        return "user";
    }
}
