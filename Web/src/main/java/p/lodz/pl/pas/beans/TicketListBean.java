package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.Ticket;
import p.lodz.pl.pas.model_web.TicketDTO;
import p.lodz.pl.pas.services.TicketService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
@Named
public class TicketListBean implements Serializable {
    @Inject
    TicketService ticketService;

    private static final Logger LOGGER = Logger.getLogger(JobCreateBean.class.getName());


    private TicketDTO ticketDTO = new TicketDTO();

    public TicketListBean() {
    }

    public List<Ticket> getTicketList() {
        return ticketService.getAllTickets();
    }

    public void createNewTicket() {
        if (ticketDTO.getJob() != null && ticketDTO.getUser() != null) {
            LOGGER.log(Level.INFO, ticketDTO.toString());
            Response response = ticketService.createTicket(ticketDTO);
            LOGGER.log(Level.INFO, response.toString());
        } else {
            throw new IllegalArgumentException("Job or user is null");
        }
    }
}
