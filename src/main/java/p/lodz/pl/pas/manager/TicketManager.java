package p.lodz.pl.pas.manager;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.model.Job;
import p.lodz.pl.pas.model.Ticket;
import p.lodz.pl.pas.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;


public class TicketManager {
    ArrayList<Ticket> tickets;

    public TicketManager() {
        this.tickets = new ArrayList<>();
    }

    public synchronized boolean createTicket(User user, Job job, Date jobStart, Date jobEnd, String description) {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (!checkIfUUIDExists(uuid));
        return tickets.add(new Ticket(
                uuid,
                user.getUuid(),
                job.getUuid(),
                jobStart,
                jobEnd,
                description
        ));
    }


    private boolean checkIfUUIDExists(UUID uuid) {
        for (Ticket u : tickets) {
            if (u.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }
    
    public Ticket findByUUID(UUID uuid) throws ItemNotFoundException {
        Optional<Ticket> optional = tickets.stream().filter(j -> j.getUuid().equals(uuid)).findFirst();
        return optional.orElseThrow(() -> new ItemNotFoundException(
                "Ticke with UUID " + uuid.toString() + " not found"
        ));
    }
}
