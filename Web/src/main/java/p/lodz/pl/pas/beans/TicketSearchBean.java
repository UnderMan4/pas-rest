package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.Ticket;
import p.lodz.pl.pas.model_web.TicketDTO;
import p.lodz.pl.pas.services.TicketService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class TicketSearchBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(JobCreateBean.class.getName());

    @Inject
    TicketService ticketService;

    private Ticket ticket;

    private UUID uuid;

    public TicketSearchBean() {
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void searchTicket() {
        if (uuid != null) {
            LOGGER.log(Level.INFO, "Searching for ticket with uuid " + uuid.toString());
            setTicket(ticketService.searchTicket(uuid));
            LOGGER.log(Level.INFO, ticket.toString());
        } else {
            throw new IllegalArgumentException("Ticket uuid is empty");
        }
    }
}
