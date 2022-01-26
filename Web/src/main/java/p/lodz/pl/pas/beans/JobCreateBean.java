package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.JobDTO;
import p.lodz.pl.pas.services.JobService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
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
        try {
            FacesMessage message = jobService.createJob(newJob);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (RESTException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
        }
        return "job";
    }
}
