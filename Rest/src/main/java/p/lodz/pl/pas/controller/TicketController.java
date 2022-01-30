package p.lodz.pl.pas.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import p.lodz.pl.pas.exceptions.DateException;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.JobAlreadyTaken;
import p.lodz.pl.pas.exceptions.cantDeleteException;
import p.lodz.pl.pas.manager.TicketManager;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.*;
import static p.lodz.pl.pas.conversion.GsonLocalDateTime.getGsonSerializer;


@Path("ticket")
@RolesAllowed({"Admin", "ResourceAdministrator"})
public class TicketController {
    @Inject
    TicketManager ticketManager;

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTicket(String json) {
        // TODO CLEANUP
        JsonObject jsonObject;
        UUID user;
        UUID job;
        String description;
        try {
            jsonObject = JsonParser.parseString(json).getAsJsonObject();
            description = jsonObject.get("description").getAsString();
            user = UUID.fromString(jsonObject.get("user").getAsString());
            job = UUID.fromString(jsonObject.get("job").getAsString());
        } catch (JsonSyntaxException | NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid json name").build();
        }

        LocalDateTime jobStart;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            jobStart = LocalDateTime.parse(jsonObject.get("jobStart").getAsString(), dtf);
        } catch (Exception e) { // catch all possible exceptions for parsing dates
            return Response.status(BAD_REQUEST).entity("jobStart is in wrong format").build();
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
    public Response findTicket(@QueryParam("UUID") @NotNull UUID uuid) {
        try {
            return Response.status(ACCEPTED).entity(ticketManager.findByUUID(uuid)).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("remove")
    public Response deleteTicket(@QueryParam("UUID") @NotNull UUID uuid) {
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
    public Response getUserTickets(@QueryParam("UUID") @NotNull String uuid) {
        try {
            return Response.status(ACCEPTED).entity(ticketManager.searchByUserUUID(uuid)).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("getJobTickets")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobTickets(@QueryParam("UUID") @NotNull String uuid) {
        try {
            return Response.status(ACCEPTED).entity(ticketManager.searchByJobUUID(uuid)).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("searchByUUID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByUUID(@QueryParam("UUID") @NotNull String uuid) {
        try {
            return Response.status(ACCEPTED).entity(ticketManager.searchByUUID(uuid)).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
