package p.lodz.pl.pas.services;

import p.lodz.pl.pas.conversion.GsonLocalDateTime;
import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.Ticket;
import p.lodz.pl.pas.model_web.TicketDTO;

import javax.faces.application.FacesMessage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketService implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

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

    public FacesMessage createTicket(TicketDTO newTicket) throws RESTException {
        LOGGER.log(Level.INFO, newTicket.toString());
        if (newTicket.getUser() != null && newTicket.getJob() != null) {
            Response response = getTicketWebTarget().path("create").request()
                    .post(Entity.json(gsonLocalDateTime.getGsonSerializer().toJson(newTicket)));
            LOGGER.log(Level.INFO, response.toString());
            if (response.getStatus() != 202) {
                return new FacesMessage(response.readEntity(String.class));
            } else {
                throw new RESTException(response.readEntity(String.class));
            }
        } else {
            throw new IllegalArgumentException("User or Job uuid is null");
        }
    }

    public List<Ticket> searchTicket(String uuid) {
        return getTicketWebTarget().path("searchByUUID").queryParam("UUID", uuid).request().get(new GenericType<List<Ticket>>() {
        });
    }

    public List<Ticket> getUserTicketList(String uuid) {
        return getTicketWebTarget().path("getUserTickets").queryParam("UUID", uuid).request().get(new GenericType<List<Ticket>>() {
        });
    }

    public List<Ticket> getJobTicketList(String uuid) {
        return getTicketWebTarget().path("getJobTickets").queryParam("UUID", uuid).request().get(new GenericType<List<Ticket>>() {
        });
    }
}
