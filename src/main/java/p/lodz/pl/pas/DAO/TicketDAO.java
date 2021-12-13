package p.lodz.pl.pas.DAO;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.cantDeleteException;
import p.lodz.pl.pas.model.AccessLevel;
import p.lodz.pl.pas.model.Job;
import p.lodz.pl.pas.model.Ticket;
import p.lodz.pl.pas.model.User;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TicketDAO implements DAO<Ticket> {
    ArrayList<Ticket> tickets;

    public TicketDAO() {
        tickets = new ArrayList<>();
        JobDAO jobDAO = new JobDAO();
        this.create(new Ticket(UUID.fromString("86600b3f-2d48-4d88-a26b-45ec2eeb4845"),
                new User(UUID.fromString("54ceb043-5f89-41bb-a29b-f2c0e9909dad"), "jkowalski", "Jan", "Kowalski", true, AccessLevel.User),
                new Job(UUID.fromString("b8344cdb-dc2d-42a0-8c0f-d35f676b8074"), "Cleanup code", "Cleanup code in this program"),
                Date.valueOf("2021-12-09"),
                Date.valueOf("2021-12-13"),
                "Cleanup code ticket description"
                ));

        this.create(new Ticket(UUID.fromString("229ef12a-13a9-4d0f-9ae7-239dfd4c17b9"),
                new User(UUID.fromString("84d267cf-6dc4-40cd-b1d3-000733a85458"), "ttttt", "Tomasz", "Kowalski", true, AccessLevel.User),
                new Job(UUID.fromString("858908da-ae32-4527-bdcb-13a91b0a49b9"), "Pass ISRP", "Pass ISRP and achive greatness"),
                Date.valueOf("2021-10-01"),
                Date.valueOf("2021-11-20"),
                "Pass isrp and enjoy life"
        ));
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
