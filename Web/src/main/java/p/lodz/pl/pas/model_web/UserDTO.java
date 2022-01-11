package p.lodz.pl.pas.model_web;

public class UserDTO {
    private String login;
    private String name;
    private String surname;
    private Boolean active;
    private AccessLevel accessLevel;

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

    @Override
    public String toString() {
        return "UserDTO{"
                + "login='" + login + '\'' +
                ", name={" + name + '\'' +
                ", surname={" + surname + '\'' +
                ", active={" + active + '\'' +
                ", accessLevel={" + accessLevel + '\'' +
                '}';

    }
}
