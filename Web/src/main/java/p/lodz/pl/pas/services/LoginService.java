package p.lodz.pl.pas.services;


import com.nimbusds.jwt.SignedJWT;
import p.lodz.pl.pas.beans.ActiveUserBean;
import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.exceptions.WrongLoginException;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
public class LoginService implements Serializable {


    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private String token;
    // private String role;
    @Inject
    ActiveUserBean activeUserBean;

    private WebTarget getClientWebTarget() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("authenticate");

    }

    public void login(String login, String password) throws WrongLoginException, RESTException {
        Response response = getClientWebTarget()
                .request()
                .post(
                        Entity.json(
                                Json.createObjectBuilder()
                                        .add("login", login)
                                        .add("password", password)
                                        .build()
                        )
                );
        LOGGER.log(Level.INFO, "Request status: " + response.getStatus());
        if (response.getStatus() == 202) {
            token = response.readEntity(String.class);
            LOGGER.log(Level.INFO, "Token: " + token);
            SignedJWT jwt = null;
            try {
                jwt = SignedJWT.parse(token);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                assert jwt != null;
                LOGGER.log(Level.INFO, "ClaimSet: " + jwt.getJWTClaimsSet().getClaims().toString());
                String role = jwt.getJWTClaimsSet().getClaims().get("auth").toString();
                activeUserBean.setRole(role);
                activeUserBean.setUsername(jwt.getJWTClaimsSet().getClaims().get("sub").toString());
                activeUserBean.setAdministrator(role.equals("Admin"));
                activeUserBean.setResourceAdministrator(role.equals("ResourceAdministrator"));
                activeUserBean.setUserAdministrator(role.equals("UserAdministrator"));
                activeUserBean.setNormalUser(role.equals("NormalUser"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else if (response.getStatus() == 401) {
            throw new WrongLoginException("Wrong login or password");
        } else {
            throw new RESTException("Something went wrong");
        }
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "/index?faces-redirect=true";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // public String getRole() {
    //     return role;
    // }
    //
    // public void setRole(String role) {
    //     this.role = role;
    // }
    //
    // public String getUsername() {
    //     return username;
    // }
    //
    // public void setUsername(String username) {
    //     this.username = username;
    // }
}
