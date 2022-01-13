package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.services.JobService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class JobEditBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private Job editedJob;

    @Inject
    JobService jobService;

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
            Response response = jobService.saveEditedJob(editedJob);
            if (response.getStatus() != 202) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(response.readEntity(String.class)));
            }
            LOGGER.log(Level.INFO, response.toString());
        } else {
            throw new IllegalArgumentException("Edited Job is null");
        }
        return "job";
    }
}
