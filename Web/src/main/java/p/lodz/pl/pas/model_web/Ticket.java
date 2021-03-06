package p.lodz.pl.pas.model_web;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private UUID uuid;
    private User user;
    private Job job;
    private LocalDateTime jobStart;
    private LocalDateTime jobEnd;
    private String description;
    private TicketStatus status;

    public Ticket() {
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

    @Override
    public String toString() {
        return "Ticket{" +
                "uuid=" + uuid +
                ", user=" + user +
                ", job=" + job +
                ", jobStart=" + jobStart +
                ", jobEnd=" + jobEnd +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    // ------------------------------------------------------------------------------------------------
}
