package p.lodz.pl.pas.beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ActiveUserBean implements Serializable {

    String username;
    String role;

    private boolean administrator;
    private boolean resourceAdministrator;
    private boolean userAdministrator;
    private boolean normalUser;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public boolean isResourceAdministrator() {
        return resourceAdministrator;
    }

    public void setResourceAdministrator(boolean resourceAdministrator) {
        this.resourceAdministrator = resourceAdministrator;
    }

    public boolean isUserAdministrator() {
        return userAdministrator;
    }

    public void setUserAdministrator(boolean userAdministrator) {
        this.userAdministrator = userAdministrator;
    }

    public boolean isNormalUser() {
        return normalUser;
    }

    public void setNormalUser(boolean normalUser) {
        this.normalUser = normalUser;
    }
}


