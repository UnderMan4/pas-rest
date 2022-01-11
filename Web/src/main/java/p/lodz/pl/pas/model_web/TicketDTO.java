package p.lodz.pl.pas.model_web;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.UUID;

public class TicketDTO {
    private User user;
    private Job job;
    private LocalDateTime jobStart;
    private LocalDateTime jobEnd;
    private String description;
    private TicketStatus status;

    public TicketDTO(User user, Job job, LocalDateTime jobStart, LocalDateTime jobEnd, String description) {
        this.user = user;
        this.job = job;
        this.jobStart = jobStart;
        this.jobEnd = jobEnd;
        this.description = description;
        this.status = TicketStatus.ToDo;
    }

    public TicketDTO(User user, Job job, LocalDateTime jobStart, LocalDateTime jobEnd) {
        this.user = user;
        this.job = job;
        this.jobStart = jobStart;
        this.jobEnd = jobEnd;
    }

    public TicketDTO() {
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

    @Override
    public String toString() {
        return "TicketDTO{" +
                "user=" + user +
                ", job=" + job +
                ", jobStart=" + jobStart +
                ", jobEnd=" + jobEnd +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    // ------------------------------------------------------------------------------------------------
}
