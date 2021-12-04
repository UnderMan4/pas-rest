package p.lodz.pl.pas.model;

import java.util.UUID;

public class User {
    String login;
    String name;
    String surname;
    UUID uuid;
    Boolean active;

    public User(String login, String name, String surname, UUID uuid, Boolean active) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.uuid = uuid;
        this.active = active;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    // ------------------------------------------------------------------------------------------------

}
