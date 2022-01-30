package p.lodz.pl.pas.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.manager.JobManager;
import p.lodz.pl.pas.manager.TicketManager;
import p.lodz.pl.pas.model.Ticket;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.UUID;


@Path("job")
@RolesAllowed({"Admin", "ResourceAdministrator"})
public class JobController {
    @Inject
    JobManager jobManager;
    @Inject
    TicketManager ticketManager;

    public JobController() {
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createJob(@NotNull String json) {
        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            String description = jsonObject.get("description").getAsString();
            UUID uuid = jobManager.createJob(name, description);
            if (uuid == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            } else {
                return Response.status(Response.Status.CREATED).entity(uuid).build();
            }
        } catch (JsonSyntaxException | NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid json name").build();
        }
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList() {
        Gson gson = new Gson();
        return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(jobManager.getJobs())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findJob(@QueryParam("UUID") @NotNull UUID uuid) {
        try {
            Gson gson = new Gson();
            return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(jobManager.findJob(uuid))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateJob(@NotNull String json) {
        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            UUID uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
            String name = jsonObject.get("name").getAsString();
            String description = jsonObject.get("description").getAsString();
            jobManager.updateJob(uuid, name, description);
            return Response.status(Response.Status.ACCEPTED).entity("Job updated").build();
        } catch (JsonSyntaxException | NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid json name").build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("remove")
    public Response removeJob(@QueryParam("UUID") @NotNull UUID uuid) {
        // search if job exists
        try {
            jobManager.findJob(uuid);
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        // search if job is used in a ticket
        try {
            ArrayList<Ticket> t = ticketManager.searchByJobUUID(uuid.toString());
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Job is used in a ticket " + t.get(0).getUuid()).build();
        } catch (ItemNotFoundException e) {
            // if job isn't in any tickets
            try {
                jobManager.removeJob(uuid);
                return Response.status(Response.Status.ACCEPTED).entity("Job removed successfully").build();
            } catch (ItemNotFoundException unexpectedException) {
                // JUST IN CASE
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(unexpectedException.getMessage()).build();
            }
        }
    }

    @GET
    @Path("searchByUUID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByUUID(@QueryParam("UUID") @NotNull String uuid) {
        try {
            Gson gson = new Gson();
            return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(jobManager.searchByUUID(uuid))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
