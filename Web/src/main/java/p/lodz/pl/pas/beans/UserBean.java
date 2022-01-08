package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model.User;
import p.lodz.pl.pas.services.Const;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

/**
 * Ziarno używane w przykładach dot. ziaren i atrybutów w poszczególnych zasięgach.
 *
 * @author java
 */

@Named
@SessionScoped
public class UserBean implements Serializable {

    public UserBean() {
    }

    public List<User> getUserList() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("user").path("list").request(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {
        });
    }


}
