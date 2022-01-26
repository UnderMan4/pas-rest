package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.Ticket;
import p.lodz.pl.pas.model_web.TicketDTO;
import p.lodz.pl.pas.services.TicketService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class TicketListBean implements Serializable {
    @Inject
    TicketService ticketService;

    private final TicketDTO ticketDTO = new TicketDTO();

    public TicketListBean() {
    }

    public List<Ticket> getTicketList() {
        return ticketService.getAllTickets();
    }
}
