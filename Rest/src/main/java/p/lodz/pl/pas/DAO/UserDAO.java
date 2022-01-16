package p.lodz.pl.pas.DAO;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.model.*;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserDAO implements DAO<User> {
    ArrayList<User> users;

    public UserDAO() {
        users = new ArrayList<>();
        this.create(new NormalUser(UUID.fromString("54ceb043-5f89-41bb-a29b-f2c0e9909dad"), "jkowalski", "Jan", "Kowalski", true));
        this.create(new ResourceAdministrator(UUID.fromString("40d68ba5-39ba-4f2e-9e61-a55daf7b3e8e"), "jjjkowal", "Jaroslaw", "Kowalski", true));
        this.create(new UserAdministrator(UUID.fromString("84d267cf-6dc4-40cd-b1d3-000733a85458"), "ttttt", "Tomasz", "Kowalski", true));
        this.create(new Admin(UUID.fromString("295eea09-5541-42e4-ac24-126a0d87607e"), "Restitutor", "Lucius", "Aurelianus", true));
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
        if (object.getUuid() == null) {
            object.setUuid(UUID.randomUUID());
        }
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

    // find users with matching login
    @Override
    public ArrayList<User> searchByUUID(String uuid) throws ItemNotFoundException {
        List<User> list = users.stream().filter(u -> u.getUuid().toString().contains(uuid)).collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new ItemNotFoundException(
                    "Users with login " + uuid + " not found"
            );
        }
        return new ArrayList<User>(list);
    }

    // find users with matching login
    public ArrayList<User> findUsersByLogin(String login) throws ItemNotFoundException {
        List<User> list = users.stream().filter(u -> u.getLogin().contains(login)).collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new ItemNotFoundException(
                    "Users with login " + login + " not found"
            );
        }
        return new ArrayList<User>(list);
    }
}
