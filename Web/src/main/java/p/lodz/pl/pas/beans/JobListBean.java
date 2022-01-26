package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.services.JobService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

    private List<Job> jobList;

    public JobListBean() {
    }

    @PostConstruct
    public void init() {
        jobList = jobService.getAllJobs();
    }

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    public String editJob(Job job) {
        jobEditBean.setEditedJob(job);
        return "editJob";
    }

    public void deleteJob() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params =
                fc.getExternalContext().getRequestParameterMap();
        String uuid =  params.get("jobUUID");

        try {
            jobService.deleteJob(uuid);
        } catch (RESTException e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(deleteJobButton.getClientId(context), message);
        }
        init();
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
