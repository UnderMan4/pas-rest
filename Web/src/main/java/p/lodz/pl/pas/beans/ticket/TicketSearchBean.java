package p.lodz.pl.pas.beans.ticket;

import p.lodz.pl.pas.model_web.Ticket;
import p.lodz.pl.pas.services.TicketService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class TicketSearchBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Inject
    TicketService ticketService;

    private List<Ticket> ticketList;

    private String uuid;

    public TicketSearchBean() {
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void searchTicket() {
        if (uuid != null) {
            LOGGER.log(Level.INFO, "Searching for ticket with uuid " + uuid.toString());
            try {
                setTicketList(ticketService.searchTicket(uuid));
                LOGGER.log(Level.INFO, ticketList.toString());
            } catch (javax.ws.rs.NotFoundException e) {
                LOGGER.log(Level.INFO, e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
                setTicketList(null);
            }
            LOGGER.log(Level.INFO, ticketList.toString());
        } else {
            throw new IllegalArgumentException("Ticket uuid is empty");
        }
    }
}
