package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.TicketDTO;
import p.lodz.pl.pas.services.TicketService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class TicketCreateBean implements Serializable {

    @Inject
    TicketService ticketService;

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private final TicketDTO newTicket = new TicketDTO();

    public TicketCreateBean() {
    }

    public TicketDTO getNewTicket() {
        return newTicket;
    }

    public String createNewTicket() {
        if (newTicket.getUser() != null && newTicket.getJob() != null) {
            LOGGER.log(Level.INFO, newTicket.toString());
            Response response = ticketService.createTicket(newTicket);
            LOGGER.log(Level.INFO, response.toString());
        } else {
            throw new IllegalArgumentException("User or Job uuid is null");
        }
        return "ticket";
    }
}
