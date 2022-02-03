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

    private User createUserObject(UUID uuid, String login, String password, String name, String surname, Boolean active, AccessLevel accessLevel) {
        User u;
        switch (accessLevel) {
            case ResourceAdministrator -> u = new ResourceAdministrator(uuid, login, password, name, surname, active);
            case NormalUser -> u = new NormalUser(uuid, login, password, name, surname, active);
            case Admin -> u = new Admin(uuid, login, password, name, surname, active);
            case UserAdministrator -> u = new UserAdministrator(uuid, login, password, name, surname, active);
            default -> throw new IllegalStateException("Unexpected value: " + accessLevel);
        }
        return u;
    }

    public synchronized boolean createUser(String login, String password, String name, String surname, Boolean active, AccessLevel accessLevel) throws LoginNotUnique {
        if (userDAO.checkLoginUnique(login)) {
            return userDAO.create(createUserObject(null, login, password, name, surname, active, accessLevel));
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

    public synchronized boolean editUserWithUUID(UUID uuid, String login, String password, String name, String surname, Boolean active,
                                                 AccessLevel accessLevel) throws ItemNotFoundException {
        User user = userDAO.readOne(uuid);
        String updated_login = login;
        if (login == null) {
            updated_login = user.getLogin();
        }

        String updated_password = password;
        if (login == null) {
            updated_password = user.getPassword();
        }

        String updated_name = name;
        if (login == null) {
            updated_name = user.getName();
        }

        String updated_surname = surname;
        if (login == null) {
            updated_surname = user.getSurname();
        }

        Boolean updated_active = active;
        if (login == null) {
            updated_active = user.getActive();
        }

        AccessLevel updated_accessLevel = accessLevel;
        if (login == null) {
            updated_accessLevel = user.getUserAccessLevel();
        }

        return userDAO.update(createUserObject(uuid, updated_login, updated_password, updated_name, updated_surname, updated_active, updated_accessLevel));
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

    public User findUserByLoginPasswordActive(String login, String password) throws ItemNotFoundException {
        return userDAO.findUserByLoginPasswordActive(login, password);
    }

    public User findUserByLogin(String login) throws ItemNotFoundException {
        return userDAO.findUserByLogin(login);
    }

    public synchronized boolean changePassword(String login, String password) throws ItemNotFoundException {
        return userDAO.changePassword(login, password);
    }

}
