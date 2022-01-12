package p.lodz.pl.pas.services;


import com.google.gson.Gson;
import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.model_web.JobDTO;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

public class JobService implements Serializable {
    private final Job newJob = new Job();

    public JobService() {
    }

    public Job getNewJob(){
        return newJob;
    }

    private WebTarget getClientWebTarget() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("job");

    }

    public Response createJob(JobDTO newJob) {
        return getClientWebTarget().path("create").request()
                .post(Entity.json(new Gson().toJson(newJob)));
    }

    public List<Job> getAllJobs() {
        return getClientWebTarget().path("list").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Job>>() {
        });
    }

    public Response saveEditedJob(Job editedJob) {
        return getClientWebTarget().path("update").request()
                .post(Entity.json(new Gson().toJson(editedJob)));
    }

    public Response deleteJob(Job job) {
        return getClientWebTarget().path("remove").queryParam("UUID", job.getUuid()).request().get();
    }

    public List<Job> searchByUUID(String uuid) {
        return getClientWebTarget().path("searchByUUID").queryParam("UUID", uuid).request(MediaType.APPLICATION_JSON).get(new GenericType<List<Job>>() {
        });
    }
}
