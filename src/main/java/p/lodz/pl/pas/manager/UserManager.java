package p.lodz.pl.pas.manager;

import p.lodz.pl.pas.DAO.UserDAO;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.LoginNotUnique;
import p.lodz.pl.pas.model.AccessLevel;
import p.lodz.pl.pas.model.User;

import java.util.ArrayList;
import java.util.UUID;

public class UserManager {
    UserDAO userDAO;

    public UserManager() {
        userDAO = new UserDAO();
    }

    public synchronized boolean createUser(String login, String name, String surname, Boolean active, AccessLevel accessLevel) throws LoginNotUnique {
        if (userDAO.checkLoginUnique(login)) {
            return userDAO.create(new User(UUID.randomUUID(), login, name, surname, active, accessLevel));
        } else {
            throw new LoginNotUnique("Login " + login + " is not unique");
        }
    }

    public ArrayList<User> getUserList() {
        return userDAO.readAll();
    }

    public User findUser(String login) throws ItemNotFoundException {
        return userDAO.findUser(login);
    }

    public ArrayList<User> findUsers(String login) throws ItemNotFoundException {
        return userDAO.findUsers(login);
    }

    public User findUser(UUID uuid) throws ItemNotFoundException {
        return userDAO.readOne(uuid);
    }

    public boolean checkLoginUnique(String login) {
        return userDAO.checkLoginUnique(login);
    }

    public synchronized boolean editUserWithUUID(UUID uuid, String login, String name, String surname, Boolean active,
                                                 AccessLevel accessLevel) throws ItemNotFoundException {
        return userDAO.update(new User(uuid, login, name, surname, active, accessLevel));
    }

    public synchronized boolean setUserActive(UUID uuid, boolean active) throws ItemNotFoundException {
        return userDAO.setUserActive(uuid, active);
    }

}