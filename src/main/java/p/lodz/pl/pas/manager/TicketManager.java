package p.lodz.pl.pas.manager;

import p.lodz.pl.pas.DAO.JobDAO;
import p.lodz.pl.pas.DAO.TicketDAO;
import p.lodz.pl.pas.DAO.UserDAO;
import p.lodz.pl.pas.exceptions.DateException;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.model.Job;
import p.lodz.pl.pas.model.Ticket;
import p.lodz.pl.pas.model.User;

import javax.inject.Inject;
import java.util.Date;
import java.util.UUID;


public class TicketManager {
    @Inject
    TicketDAO ticketDAO;
    
    @Inject
    UserDAO userDAO;
    
    @Inject
    JobDAO jobDAO;
    

    public synchronized boolean createTicket(UUID userUUID, UUID jobUUID, Date jobStart, Date jobEnd, String description) throws DateException, ItemNotFoundException {
        if (jobStart.compareTo(jobEnd) >= 0) {
            throw new DateException("Job Start must be before Job End");
        }
        User user = userDAO.readOne(userUUID);
        Job job = jobDAO.readOne(jobUUID);
        return ticketDAO.create(new Ticket(UUID.randomUUID(), user, job, jobStart, jobEnd, description));
    }

    public Ticket findByUUID(UUID uuid) throws ItemNotFoundException {
        return findByUUID(uuid);
    }
}
