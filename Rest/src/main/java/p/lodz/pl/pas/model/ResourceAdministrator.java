package p.lodz.pl.pas.model;

import java.util.UUID;

public class ResourceAdministrator extends User {
    public ResourceAdministrator(UUID uuid, String login, String name, String surname, Boolean active) {
        super(uuid, login, name, surname, active);
    }

    public ResourceAdministrator(String login, String name, String surname, Boolean active) {
        super(login, name, surname, active);
    }

    @Override
    public AccessLevel getUserAccessLevel() {
        return AccessLevel.ResourceAdministrator;
    }
}
