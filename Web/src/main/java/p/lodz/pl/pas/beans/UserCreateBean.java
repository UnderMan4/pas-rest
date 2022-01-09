package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.UserDTO;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserCreateBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(UserCreateBean.class.getName());
    private UserDTO newUser = new UserDTO();

    public UserCreateBean() {
    }

    public UserDTO getNewUser() {
        return newUser;
    }
}
