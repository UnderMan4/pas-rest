package p.lodz.pl.pas.beans.user;

import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.User;
import p.lodz.pl.pas.services.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserListBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Inject
    UserDetailsBean userDetailsBean;

    @Inject
    UserService userService;

    @Inject
    UserEditBean userEditBean;

    private List<User> userList;

    private String search = "";

    @PostConstruct
    public void init() {
        if (search == "") {
            userList = userService.getAllUsers();

        } else {
            userList = userService.search(search);
        }
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public UserListBean() {

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
        init();
    }

    public String detailsUser(User user) {
        userDetailsBean.setUser(user);
        userDetailsBean.userDetails();
        return "detailsUser";
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
