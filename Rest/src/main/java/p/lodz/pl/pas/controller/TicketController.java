package p.lodz.pl.pas.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import p.lodz.pl.pas.RegexList;
import p.lodz.pl.pas.exceptions.DateException;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.cantDeleteException;
import p.lodz.pl.pas.manager.TicketManager;
import p.lodz.pl.pas.model.Ticket;

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
        LocalDateTime jobEnd;
        String description = jsonObject.get("description").getAsString();
        

        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try {
            jobStart = LocalDateTime.parse(jsonObject.get("jobStart").getAsString(), dtf);
        } catch (Exception e) { // catch all possible exceptions for parsing dates
            return Response.status(BAD_REQUEST).entity(
                    "jobStart is in wrong format: " + jsonObject.get("jobStart").getAsString()
            ).build();
        }
        try {
            jobEnd = LocalDateTime.parse(jsonObject.get("jobEnd").getAsString(), dtf);
        } catch (Exception e) { // catch all possible exceptions for parsing dates
            return Response.status(BAD_REQUEST).entity(
                    "jobEnd is in wrong format: " + jsonObject.get("jobEnd").getAsString()
            ).build();
        }

        if (!verifyDescription(description)) {
             return Response.status(Response.Status.BAD_REQUEST).entity("Description not valid").build();
         }

        try {
            if (ticketManager.createTicket(
                    user, 
                    job, 
                    jobStart, 
                    jobEnd, 
                    description
            )) {
                return Response.status(CREATED).build();
            } else {
                return Response.status(BAD_REQUEST).build();
            }
        } catch (DateException | ItemNotFoundException e) {
            return Response.status(BAD_REQUEST).build();
        }
    }

    @GET
    @Path("list")
    public Response getTicketList() {
        return Response.status(ACCEPTED).entity(
                getGsonSerializer().toJson(ticketManager.getTicketList())
        ).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Ticket findTicket(@QueryParam("UUID") UUID uuid) {
        try {
            return ticketManager.findByUUID(uuid);
        } catch (ItemNotFoundException e) {
            return null;
        }
    }

    @GET
    @Path("remove")
    public  Response deleteUser(@QueryParam("UUID") UUID uuid) {
        try {
            return Response.status(ACCEPTED).entity(ticketManager.delete(uuid)).build();
        } catch (cantDeleteException | ItemNotFoundException e) {
            return Response.status(NOT_MODIFIED).entity(e.getMessage()).build();
        }
    }
    


}