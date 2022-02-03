package p.lodz.pl.pas.model_web;

import java.util.UUID;

public class User {
    private String login;
    private String name;
    private String surname;
    private UUID uuid;
    private Boolean active;
    private AccessLevel accessLevel;
    private String password;

    public User(UUID uuid, String login, String password, String name, String surname, Boolean active) {
        this.uuid = uuid;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.active = active;
    }

    public User(String login, String password, String name, String surname, Boolean active) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.active = active;
    }

    public User() {

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

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", uuid=" + uuid +
                ", active=" + active +
                ", accessLevel=" + accessLevel +
                '}';
    }

    // ------------------------------------------------------------------------------------------------

}
