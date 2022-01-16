package p.lodz.pl.pas.model;

import java.util.UUID;

public class UserAdministrator extends User {
    public UserAdministrator(UUID uuid, String login, String name, String surname, Boolean active) {
        super(uuid, login, name, surname, active);
    }

    public UserAdministrator(String login, String name, String surname, Boolean active) {
        super(login, name, surname, active);
    }

    @Override
    public AccessLevel getUserAccessLevel() {
        return AccessLevel.UserAdministrator;
    }
}
