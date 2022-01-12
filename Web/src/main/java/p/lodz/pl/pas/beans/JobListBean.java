package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.services.JobService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class JobListBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Inject
    JobService jobService;

    @Inject
    JobEditBean jobEditBean;

    @Inject
    JobDetailsBean jobDetailsBean;

    private UIComponent deleteJobButton;

    public JobListBean() {
    }

    public List<Job> getJobList() {
        return jobService.getAllJobs();
    }

    public String editJob(Job job) {
        jobEditBean.setEditedJob(job);
        return "editJob";
    }

    public void deleteJob(Job job) {
        LOGGER.log(Level.INFO, "Job to remove " + job.toString());
        Response response = jobService.deleteJob(job);
        if (response.getStatus() == 406) {
            FacesMessage message = new FacesMessage(response.readEntity(String.class));
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(deleteJobButton.getClientId(context), message);
        }
        LOGGER.log(Level.INFO, response.toString());
    }

    public UIComponent getDeleteJobButton() {
        return deleteJobButton;
    }

    public void setDeleteJobButton(UIComponent deleteJobButton) {
        this.deleteJobButton = deleteJobButton;
    }

    public String detailsUser(Job job) {
        jobDetailsBean.setJob(job);
        jobDetailsBean.jobDetails();
        return "detailsJob";
    }
}
