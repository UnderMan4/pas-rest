package p.lodz.pl.pas.model;

import java.util.UUID;

public class Admin extends User {
    public Admin(UUID uuid, String login, String name, String surname, Boolean active) {
        super(uuid, login, name, surname, active);
    }

    public Admin(String login, String name, String surname, Boolean active) {
        super(login, name, surname, active);
    }

    @Override
    public AccessLevel getUserAccessLevel() {
        return AccessLevel.Admin;
    }
}
