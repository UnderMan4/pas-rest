package p.lodz.pl.pas.DAO;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.cantDeleteException;
import p.lodz.pl.pas.model.Ticket;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TicketDAO implements DAO<Ticket> {
    ArrayList<Ticket> tickets;

    public TicketDAO() {
        tickets = new ArrayList<>();
    }

    @Override
    public ArrayList<Ticket> readAll() {
        return tickets;
    }

    @Override
    public Ticket readOne(UUID uuid) throws ItemNotFoundException {
        Optional<Ticket> optional = tickets.stream().parallel().filter(j -> j.getUuid().equals(uuid)).findFirst();
        return optional.orElseThrow(() -> new ItemNotFoundException(
                "Ticket with UUID " + uuid + " not found"
        ));
    }

    @Override
    public boolean create(Ticket object) {
        return tickets.add(object);
    }

    @Override
    public boolean delete(UUID uuid) throws ItemNotFoundException, cantDeleteException {
        Ticket toDelete = readOne(uuid);
        if (toDelete.getJobEnd().toInstant().isAfter(Instant.from(LocalDateTime.now()))) {
            return tickets.remove(readOne(uuid));
        } else {
            throw new cantDeleteException("Can't delete ticket because it has already ended");
        }
    }

    @Override
    public boolean update(Ticket object) throws ItemNotFoundException {
        Ticket t = readOne(object.getUuid());
        t.setJobStart(object.getJobStart());
        t.setJobEnd(object.getJobEnd());
        t.setDescription(object.getDescription());
        t.setStatus(object.getStatus());
        return true;
    }

}
