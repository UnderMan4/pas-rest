package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.User;
import p.lodz.pl.pas.services.UserService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserListBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Inject
    UserService userService;

    @Inject
    UserEditBean userEditBean;

    public UserListBean() {
    }

    public List<User> getUserList() {
        return userService.getAllUsers();
    }

    public String editUser(User user) {
        userEditBean.setEditedUser(user);
        return "editUser";
    }

    public void activateUser(User user) {
        Response response = userService.setUserActive(user.getUuid(), true);
        if (response.getStatus() == 202) {
            LOGGER.log(Level.INFO, "Setting user " + user.getUuid() + " status active");
        } else {
            LOGGER.log(Level.WARNING, "Cannot set user " + user.getUuid() + " status active");
            FacesMessage message = new FacesMessage(response.readEntity(String.class));
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
        }
    }

    public void deactivateUser(User user) {
        Response response = userService.setUserActive(user.getUuid(), false);
        if (response.getStatus() == 202) {
            LOGGER.log(Level.INFO, "Setting user " + user.getUuid() + " status inactive");
        } else {
            LOGGER.log(Level.WARNING, "Cannot set user " + user.getUuid() + " status inactive");
            FacesMessage message = new FacesMessage(response.readEntity(String.class));
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
        }
    }
}
