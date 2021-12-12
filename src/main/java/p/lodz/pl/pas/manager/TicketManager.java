package p.lodz.pl.pas.manager;

import p.lodz.pl.pas.DAO.TicketDAO;
import p.lodz.pl.pas.exceptions.DateException;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.model.Job;
import p.lodz.pl.pas.model.Ticket;
import p.lodz.pl.pas.model.User;

import java.util.Date;
import java.util.UUID;


public class TicketManager {
    TicketDAO ticketDAO;

    public TicketManager() {
        ticketDAO = new TicketDAO();
    }

    public synchronized boolean createTicket(User user, Job job, Date jobStart, Date jobEnd, String description) throws DateException, ItemNotFoundException {
        if (jobStart.compareTo(jobEnd) >= 0) {
            throw new DateException("Job Start must be before Job End");
        }
        if (user == null) {
            throw new ItemNotFoundException("User not found");
        } else if (job == null) {
            throw new ItemNotFoundException("Job not found");
        }
        return ticketDAO.create(new Ticket(UUID.randomUUID(), user, job, jobStart, jobEnd, description));
    }

    public Ticket findByUUID(UUID uuid) throws ItemNotFoundException {
        return findByUUID(uuid);
    }
}
