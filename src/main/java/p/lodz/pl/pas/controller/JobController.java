package p.lodz.pl.pas.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import p.lodz.pl.pas.RegexList;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.manager.JobManager;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;


@Path("job")
public class JobController {
    @Inject
    JobManager jobManager;

    public JobController() {
    }

    private boolean verifyName(String name) {
        return RegexList.JOB_NAME.matcher(name).matches();
    }

    private boolean verifyDescription(String description) {
        return RegexList.JOB_DESCRIPTION.matcher(description).matches();
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createJob(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        if (!verifyName(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Job name not valid").build();
        } else if (!verifyDescription(description)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Description not valid").build();
        }

        if (jobManager.createJob(jsonObject.get("name").getAsString(), jsonObject.get("description").getAsString())) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("list")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getList() {
        Gson gson = new Gson();
        return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(jobManager.getJobs())).build();
    }

    @GET
    public Response findJob(@QueryParam("UUID") UUID uuid) {
        Gson gson = new Gson();
        try {
            return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(jobManager.findJob(uuid))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateJob(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        UUID uuid = UUID.fromString(jsonObject.get("UUID").getAsString());
        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        if (!verifyName(name)) {
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeJob(UUID uuid) {

        try {
            jobManager.removeJob(uuid);
            return Response.status(Response.Status.CREATED).entity("Job removed").build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Job not found").build();
        }
    }


}
