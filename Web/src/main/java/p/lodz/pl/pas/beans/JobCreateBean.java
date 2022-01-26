package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.JobDTO;
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
public class JobCreateBean implements Serializable {

    @Inject
    JobService jobService;

    @Inject
    JobListBean jobListBean;

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private final JobDTO newJob = new JobDTO();

    public JobCreateBean() {
    }

    public JobDTO getNewJob() {
        return newJob;
    }


    public String createNewJob() {
        if (newJob.getName() != null) {
            LOGGER.log(Level.INFO, newJob.toString());
            Response response = jobService.createJob(newJob);
            if (response.getStatus() != 202) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(response.readEntity(String.class)));
            }
            LOGGER.log(Level.INFO, response.toString());
        } else {
            throw new IllegalArgumentException("Name is null");
        }
        return "job";
    }
}
