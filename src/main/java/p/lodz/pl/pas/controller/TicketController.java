package p.lodz.pl.pas.controller;

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
import java.util.Date;

public class TicketController {
    @Inject
    TicketManager ticketManager;

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTicket(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String user = jsonObject.get("user").getAsString();
        String job = jsonObject.get("job").getAsString();
        String jobStart = jsonObject.get("jobStart").getAsString();
        String jobEnd = jsonObject.get("jobEnd").getAsString();
        String description = jsonObject.get("description").getAsString();

        if(!verifyUser(user)){
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        } else if (!verifyJob(job)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        } else if (!verifyJobStart(jobStart)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        } else if (!verifyJobEnd(jobEnd)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        } else if (!verifyDescription(description)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Description not valid").build();
        }

        if (ticketManager.createTicket(jsonObject.get("user").getAsString(), jsonObject.get("job").getAsString(), jsonObject.get("jobStart").getAsString(), jsonObject.get("jobEnd").getAsString(), jsonObject.get("description").getAsString())) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private boolean verifyJobEnd(String jobEnd) {
        return RegexList.todo.matcher(jobEnd).matches();
    }

    private boolean verifyJobStart(String jobStart) {
        return RegexList.todo.matcher(jobStart).matches();
    }

    private boolean verifyJob(String job) {
        return RegexList.todo.matcher(job).matches();
    }

    private boolean verifyUser(String user) {
        return RegexList.todo.matcher(user).matches();
    }

    private boolean verifyDescription(String description) {
        return RegexList.todo.matcher(description).matches();
    }


}
