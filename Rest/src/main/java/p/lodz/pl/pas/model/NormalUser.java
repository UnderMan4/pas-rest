package p.lodz.pl.pas.model;

import javax.annotation.security.DeclareRoles;
import java.util.UUID;

@DeclareRoles("NormalUser")
public class NormalUser extends User {
    public NormalUser(UUID uuid, String login, String password, String name, String surname, Boolean active) {
        super(uuid, login, password, name, surname, active);
    }

    public NormalUser(String login, String name, String surname, Boolean active) {
        super(login, name, surname, active);
    }

    @Override
    public AccessLevel getUserAccessLevel() {
        return AccessLevel.NormalUser;
    }
}
