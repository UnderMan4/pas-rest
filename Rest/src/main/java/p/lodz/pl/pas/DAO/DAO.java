package p.lodz.pl.pas.DAO;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.cantDeleteException;

import java.util.List;
import java.util.UUID;

public interface DAO<T> {
    List<T> readAll();

    T readOne(UUID uuid) throws ItemNotFoundException;

    boolean create(T object);

    boolean delete(UUID uuid) throws ItemNotFoundException, cantDeleteException;

    boolean update(T object) throws ItemNotFoundException;

    // return if T.uuid contains String UUID
    List<T> searchByUUID(String uuid) throws ItemNotFoundException;

    List<T> search(String s) throws ItemNotFoundException;
}
