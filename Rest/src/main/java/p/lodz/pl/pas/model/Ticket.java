package p.lodz.pl.pas.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

import static p.lodz.pl.pas.model.TicketStatus.ToDo;

public class Ticket implements SingableEntity{
    private UUID uuid;

    @NotNull
    private User user;
    @NotNull
    private Job job;

    private LocalDateTime jobStart;
    private LocalDateTime jobEnd;

    @Size(max = 400, message = "Max description length is 400")
    private String description;
    private TicketStatus status;

    public Ticket(UUID uuid, User user, Job job, LocalDateTime jobStart, LocalDateTime jobEnd, String description) {
        this.uuid = uuid;
        this.user = user;
        this.job = job;
        this.jobStart = jobStart;
        this.jobEnd = jobEnd;
        this.description = description;
        this.status = ToDo;
    }

    public Ticket(UUID uuid, User user, Job job, LocalDateTime jobStart, LocalDateTime jobEnd) {
        this.uuid = uuid;
        this.user = user;
        this.job = job;
        this.jobStart = jobStart;
        this.jobEnd = jobEnd;
    }

    public Ticket(User user, Job job, LocalDateTime jobStart, LocalDateTime jobEnd, String description) {
        this.user = user;
        this.job = job;
        this.jobStart = jobStart;
        this.jobEnd = jobEnd;
        this.description = description;
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


    @Override
    public String getSingablePayload() {
        return getUuid().toString() + getJob().getUuid().toString() + getUser().getUuid().toString();
    }
}
