package p.lodz.pl.pas.services;

import p.lodz.pl.pas.conversion.GsonLocalDateTime;
import p.lodz.pl.pas.model_web.Ticket;
import p.lodz.pl.pas.model_web.TicketDTO;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TicketService implements Serializable {
    private final Ticket newTicket = new Ticket();

    private GsonLocalDateTime gsonLocalDateTime;

    public TicketService() {

    }

    public Ticket getNewTicket() {
        return newTicket;
    }

    private WebTarget getTicketWebTarget(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("ticket");
    }

    public List<Ticket> getAllTickets() {
        return getTicketWebTarget().path("list").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Ticket>>() {
        });
    }

    public Response createTicket(TicketDTO editTicket) {
        return getTicketWebTarget().path("create").request()
                .post(Entity.json(gsonLocalDateTime.getGsonSerializer().toJson(editTicket)));
    }

    public List<Ticket> searchTicket(String uuid) {
        return getTicketWebTarget().path("searchByUUID").queryParam("UUID", uuid).request().get(new GenericType<List<Ticket>>() {
        });
    }
}
