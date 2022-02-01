package p.lodz.pl.pas.manager;

import p.lodz.pl.pas.DAO.UserDAO;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.LoginNotUnique;
import p.lodz.pl.pas.model.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.UUID;

public class UserManager {
    @Inject
    UserDAO userDAO;

    public UserManager() {
        userDAO = new UserDAO();
    }

    private User createUserObject(UUID uuid, String login, String name, String surname, Boolean active, AccessLevel accessLevel) {
        User u;
        switch (accessLevel) {
            case ResourceAdministrator -> u = new ResourceAdministrator(uuid, login, name, surname, active);
            case NormalUser -> u = new NormalUser(uuid, login, name, surname, active);
            case Admin -> u = new Admin(uuid, login, name, surname, active);
            case UserAdministrator -> u = new UserAdministrator(uuid, login, name, surname, active);
            default -> throw new IllegalStateException("Unexpected value: " + accessLevel);
        }
        return u;
    }

    public synchronized boolean createUser(String login, String name, String surname, Boolean active, AccessLevel accessLevel) throws LoginNotUnique {
        if (userDAO.checkLoginUnique(login)) {
            return userDAO.create(createUserObject(null, login, name, surname, active, accessLevel));
        } else {
            throw new LoginNotUnique("Login " + login + " is not unique");
        }
    }

    public ArrayList<User> getUserList() {
        return userDAO.readAll();
    }

    public User findUser(UUID uuid) throws ItemNotFoundException {
        return userDAO.readOne(uuid);
    }

    // returns true if login is Unique
    public boolean checkLoginUnique(String login) {
        return userDAO.checkLoginUnique(login);
    }

    public synchronized boolean editUserWithUUID(UUID uuid, String login, String name, String surname, Boolean active,
                                                 AccessLevel accessLevel) throws ItemNotFoundException {

        return userDAO.update(createUserObject(uuid, login, name, surname, active, accessLevel));
//        if (userDAO.readOne(uuid).getUserAccessLevel().equals(accessLevel)) {
//            // we dont change user role
//
//        } else {
//
//            return userDAO.updateRole(createUserObject(uuid, login, name, surname, active, accessLevel));
//        }
//

    }

    public synchronized boolean setUserActive(UUID uuid, boolean active) throws ItemNotFoundException {
        return userDAO.setUserActive(uuid, active);
    }

    // search by UUID and return all matching results
    public ArrayList<User> searchByUUID(String uuid) throws ItemNotFoundException {
        return userDAO.searchByUUID(uuid);
    }

    public ArrayList<User> findUsersByLogin(String login) throws ItemNotFoundException {
        return userDAO.findUsersByLogin(login);
    }

    public ArrayList<User> search(String s) throws ItemNotFoundException {
        return userDAO.search(s);
    }
}
