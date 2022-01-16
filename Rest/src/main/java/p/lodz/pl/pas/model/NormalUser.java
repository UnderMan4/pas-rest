package p.lodz.pl.pas.model;

import java.util.UUID;

public class NormalUser extends User {
    public NormalUser(UUID uuid, String login, String name, String surname, Boolean active) {
        super(uuid, login, name, surname, active);
    }

    public NormalUser(String login, String name, String surname, Boolean active) {
        super(login, name, surname, active);
    }

    @Override
    public AccessLevel getUserAccessLevel() {
        return AccessLevel.NormalUser;
    }
}
