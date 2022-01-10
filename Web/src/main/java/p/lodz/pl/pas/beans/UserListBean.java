package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.User;
import p.lodz.pl.pas.services.UserService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Ziarno używane w przykładach dot. ziaren i atrybutów w poszczególnych zasięgach.
 *
 * @author java
 */

@Named
@SessionScoped
public class UserListBean implements Serializable {
    
    @Inject
    UserService userService;

    @Inject
    UserEditBean userEditBean;

    public UserListBean() {
    }

    public List<User> getUserList() {
        return userService.getAllUsers();
    }

    public String editUser(User user) {
        userEditBean.setEditedUser(user);
        return "editUser";
    }
}
