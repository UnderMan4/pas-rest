package p.lodz.pl.pas.beans.ticket;

import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.model_web.Ticket;
import p.lodz.pl.pas.model_web.TicketDTO;
import p.lodz.pl.pas.model_web.User;
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

    private User user;
    private Job job;

    public TicketCreateBean() {
        newTicket.setJobEnd(null);
    }

    public TicketDTO getNewTicket() {
        return newTicket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String createNewTicket() {
        try {
            newTicket.setJob(job.getUuid());
            newTicket.setUser(user.getUuid());
            FacesMessage message = ticketService.createTicket(newTicket);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (RESTException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
        }
        return "ticket";
    }
}
