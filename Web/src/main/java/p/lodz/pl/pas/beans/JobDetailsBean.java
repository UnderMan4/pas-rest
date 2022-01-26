package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.Job;
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
public class JobDetailsBean implements Serializable {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());
    private Job job;

    @Inject
    TicketService ticketService;

    List<Ticket> ticketList;

    public JobDetailsBean() {

    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void jobDetails() {
        if (job != null) {
            LOGGER.log(Level.INFO, "Getting details for " + job.getUuid());
            try {
                setTicketList(ticketService.getJobTicketList(job.getUuid().toString()));
                LOGGER.log(Level.INFO, ticketList.toString());
            } catch (javax.ws.rs.NotFoundException e) {
                LOGGER.log(Level.INFO, e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.toString()));
                setTicketList(null);
            }
        } else {
            throw new IllegalArgumentException("Edited user is null");
        }
    }
}
