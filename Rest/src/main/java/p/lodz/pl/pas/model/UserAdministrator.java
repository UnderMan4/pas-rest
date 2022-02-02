package p.lodz.pl.pas.model;

import javax.annotation.security.DeclareRoles;
import java.util.UUID;

@DeclareRoles("UserAdministrator")
public class UserAdministrator extends User {
    public UserAdministrator(UUID uuid, String login, String password, String name, String surname, Boolean active) {
        super(uuid, login, password, name, surname, active);
    }

    public UserAdministrator(String login, String name, String surname, Boolean active) {
        super(login, name, surname, active);
    }

    @Override
    public AccessLevel getUserAccessLevel() {
        return AccessLevel.UserAdministrator;
    }
}
