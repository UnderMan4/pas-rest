package p.lodz.pl.pas.services;


import com.google.gson.Gson;
import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.model_web.JobDTO;

import javax.faces.application.FacesMessage;
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
import java.util.logging.Logger;

public class JobService implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private final Job newJob = new Job();

    public JobService() {
    }

    public Job getNewJob() {
        return newJob;
    }

    private WebTarget getClientWebTarget() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Const.MAIN_URL);
        return target.path("api").path("job");

    }

    public FacesMessage createJob(JobDTO newJob) throws RESTException {
        LOGGER.log(Level.INFO, "Creating new job " + newJob.toString());
        Response response = getClientWebTarget().path("create").request()
                .post(Entity.json(new Gson().toJson(newJob)));
        LOGGER.log(Level.INFO, response.toString());
        if (response.getStatus() != 202) {
            return new FacesMessage(response.readEntity(String.class));
        } else {
            throw new RESTException(response.readEntity(String.class));
        }
    }

    public List<Job> getAllJobs() {
        return getClientWebTarget().path("list").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Job>>() {
        });
    }

    public FacesMessage saveEditedJob(Job editedJob) throws RESTException {
        Response response = getClientWebTarget().path("update").request()
                .post(Entity.json(new Gson().toJson(editedJob)));
        LOGGER.log(Level.INFO, editedJob.toString());
        LOGGER.log(Level.INFO, response.toString());
        if (response.getStatus() != 202) {
            return new FacesMessage(response.readEntity(String.class));
        } else {
            throw new RESTException(response.readEntity(String.class));
        }
    }

    public void deleteJob(String uuid) throws RESTException {
        LOGGER.log(Level.INFO, "Job to remove " + uuid.toString());
        Response response = getClientWebTarget().path("remove").queryParam("UUID", uuid).request().get();
        LOGGER.log(Level.INFO, response.toString());
        if (response.getStatus() == 406) {
            throw new RESTException(response.readEntity(String.class));
        }
    }

    public List<Job> searchByUUID(String uuid) {
        return getClientWebTarget().path("searchByUUID").queryParam("UUID", uuid).request(MediaType.APPLICATION_JSON).get(new GenericType<List<Job>>() {
        });
    }
}
