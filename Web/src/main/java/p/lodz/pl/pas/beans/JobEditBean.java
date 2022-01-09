package p.lodz.pl.pas.beans;

import com.google.gson.Gson;
import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.services.Const;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class JobEditBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(JobCreateBean.class.getName());

    private Job editedJob;

    /**
     * Creates a new instance of JobBean
     */
    public JobEditBean() {
    }

    public Job getEditedJob() {
        return editedJob;
    }

    public void setEditedJob(Job editedJob) {
        this.editedJob = editedJob;
    }

    public String saveEditedJob() {
        LOGGER.log(Level.INFO, editedJob.toString());
        if (editedJob != null) {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(Const.MAIN_URL);
            Response response = target.path("api").path("job").path("update").request()
                    .post(Entity.json(new Gson().toJson(editedJob)));
            LOGGER.log(Level.INFO, response.toString());
        } else {
            throw new IllegalArgumentException("Edited Job is null");
        }
        return "job";

    }
}
