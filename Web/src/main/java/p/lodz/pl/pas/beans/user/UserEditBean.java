package p.lodz.pl.pas.beans.user;

import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.User;
import p.lodz.pl.pas.services.UserService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserEditBean implements Serializable {
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
        try {
            FacesMessage message = userService.saveEditedUser(editedUser);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (RESTException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
        }
        return "user";
    }
}
