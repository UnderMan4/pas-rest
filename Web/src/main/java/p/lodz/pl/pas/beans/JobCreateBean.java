package p.lodz.pl.pas.beans;

import p.lodz.pl.pas.model_web.JobDTO;
import p.lodz.pl.pas.services.JobService;

import javax.enterprise.context.SessionScoped;
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
        if (newJob.getName() != null) {
            LOGGER.log(Level.INFO, newJob.toString());
            Response response = jobService.createJob(newJob);
            LOGGER.log(Level.INFO, response.toString());
        } else {
            throw new IllegalArgumentException("Name is null");
        }
    }
}
