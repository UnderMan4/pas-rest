package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.exceptions.WrongLoginException;
import p.lodz.pl.pas.services.LoginService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


@Named
@SessionScoped
public class LoginBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private String username;
    private String password;
    private boolean loggedIn = false;


    @Inject
    LoginService loginService;

    // private boolean isAdministrator;
    // private boolean isResourceAdministrator;
    // private boolean isUserAdministrator;
    // private boolean isNormalUser;


    public boolean login() {
        try {
            loginService.login(username, password);
            LOGGER.log(Level.INFO, username + " logged in");
            // ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            // context.redirect(context.getRequestContextPath() + "/start.xhtml");
            // LOGGER.log(Level.INFO, "Role: " + loginService.getRole());
            //
            // boolean isAdministrator = loginService.getRole().equals("Admin");
            // LOGGER.log(Level.INFO, "Admin: " + isAdministrator);
            // boolean isResourceAdministrator = loginService.getRole().equals("ResourceAdministrator");
            // LOGGER.log(Level.INFO, "ResourceAdministrator: " + isResourceAdministrator);
            // boolean isUserAdministrator = loginService.getRole().equals("UserAdministrator");
            // LOGGER.log(Level.INFO, "UserAdministrator: " + isUserAdministrator);
            // boolean isNormalUser = loginService.getRole().equals("NormalUser");
            // LOGGER.log(Level.INFO, "NormalUser: " + isNormalUser());
            loggedIn = true;
            return true;
        } catch (WrongLoginException | RESTException e) {
            // return new FacesMessage("Login failed: " + e.getMessage());
            return false;
        }
    }

    public boolean logout() {
        loginService.logout();
        return true;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    // public boolean isAdministrator() {
    //     LOGGER.log(Level.INFO, "Admin2: " + isAdministrator);
    //
    //     return isAdministrator;
    //     // return true;
    // }
    //
    // public boolean isResourceAdministrator() {
    //     return isResourceAdministrator;
    // }
    //
    // public boolean isUserAdministrator() {
    //     return isUserAdministrator;
    // }
    //
    // public boolean isNormalUser() {
    //     return isNormalUser;
    // }
}
