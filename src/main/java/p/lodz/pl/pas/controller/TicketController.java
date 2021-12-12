package p.lodz.pl.pas.controller;

import p.lodz.pl.pas.model.Job;
import p.lodz.pl.pas.model.Ticket;
import p.lodz.pl.pas.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


public class TicketController {
    ArrayList<Ticket> ticketArrayList;

    public TicketController() {
        this.ticketArrayList = new ArrayList<>();
    }

    public synchronized boolean createTicket(User user, Job job, Date jobStart, Date jobEnd, String description) {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (!checkIfUUIDExists(uuid));
        return ticketArrayList.add(new Ticket(
                uuid,
                user.getUuid(),
                job.getUuid(),
                jobStart,
                jobEnd,
                description
        ));
    }


    public boolean checkIfUUIDExists(UUID uuid) {
        for (Ticket u : ticketArrayList) {
            if (u.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }
}
