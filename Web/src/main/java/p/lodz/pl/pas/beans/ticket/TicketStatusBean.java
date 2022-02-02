package p.lodz.pl.pas.beans.ticket;

import p.lodz.pl.pas.model_web.TicketStatus;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class TicketStatusBean implements Serializable {

    private TicketStatus ticketStatus;


    public TicketStatusBean() {
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public TicketStatus[] getTicketStatusList() {
        return TicketStatus.values();
    }
}
