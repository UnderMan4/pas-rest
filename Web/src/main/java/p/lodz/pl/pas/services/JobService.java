package p.lodz.pl.pas.services;


import com.google.gson.Gson;
import p.lodz.pl.pas.model_web.Job;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

public class JobService implements Serializable {
    private Job newJob = new Job();

    public JobService() {
    }

    public Job getNewJob(){
        return newJob;
    }

    public List<Job> getAllJobs() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("job").path("list").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Job>>() {
        });
    }

    public Response saveEditedJob(Job editedJob) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("job").path("update").request()
                .post(Entity.json(new Gson().toJson(editedJob)));
    }


}
