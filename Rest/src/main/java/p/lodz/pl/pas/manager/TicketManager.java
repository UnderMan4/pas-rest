package p.lodz.pl.pas.manager;

import p.lodz.pl.pas.DAO.JobDAO;
import p.lodz.pl.pas.DAO.TicketDAO;
import p.lodz.pl.pas.DAO.UserDAO;
import p.lodz.pl.pas.exceptions.DateException;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.JobAlreadyTaken;
import p.lodz.pl.pas.exceptions.cantDeleteException;
import p.lodz.pl.pas.model.Job;
import p.lodz.pl.pas.model.Ticket;
import p.lodz.pl.pas.model.User;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


public class TicketManager {
    @Inject
    TicketDAO ticketDAO;

    @Inject
    UserDAO userDAO;

    @Inject
    JobDAO jobDAO;


    public synchronized boolean createTicket(UUID userUUID, UUID jobUUID, LocalDateTime jobStart, LocalDateTime jobEnd, String description) throws DateException, ItemNotFoundException, JobAlreadyTaken {
        if (jobEnd != null) {
            if (jobStart.isBefore(jobEnd)) {
                throw new DateException("Job Start must be before Job End");
            }
        }
        Ticket t = ticketDAO.tryToGetJob(jobUUID, jobStart);
        // if list is not empty, we cant
        if (t != null) {
            throw new JobAlreadyTaken("Job is already taken by ticket " + t.getUuid());
        }
        User user = userDAO.readOne(userUUID);
        Job job = jobDAO.readOne(jobUUID);
        return ticketDAO.create(new Ticket(user, job, jobStart, jobEnd, description));
    }

    public Ticket findByUUID(UUID uuid) throws ItemNotFoundException {
        return ticketDAO.readOne(uuid);
    }

    public ArrayList<Ticket> getTicketList() {
        return ticketDAO.readAll();
    }

    public boolean delete(UUID uuid) throws ItemNotFoundException, cantDeleteException {
        return ticketDAO.delete(uuid);
    }

    public ArrayList<Ticket> searchByJobUUID(String jobUUID) throws ItemNotFoundException {
        return ticketDAO.searchByJobUUID(jobUUID);
    }

    public ArrayList<Ticket> searchByUserUUID(String userUUID) throws ItemNotFoundException {
        return ticketDAO.searchByUserUUID(userUUID);
    }

    // search by UUID and return all matching results
    public ArrayList<Ticket> searchByUUID(String userUUID) throws ItemNotFoundException {
        return ticketDAO.searchByUUID(userUUID);
    }

    public ArrayList<Ticket> search(String s) throws ItemNotFoundException {
        return ticketDAO.search(s);
    }

    public synchronized boolean endJob(UUID uuid, LocalDateTime jobEnd) throws ItemNotFoundException{
        return ticketDAO.endJob(uuid, jobEnd);
    }
}
