package p.lodz.pl.pas.controller;

import p.lodz.pl.pas.model.AccessLevel;
import p.lodz.pl.pas.model.User;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class UserController {
    ArrayList<User> userArrayList;

    public UserController() {
        userArrayList = new ArrayList<>();
        this.createUser("jkowalski", "Jan", "Kowalski", true, AccessLevel.User);
        this.createUser("jjjkowal", "Jaroslaw", "Kowalski", true, AccessLevel.ResourceAdministrator);
        this.createUser("ttttt", "Tomasz", "Kowalski", true, AccessLevel.User);
        this.createUser("Restitutor", "Lucius", "Aurelianus", true, AccessLevel.UserAdministrator);
    }

    public boolean createUser(String login, String name, String surname, Boolean active, AccessLevel accessLevel) {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        }
        while (!checkIfUUIDExists(uuid));
        return userArrayList.add(new User(uuid, login, name, surname, active, accessLevel));
    }

    public ArrayList<User> getUserList() {
        return userArrayList;
    }

    public User findUser(String login) {
        Optional<User> optional = userArrayList.stream().filter(u -> u.getLogin().equals(login)).findFirst();
        return optional.orElse(null);
    }

    public User findAllUser(String login) {
        Optional<User> optional = userArrayList.stream().filter(u -> u.getLogin().contains(login)).findAny();
        return optional.orElse(null);
    }

    public User findUser(UUID uuid) {
        Optional<User> optional = userArrayList.stream().filter(u -> u.getUuid().equals(uuid)).findFirst();
        return optional.orElse(null);
    }

    public boolean checkIfUUIDExists(UUID uuid) {
        for ( User u : userArrayList ) {
            if (u.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfLoginExists(String login) {
        for ( User u : userArrayList ) {
            if (u.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public boolean editUserWithUUID(UUID uuid, String login, String name, String surname, Boolean active, AccessLevel accessLevel) {
        // uuid must exist
        if (checkIfUUIDExists(uuid)) {
            return false;
        }

        User u = findUser(uuid);
        // if login is being changed
        if (!u.getLogin().equals(login)) {
            // check if login is already in the database
            if (checkIfLoginExists(login)) {
                return false;
            }
        }
        u.setLogin(login);
        u.setName(name);
        u.setSurname(surname);
        u.setActive(active);
        u.setAccessLevel(accessLevel);
        return true;
    }

    public boolean setUserActive(UUID uuid, boolean active) {
        // uuid must exist
        if (checkIfUUIDExists(uuid)) {
            return false;
        }
        User u = findUser(uuid);
        u.setActive(active);
        return true;
    }

}
