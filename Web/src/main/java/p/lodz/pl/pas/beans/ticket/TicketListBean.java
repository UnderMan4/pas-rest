package p.lodz.pl.pas.beans.ticket;

import p.lodz.pl.pas.model_web.Ticket;
import p.lodz.pl.pas.model_web.TicketDTO;
import p.lodz.pl.pas.services.TicketService;

import javax.annotation.PostConstruct;
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

    private String search = "";

    private final TicketDTO ticketDTO = new TicketDTO();

    private List<Ticket> ticketList;

    public TicketListBean() {
    }

    @PostConstruct
    public void init() {
        if (search == "") {
            ticketList = ticketService.getAllTickets();
        } else {
            ticketList = ticketService.search(search);
        }
    }

    // public List<Ticket> getTicketList() {
    //     return ticketService.getAllTickets();
    // }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
