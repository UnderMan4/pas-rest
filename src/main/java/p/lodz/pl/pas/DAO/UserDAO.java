package p.lodz.pl.pas.DAO;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.model.AccessLevel;
import p.lodz.pl.pas.model.User;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class UserDAO implements DAO<User> {
    ArrayList<User> users;

    public UserDAO() {
        users = new ArrayList<>();
        this.create(new User(UUID.randomUUID(), "jkowalski", "Jan", "Kowalski", true, AccessLevel.User));
        this.create(new User(UUID.randomUUID(), "jjjkowal", "Jaroslaw", "Kowalski", true, AccessLevel.ResourceAdministrator));
        this.create(new User(UUID.randomUUID(), "ttttt", "Tomasz", "Kowalski", true, AccessLevel.User));
        this.create(new User(UUID.randomUUID(), "Restitutor", "Lucius", "Aurelianus", true, AccessLevel.UserAdministrator));
    }

    @Override
    public ArrayList<User> readAll() {
        return users;
    }

    @Override
    public User readOne(UUID uuid) throws ItemNotFoundException {
        Optional<User> optional = users.stream().filter(u -> u.getUuid().equals(uuid)).findFirst();
        return optional.orElseThrow(() -> new ItemNotFoundException(
                "User with UUID " + uuid + " not found"
        ));
    }

    @Override
    public boolean create(User object) {
        return users.add(object);
    }

    @Override
    public boolean delete(UUID uuid) throws ItemNotFoundException {
        return users.remove(readOne(uuid));
    }

    @Override
    public boolean update(User object) throws ItemNotFoundException {
        User u = readOne(object.getUuid());
        u.setName(object.getName());
        u.setLogin(object.getLogin());
        u.setSurname(object.getSurname());
        u.setActive(object.getActive());
        u.setAccessLevel(object.getAccessLevel());
        return true;
    }

    public boolean checkLoginUnique(String login) {
        return users.stream().parallel().noneMatch(u -> u.getLogin().equals(login));
    }

    public boolean setUserActive(UUID uuid, boolean active) throws ItemNotFoundException {
        User u = readOne(uuid);
        u.setActive(active);
        return true;
    }

    public User findUser(String login) throws ItemNotFoundException {
        Optional<User> optional = users.stream().filter(u -> u.getLogin().equals(login)).findFirst();
        return optional.orElseThrow(() -> new ItemNotFoundException(
                "User with login " + login + " not found"
        ));
    }

    public ArrayList<User> findUsers(String login) throws ItemNotFoundException {
        Optional<User> optional = users.stream().parallel().filter(u -> u.getLogin().contains(login)).findAny();
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("User with matching login " + login + " not found");
        }
        // return (ArrayList<User>) optional.stream().toList();                    // Java 16
        //return (ArrayList<User>) optional.stream().collect(Collectors.toList()); // Java 9
        ArrayList<User> result = new ArrayList<>();
        optional.ifPresent(result::add);
        return result;
    }
}
