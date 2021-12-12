package p.lodz.pl.pas.manager;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.model.AccessLevel;
import p.lodz.pl.pas.model.User;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class UserManager {
    ArrayList<User> users;

    public UserManager() {
        users = new ArrayList<>();
        this.createUser("jkowalski", "Jan", "Kowalski", true, AccessLevel.User);
        this.createUser("jjjkowal", "Jaroslaw", "Kowalski", true, AccessLevel.ResourceAdministrator);
        this.createUser("ttttt", "Tomasz", "Kowalski", true, AccessLevel.User);
        this.createUser("Restitutor", "Lucius", "Aurelianus", true, AccessLevel.UserAdministrator);
    }

    public synchronized boolean createUser(String login, String name, String surname, Boolean active, AccessLevel accessLevel) {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        }
        while (!checkIfUUIDExists(uuid));
        return users.add(new User(uuid, login, name, surname, active, accessLevel));
    }

    public ArrayList<User> getUserList() {
        return users;
    }

    public User findUser(String login) throws ItemNotFoundException {
        Optional<User> optional = users.stream().filter(u -> u.getLogin().equals(login)).findFirst();
        return optional.orElseThrow(() -> new ItemNotFoundException(
                "User with login " + login + " not found"
        ));
    }

    public User findUsers(String login) throws ItemNotFoundException {
        Optional<User> optional = users.stream().filter(u -> u.getLogin().contains(login)).findAny();
        return optional.orElseThrow(() -> new ItemNotFoundException(
                "User with login " + login + " not found"
        ));
    }

    public User findUser(UUID uuid) throws ItemNotFoundException {
        Optional<User> optional = users.stream().filter(u -> u.getUuid().equals(uuid)).findFirst();
        return optional.orElseThrow(() -> new ItemNotFoundException(
                "User with UUID " + uuid.toString() + " not found"
        ));
    }

    public boolean checkIfUUIDExists(UUID uuid) {
        for ( User u : users) {
            if (u.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfLoginExists(String login) {
        for ( User u : users) {
            if (u.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public synchronized boolean editUserWithUUID(UUID uuid, String login, String name, String surname, Boolean active, AccessLevel accessLevel) {
        // uuid must exist
        if (checkIfUUIDExists(uuid)) {
            return false;
        }

        User u = null;
        try {
            u = findUser(uuid);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
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

    public synchronized boolean setUserActive(UUID uuid, boolean active) {
        // uuid must exist
        if (checkIfUUIDExists(uuid)) {
            return false;
        }
        User u = null;
        try {
            u = findUser(uuid);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        u.setActive(active);
        return true;
    }

}
