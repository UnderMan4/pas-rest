package p.lodz.pl.pas.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import p.lodz.pl.pas.RegexList;
import p.lodz.pl.pas.exceptions.DateException;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.JobAlreadyTaken;
import p.lodz.pl.pas.exceptions.cantDeleteException;
import p.lodz.pl.pas.manager.TicketManager;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.*;
import static p.lodz.pl.pas.conversion.GsonLocalDateTime.getGsonSerializer;


@Path("ticket")
public class TicketController {
    @Inject
    TicketManager ticketManager;

    private boolean verifyDescription(String description) {
        return !RegexList.DESCRIPTION.matcher(description).matches();
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTicket(String json){
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        UUID user;
        UUID job;
        try {
            user = UUID.fromString(jsonObject.get("user").getAsString());
            job = UUID.fromString(jsonObject.get("job").getAsString());
        } catch (NullPointerException n) {
            return Response.status(BAD_REQUEST).entity(
                    "Wrong json format"
            ).build();
        }

        LocalDateTime jobStart;
        String description = jsonObject.get("description").getAsString();
        

        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try {
            jobStart = LocalDateTime.parse(jsonObject.get("jobStart").getAsString(), dtf);
        } catch (Exception e) { // catch all possible exceptions for parsing dates
            return Response.status(BAD_REQUEST).entity(
                    "jobStart is in wrong format: " + jsonObject.get("jobStart").getAsString()
            ).build();
        }

        if (!verifyDescription(description)) {
             return Response.status(Response.Status.BAD_REQUEST).entity("Description not valid").build();
         }

        try {
            ticketManager.createTicket(user, job, jobStart, null, description);
            return Response.status(CREATED).entity("Ticket created").build();
        } catch (DateException e) {
            // if user inputs wrong date in correct format, ex. day that does not exists
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch (ItemNotFoundException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        } catch (JobAlreadyTaken e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTicketList() {
        return Response.status(ACCEPTED).entity(
                getGsonSerializer().toJson(ticketManager.getTicketList())
        ).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTicket(@QueryParam("UUID") UUID uuid) {
        try {
            return Response.status(ACCEPTED).entity(ticketManager.findByUUID(uuid)).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("remove")
    public Response deleteTicket(@QueryParam("UUID") UUID uuid) {
        try {
            return Response.status(ACCEPTED).entity(ticketManager.delete(uuid)).build();
        } catch (cantDeleteException e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (ItemNotFoundException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("getUserTickets")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserTickets(@QueryParam("UUID") String uuid) {
        try {
            return Response.status(ACCEPTED).entity(ticketManager.searchByUserUUID(uuid)).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("getJobTickets")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobTickets(@QueryParam("UUID") String uuid) {
        try {
            return Response.status(ACCEPTED).entity(ticketManager.searchByJobUUID(uuid)).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("searchByUUID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByUUID(@QueryParam("UUID") String uuid) {
        try {
            return Response.status(ACCEPTED).entity(ticketManager.searchByUUID(uuid)).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
