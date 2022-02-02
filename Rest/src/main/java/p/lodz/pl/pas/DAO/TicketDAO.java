package p.lodz.pl.pas.DAO;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.cantDeleteException;
import p.lodz.pl.pas.model.*;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class TicketDAO implements DAO<Ticket> {
    ArrayList<Ticket> tickets;
    final DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public TicketDAO() {
        tickets = new ArrayList<>();
        this.create(new Ticket(UUID.fromString("86600b3f-2d48-4d88-a26b-45ec2eeb4845"),
                new NormalUser(UUID.fromString("54ceb043-5f89-41bb-a29b-f2c0e9909dad"), "jkowalski", "P@ssw0rd", "Jan", "Kowalski", true),
                new Job(UUID.fromString("b8344cdb-dc2d-42a0-8c0f-d35f676b8074"), "Cleanup code", "Cleanup code in this program"),
                LocalDateTime.parse("2021-12-09T08:00:00", dtf),
                LocalDateTime.parse("2021-12-13T16:00:00", dtf),
                "Cleanup code ticket description"
        ));

        this.create(new Ticket(UUID.fromString("229ef12a-13a9-4d0f-9ae7-239dfd4c17b9"),
                new NormalUser(UUID.fromString("84d267cf-6dc4-40cd-b1d3-000733a85458"), "ttttt", "P@ssw0rd", "Tomasz", "Kowalski", true),
                new Job(UUID.fromString("858908da-ae32-4527-bdcb-13a91b0a49b9"), "Pass ISRP", "Pass ISRP and achive greatness"),
                LocalDateTime.parse("2021-08-01T08:00:00", dtf),
                null,
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
        if (object.getUuid() == null) {
            object.setUuid(UUID.randomUUID());
        }
        return tickets.add(object);
    }

    @Override
    public boolean delete(UUID uuid) throws ItemNotFoundException, cantDeleteException {
        Ticket toDelete = readOne(uuid);
        if (toDelete.getJobEnd().isAfter(LocalDateTime.now())) {
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

    public ArrayList<Ticket> searchByJobUUID(String uuid) throws ItemNotFoundException {
        List<Ticket> list = tickets.stream().filter(t -> t.getJob().getUuid().toString().contains(uuid)).collect(Collectors.toList());
        if (list.isEmpty()) {
            throw new ItemNotFoundException(
                    "Tickets for job with UUID " + uuid + " not found"
            );
        }
        return new ArrayList<Ticket>(list);
    }

    public ArrayList<Ticket> searchByUserUUID(String uuid) throws ItemNotFoundException {
        List<Ticket> list = tickets.stream().filter(t -> t.getUser().getUuid().toString().contains(uuid)).collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new ItemNotFoundException(
                    "Tickets for user with UUID " + uuid + " not found"
            );
        }
        return new ArrayList<Ticket>(list);
    }

    @Override
    public ArrayList<Ticket> searchByUUID(String uuid) throws ItemNotFoundException {
        List<Ticket> list = tickets.stream().filter(t -> t.getUuid().toString().contains(uuid)).collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new ItemNotFoundException(
                    "Tickets for user with UUID " + uuid + " not found"
            );
        }
        return new ArrayList<Ticket>(list);
    }

    // return ticket that has the job in specified time
    public Ticket tryToGetJob(UUID jobUUID, LocalDateTime jobStart) {
        Optional<Ticket> optional = tickets.stream()
                // get tickets with job uuid
                .filter(t -> t.getJob().getUuid().equals(jobUUID))
                // get jobs that ARE NOT FINISHED
                .filter(t -> t.getJobEnd() == null)
                // search for jobs that started before our job, and are not yet finished
                .filter(t -> t.getJobStart().isBefore(jobStart))
                .findFirst();
        if (optional.isEmpty()) {
            optional = tickets.stream()
                    // get tickets with job uuid
                    .filter(t -> t.getJob().getUuid().equals(jobUUID))
                    // get jobs that ARE FINISHED
                    .filter(t -> t.getJobEnd() != null)
                    // check if they are finished before creating this
                    .filter(t -> t.getJobEnd().isAfter(jobStart))
                    .findFirst();

            // if second condition is empty then we return null
            return optional.orElse(null);
        } else {
            // there is a job that ends after we want to start our job
            return optional.get();
        }
    }
}
