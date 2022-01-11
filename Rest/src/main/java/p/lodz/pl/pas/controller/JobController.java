package p.lodz.pl.pas.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import p.lodz.pl.pas.RegexList;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.manager.JobManager;
import p.lodz.pl.pas.manager.TicketManager;
import p.lodz.pl.pas.model.Job;
import p.lodz.pl.pas.model.Ticket;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;


@Path("job")
public class JobController {
    @Inject
    JobManager jobManager;
    @Inject
    TicketManager ticketManager;

    public JobController() {
    }

    private boolean verifyName(String name) {
        return !RegexList.JOB_NAME.matcher(name).matches();
    }

    private boolean verifyDescription(String description) {
        return !RegexList.DESCRIPTION.matcher(description).matches();
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createJob(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String name;
        String description;
        try {
            name = jsonObject.get("name").getAsString();
            description = jsonObject.get("description").getAsString();
        } catch (NullPointerException nullPointerException) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid json name").build();
        }

        if (verifyName(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Job name not valid").build();
        } else if (!verifyDescription(description)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Description not valid").build();
        }
        UUID uuid = jobManager.createJob(jsonObject.get("name").getAsString(), jsonObject.get("description").getAsString());
        if (uuid == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } else {
            return Response.status(Response.Status.CREATED).entity(uuid).build();
        }
    }

    @GET
    @Path("list")
    public Response getList() {
        Gson gson = new Gson();
        return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(jobManager.getJobs())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Job findJob(@QueryParam("UUID") UUID uuid) {
        try {
            return jobManager.findJob(uuid);
        } catch (ItemNotFoundException e) {
            return null;
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateJob(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        UUID uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        if (verifyName(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Job name not valid").build();
        } else if (!verifyDescription(description)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Description not valid").build();
        }

        try {
            jobManager.updateJob(uuid, jsonObject.get("name").getAsString(), jsonObject.get("description").getAsString());
            return Response.status(Response.Status.CREATED).entity("Job updated").build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Job not found").build();
        }
    }

    @GET
    @Path("remove")
    public Response removeJob(@QueryParam("UUID") UUID uuid) {
        // search if job exists
        try {
            jobManager.findJob(uuid);
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Job not found").build();
        }

        try {
            Ticket t = ticketManager.searchByJobUUID(uuid);
            // return Response.serverError().status(Response.Status.NOT_ACCEPTABLE).type(MediaType.TEXT_PLAIN_TYPE).entity("Job is used in a ticket " + t.getUuid()).build();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Job is used in a ticket " + t.getUuid()).build();
        } catch (ItemNotFoundException e) {
            try {
                jobManager.removeJob(uuid);
                return Response.status(Response.Status.ACCEPTED).entity("Job removed successfully").build();
            } catch (ItemNotFoundException unexpectedException) {
                // if job is not found in the first search it should
                return Response.serverError().status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN_TYPE).entity("Job not found").build();
            }
        }
    }
}
