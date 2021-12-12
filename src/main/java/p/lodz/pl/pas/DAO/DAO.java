package p.lodz.pl.pas.DAO;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;

import java.util.List;
import java.util.UUID;

public interface DAO<T> {
    List<T> readAll();

    T readOne(UUID uuid) throws ItemNotFoundException;

    boolean create(T object);

    boolean delete(UUID uuid) throws ItemNotFoundException;

    boolean update(T object) throws ItemNotFoundException;
}
