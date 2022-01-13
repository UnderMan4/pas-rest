package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.User;
import p.lodz.pl.pas.services.UserService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserSearchBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Inject
    UserService userService;

    private List<User> userList;

    private String uuid;

    private String login;

    public UserSearchBean() {
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void searchUserByUUID() {
        if (uuid != null) {
            LOGGER.log(Level.INFO, "Searching for job with uuid " + uuid.toString());
            try {
                setUserList(userService.searchByUUID(uuid));
                LOGGER.log(Level.INFO, userList.toString());
            } catch (javax.ws.rs.NotFoundException e) {
                LOGGER.log(Level.INFO, e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
                setUserList(null);
            }
            LOGGER.log(Level.INFO, userList.toString());
        } else {
            throw new IllegalArgumentException("Job uuid is empty");
        }

    }

    public void searchUserByLogin() {
        if (uuid != null) {
            LOGGER.log(Level.INFO, "Searching for job with uuid " + uuid.toString());

            try {
                setUserList(userService.searchByLogin(login));
                LOGGER.log(Level.INFO, userList.toString());
            } catch (javax.ws.rs.NotFoundException e) {
                LOGGER.log(Level.INFO, e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
                setUserList(null);
            }
            LOGGER.log(Level.INFO, userList.toString());
        } else {
            throw new IllegalArgumentException("Job login is empty");
        }
    }
}
