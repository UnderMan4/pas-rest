package p.lodz.pl.pas.beans.job;

import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.services.JobService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
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
    private UIComponent deleteJobButtonConfirm;
    private UIComponent deleteJobButtonCancel;

    private boolean showDeleteConfirmation = false;
    private String jobToDeleteUUID = null;

    private String search = "";

    private List<Job> jobList;
    private List<Job> jobListAll;

    public JobListBean() {
    }

    @PostConstruct
    public void init() {
        jobListAll = jobService.getAllJobs();
        if (search == "") {
            jobList = jobService.getAllJobs();
        } else {
            jobList = jobService.search(search);
        }
        LOGGER.log(Level.INFO, jobList.toString());
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

    public void showDeleteConfirmationButtons() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params =
                fc.getExternalContext().getRequestParameterMap();
        LOGGER.log(Level.INFO, params.toString());
        setJobToDeleteUUID(params.get("jobUUID"));
        setShowDeleteConfirmation(true);

    }

    public void hideDeleteConfirmationButtons() {
        setShowDeleteConfirmation(false);
    }

    public void deleteJob() {
        // FacesContext fc = FacesContext.getCurrentInstance();
        // Map<String, String> params =
        //         fc.getExternalContext().getRequestParameterMap();
        // String uuid = params.get("jobUUID");

        try {
            jobService.deleteJob(jobToDeleteUUID);
        } catch (RESTException e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(deleteJobButton.getClientId(context), message);
        }
        init();
        hideDeleteConfirmationButtons();
    }

    public UIComponent getDeleteJobButton() {
        return deleteJobButton;
    }

    public UIComponent getDeleteJobButtonConfirm() {
        return deleteJobButtonConfirm;
    }

    public UIComponent getDeleteJobButtonCancel() {
        return deleteJobButtonCancel;
    }

    public void setDeleteJobButton(UIComponent deleteJobButton) {
        this.deleteJobButton = deleteJobButton;
    }

    public void setDeleteJobButtonConfirm(UIComponent deleteJobButtonConfirm) {
        this.deleteJobButtonConfirm = deleteJobButtonConfirm;
    }

    public void setDeleteJobButtonCancel(UIComponent deleteJobButtonCancel) {
        this.deleteJobButtonCancel = deleteJobButtonCancel;
    }

    public boolean isShowDeleteConfirmation() {
        return showDeleteConfirmation;
    }

    public void setShowDeleteConfirmation(boolean showDeleteConfirmation) {
        this.showDeleteConfirmation = showDeleteConfirmation;
    }

    public String getJobToDeleteUUID() {
        return jobToDeleteUUID;
    }

    public void setJobToDeleteUUID(String jobToDeleteUUID) {
        this.jobToDeleteUUID = jobToDeleteUUID;
    }

    public List<Job> getJobListAll() {
        return jobListAll;
    }

    public void setJobListAll(List<Job> jobListAll) {
        this.jobListAll = jobListAll;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String detailsUser(Job job) {
        jobDetailsBean.setJob(job);
        jobDetailsBean.jobDetails();
        return "detailsJob";
    }
}
