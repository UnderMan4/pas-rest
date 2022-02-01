package p.lodz.pl.pas.beans.user;

import p.lodz.pl.pas.model_web.Ticket;
import p.lodz.pl.pas.model_web.User;
import p.lodz.pl.pas.services.TicketService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserDetailsBean implements Serializable {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());
    private User user;

    @Inject
    TicketService ticketService;

    List<Ticket> ticketList;

    public UserDetailsBean() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void userDetails() {
        if (user != null) {
            LOGGER.log(Level.INFO, "Getting details for " + user.getLogin());
            try {
                setTicketList(ticketService.getUserTicketList(user.getUuid().toString()));
                LOGGER.log(Level.INFO, ticketList.toString());
            } catch (javax.ws.rs.NotFoundException e) {
                LOGGER.log(Level.INFO, "No tickets found for the user");
                setTicketList(null);
            }
        } else {
            throw new IllegalArgumentException("Edited user is null");
        }
    }
}
