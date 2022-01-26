package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.User;
import p.lodz.pl.pas.services.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class UserListBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Inject
    UserDetailsBean userDetailsBean;

    @Inject
    UserService userService;

    @Inject
    UserEditBean userEditBean;

    List<User> userList;

    public void setUserList(List<User> userList) {
        this.userList = userList;
        updateUserList();
    }

    public List<User> getUserList() {
        return userList;
    }

    @PostConstruct
    public void init() {
        updateUserList();
    }

    public UserListBean() {

    }

    public void updateUserList() {
        userList = userService.getAllUsers();
    }

    public String editUser(User user) {
        userEditBean.setEditedUser(user);
        return "editUser";
    }

    public void setUserActive(User user, Boolean value) {
        try {
            userService.setUserActive(user.getUuid(), value);
        } catch (RESTException r) {
            FacesMessage message = new FacesMessage(r.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
        }
        updateUserList();
    }

    public String detailsUser(User user) {
        userDetailsBean.setUser(user);
        userDetailsBean.userDetails();
        return "detailsUser";
    }
}
