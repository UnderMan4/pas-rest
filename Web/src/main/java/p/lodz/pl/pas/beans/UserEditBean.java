package p.lodz.pl.pas.beans;

import com.google.gson.Gson;
import p.lodz.pl.pas.model_web.User;
import p.lodz.pl.pas.services.Const;
import p.lodz.pl.pas.services.UserService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserEditBean implements Serializable {
    private final Logger LOGGER = Logger.getLogger(UserCreateBean.class.getName());
    private User editedUser;
    @Inject
    UserService userService;

    public UserEditBean() {

    }

    public User getEditedUser() {
        return editedUser;
    }

    public void setEditedUser(User editedUser) {
        this.editedUser = editedUser;
    }

    public String saveEditedUser() {
        if (editedUser != null) {
            LOGGER.log(Level.INFO, editedUser.toString());
            Response response = userService.saveEditedUser(editedUser);
            LOGGER.log(Level.INFO, response.toString());
        } else {
            throw new IllegalArgumentException("Edited user is null");

        }
        return "user";
    }
}
