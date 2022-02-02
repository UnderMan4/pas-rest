package p.lodz.pl.pas.beans.job;

import p.lodz.pl.pas.exceptions.RESTException;
import p.lodz.pl.pas.model_web.Job;
import p.lodz.pl.pas.services.JobService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class JobEditBean implements Serializable {

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
        try {
            FacesMessage message = jobService.saveEditedJob(editedJob);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (RESTException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
        }
        return "job";
    }
}
