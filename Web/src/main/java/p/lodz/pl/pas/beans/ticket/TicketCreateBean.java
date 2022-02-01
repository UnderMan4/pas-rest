package p.lodz.pl.pas.beans.ticket;

import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.TicketDTO;
import p.lodz.pl.pas.services.TicketService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class TicketCreateBean implements Serializable {

    @Inject
    TicketService ticketService;

    private final TicketDTO newTicket = new TicketDTO();

    public TicketCreateBean() {
        newTicket.setJobEnd(null);
    }

    public TicketDTO getNewTicket() {
        return newTicket;
    }

    public String createNewTicket() {
        try {
            FacesMessage message = ticketService.createTicket(newTicket);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (RESTException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
        }
        return "ticket";
    }
}
