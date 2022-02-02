package p.lodz.pl.pas.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.security.DeclareRoles;
import java.util.UUID;

@DeclareRoles("ResourceAdministrator")
public class ResourceAdministrator extends User {
    public ResourceAdministrator(UUID uuid, String login, String password, String name, String surname, Boolean active) {
        super(uuid, login, password, name, surname, active);
    }

    public ResourceAdministrator(String login, String name, String surname, Boolean active) {
        super(login, name, surname, active);
    }

    @Override
    public AccessLevel getUserAccessLevel() {
        return AccessLevel.ResourceAdministrator;
    }
}
