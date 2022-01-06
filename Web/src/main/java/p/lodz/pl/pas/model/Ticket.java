package p.lodz.pl.pas.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    UUID uuid;
    User user;
    Job job;
    LocalDateTime jobStart;
    LocalDateTime jobEnd;
    String description;
    TicketStatus status;

    public Ticket(UUID uuid, User user, Job job, LocalDateTime jobStart, LocalDateTime jobEnd, String description) {
        this.uuid = uuid;
        this.user = user;
        this.job = job;
        this.jobStart = jobStart;
        this.jobEnd = jobEnd;
        this.description = description;
        this.status = TicketStatus.ToDo;
    }

    public Ticket(UUID uuid, User user, Job job, LocalDateTime jobStart, LocalDateTime jobEnd) {
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

    public LocalDateTime getJobStart() {
        return jobStart;
    }

    public void setJobStart(LocalDateTime jobStart) {
        this.jobStart = jobStart;
    }

    public LocalDateTime getJobEnd() {
        return jobEnd;
    }

    public void setJobEnd(LocalDateTime jobEnd) {
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
