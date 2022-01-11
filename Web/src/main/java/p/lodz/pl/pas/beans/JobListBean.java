package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.services.JobService;

import javax.enterprise.context.SessionScoped;
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

    private static final Logger LOGGER = Logger.getLogger(JobCreateBean.class.getName());

    @Inject
    JobService jobService;

    @Inject
    JobEditBean jobEditBean;

    /**
     * Creates a new instance of JobBean
     */
    public JobListBean() {
    }

    public List<Job> getJobList() {
        return jobService.getAllJobs();
    }

    public String goBack() {
        return "/start";
    }

    public String editJob(Job job) {
        jobEditBean.setEditedJob(job);
        return "editJob";
    }

    public void deleteJob(Job job) {
        LOGGER.log(Level.INFO, "Job to remove " + job.toString());
        Response response = jobService.deleteJob(job);
        LOGGER.log(Level.INFO, response.toString());
    }

   
}
