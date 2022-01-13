package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.model_web.Ticket;
import p.lodz.pl.pas.services.JobService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class JobSearchBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Inject
    JobService jobService;

    private List<Job> jobList;

    private String uuid;

    public JobSearchBean() {
    }

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void searchJob() {
        if (uuid != null) {
            LOGGER.log(Level.INFO, "Searching for job with uuid " + uuid.toString());
            try {
                setJobList(jobService.searchByUUID(uuid));
                LOGGER.log(Level.INFO, jobList.toString());
            } catch (javax.ws.rs.NotFoundException e) {
                LOGGER.log(Level.INFO, e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
                setJobList(null);
            }
            LOGGER.log(Level.INFO, jobList.toString());
        } else {
            throw new IllegalArgumentException("Job uuid is empty");
        }
    }
}
