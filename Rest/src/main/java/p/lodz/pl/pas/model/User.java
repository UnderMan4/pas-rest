package p.lodz.pl.pas.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.UUID;

import static p.lodz.pl.pas.RegexList.*;

public abstract class User implements SingableEntity, Serializable {

    @NotNull
    @Pattern(regexp = USERNAME_PATTERN, message = "Username must be between 2 and 20 characters")
    private String login;

    @Pattern(regexp = SURNAME_PATTERN, message = "Name must be between 2 and 20 characters")
    private String name;

    @Pattern(regexp = SURNAME_PATTERN, message = "Surname must be between 2 and 30 characters")
    private String surname;

    private UUID uuid;

    private Boolean active;

    @Pattern(regexp = PASSWORD_PATTERN, message = "Password must be between 2 and 30 characters")
    private String password;

    public User(UUID uuid, String login, String password, String name, String surname, Boolean active) {
        this.uuid = uuid;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.active = active;
        this.password = password;
    }

    public User(String login, String name, String surname, Boolean active) {
        this.login = login;
        this.name = name;
        this.surname = surname;
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

    public abstract AccessLevel getUserAccessLevel();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ------------------------------------------------------------------------------------------------

    @Override
    public String getSingablePayload() {
        return login + uuid.toString();
    }
}
