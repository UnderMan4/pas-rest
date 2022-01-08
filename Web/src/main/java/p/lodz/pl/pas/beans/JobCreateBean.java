package p.lodz.pl.pas.beans;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import p.lodz.pl.pas.model_web.JobDTO;
import p.lodz.pl.pas.services.Const;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class JobCreateBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(JobCreateBean.class.getName());

    private JobDTO newJob = new JobDTO();

    /**
     * Creates a new instance of JobBean
     */
    public JobCreateBean() {
    }

    public JobDTO getNewJob() {
        return newJob;
    }


    public void createNewJob() {
        LOGGER.log(Level.INFO, newJob.toString());
        if (newJob.getName() != null) {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(Const.MAIN_URL);
            Response response = target.path("api").path("job").path("create").request()
                    .post(Entity.json(new Gson().toJson(newJob)));
            LOGGER.log(Level.INFO, response.toString());
        } else {
            throw new IllegalArgumentException("Name is null");
        }

    }
}
