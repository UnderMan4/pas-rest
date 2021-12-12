package p.lodz.pl.pas.model;

import java.util.Date;
import java.util.UUID;

import static p.lodz.pl.pas.model.TicketStatus.ToDo;

public class Ticket {
    UUID uuid;
    User user;
    Job job;
    Date jobStart;
    Date jobEnd;
    String description;
    TicketStatus status;

    public Ticket(UUID uuid, User user, Job job, Date jobStart, Date jobEnd, String description) {
        this.uuid = uuid;
        this.user = user;
        this.job = job;
        this.jobStart = jobStart;
        this.jobEnd = jobEnd;
        this.description = description;
        this.status = ToDo;
    }

    public Ticket(UUID uuid, User user, Job job, Date jobStart, Date jobEnd) {
        this.uuid = uuid;
        this.user = user;
        this.job = job;
        this.jobStart = jobStart;
        this.jobEnd = jobEnd;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Date getJobStart() {
        return jobStart;
    }

    public void setJobStart(Date jobStart) {
        this.jobStart = jobStart;
    }

    public Date getJobEnd() {
        return jobEnd;
    }

    public void setJobEnd(Date jobEnd) {
        this.jobEnd = jobEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    // ------------------------------------------------------------------------------------------------
}
