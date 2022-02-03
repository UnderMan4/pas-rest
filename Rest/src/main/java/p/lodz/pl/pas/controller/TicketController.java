package p.lodz.pl.pas.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import p.lodz.pl.pas.conversion.GsonLocalDateTime;
import p.lodz.pl.pas.exceptions.DateException;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.JobAlreadyTaken;
import p.lodz.pl.pas.exceptions.cantDeleteException;
import p.lodz.pl.pas.filter.SignatureValidatorFilter;
import p.lodz.pl.pas.filter.SignatureVerifier;
import p.lodz.pl.pas.manager.TicketManager;
import p.lodz.pl.pas.manager.UserManager;
import p.lodz.pl.pas.model.Ticket;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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

    @Inject
    UserManager userManager;

    @POST
    @Path("createAdvanced")
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
            // if user inputs wrong date in correct format, ex. day that does not exist
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch (ItemNotFoundException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        } catch (JobAlreadyTaken e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("list")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTicketList() {
        return Response.status(ACCEPTED).entity(
                getGsonSerializer().toJson(ticketManager.getTicketList())
        ).build();
    }

    /**
     * Returns with etag for ticket editing
     *
     * @param uuid ticket uuid
     * @return one ticket with exact uuid
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTicket(@QueryParam("UUID") @NotNull UUID uuid) {
        try {
            Ticket ticket = ticketManager.findByUUID(uuid);
            EntityTag tag = new EntityTag(SignatureVerifier.calculateEntitySignature(ticket));
            return Response.status(ACCEPTED).entity(getGsonSerializer().toJson(ticket)).tag(tag).build();
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
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByUUID(@QueryParam("UUID") @NotNull String uuid) {
        try {
            return Response.status(ACCEPTED).entity(ticketManager.searchByUUID(uuid)).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @SignatureValidatorFilter
    @Path("editTicketWithUUID")
    public Response editWithUUID(String json, @HeaderParam("If-match") @NotNull @NotEmpty String tagValue) {
        return Response.status(NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("search")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("s") String s) {
        try {
            return Response.status(Response.Status.ACCEPTED).entity(GsonLocalDateTime.getGsonSerializer().toJson(ticketManager.search(s))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }


    @POST
    @Path("createTicket")
    @RolesAllowed({"Admin", "ResourceAdministrator", "NormalUser"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePassword(@Context SecurityContext securityContext, @NotNull @NotEmpty String json) {
        JsonObject jsonObject;
        UUID user;
        UUID job;
        String description;
        try {
            jsonObject = JsonParser.parseString(json).getAsJsonObject();
            description = jsonObject.get("description").getAsString();
            user = userManager.findUserByLogin(securityContext.getUserPrincipal().getName()).getUuid();
            job = UUID.fromString(jsonObject.get("job").getAsString());
        } catch (JsonSyntaxException | NullPointerException | ItemNotFoundException e) {
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
            // if user inputs wrong date in correct format, ex. day that does not exist
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch (ItemNotFoundException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        } catch (JobAlreadyTaken e) {
            return Response.status(NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("endTicket")
    public Response search(@QueryParam("UUID") @NotNull UUID uuid, @NotNull @NotEmpty String endDate) {
        try {
            LocalDateTime jobEnd = getGsonSerializer().fromJson(endDate, LocalDateTime.class);
            return Response.status(Response.Status.ACCEPTED).entity(GsonLocalDateTime.getGsonSerializer().toJson(ticketManager.endJob(uuid, jobEnd))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
