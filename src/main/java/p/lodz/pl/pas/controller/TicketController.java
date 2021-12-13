package p.lodz.pl.pas.controller;

import p.lodz.pl.pas.exceptions.DateException;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.manager.TicketManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import p.lodz.pl.pas.RegexList;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Path("ticket")
public class TicketController {
    @Inject
    TicketManager ticketManager;

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTicket(String json){
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        UUID user = UUID.fromString(jsonObject.get("user").getAsString());
        UUID job = UUID.fromString(jsonObject.get("job").getAsString());
        Date jobStart;
        Date jobEnd;
        String description = jsonObject.get("description").getAsString();
        
        
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            jobStart = format.parse(jsonObject.get("jobStart").getAsString());
        } catch (ParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    "jobStart is in wrong format: " + jsonObject.get("jobStart").getAsString()
            ).build();
        }
        try {
            jobEnd = format.parse(jsonObject.get("jobEnd").getAsString());
        } catch (ParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    "jobEnd is in wrong format: " + jsonObject.get("jobEnd").getAsString()
            ).build();
        }


        // if(!verifyUser(user)){
        //     return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        // } else if (!verifyJob(job)) {
        //     return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        // } else if (!verifyJobStart(jobStart)) {
        //     return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        // } else if (!verifyJobEnd(jobEnd)) {
        //     return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        // } else if (!verifyDescription(description)) {
        //     return Response.status(Response.Status.BAD_REQUEST).entity("Description not valid").build();
        // }

        try {
            if (ticketManager.createTicket(user, job, jobStart, jobEnd, description)) {
                return Response.status(Response.Status.CREATED).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (DateException | ItemNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }



}
