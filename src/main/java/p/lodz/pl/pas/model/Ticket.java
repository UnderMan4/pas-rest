package p.lodz.pl.pas.model;

import java.util.Date;
import java.util.UUID;

public class Ticket {
    UUID uuid;
    UUID clientUUID;
    UUID jobUUID;
    Date jobStart;
    Date jobEnd;
    String description;
    String status;

    public Ticket(UUID uuid, UUID clientUUID, UUID jobUUID, Date jobStart, Date jobEnd, String description, String status) {
        this.uuid = uuid;
        this.clientUUID = clientUUID;
        this.jobUUID = jobUUID;
        this.jobStart = jobStart;
        this.jobEnd = jobEnd;
        this.description = description;
        this.status = status;
    }

    public Ticket(UUID uuid, UUID clientUUID, UUID jobUUID, Date jobStart, Date jobEnd) {
        this.uuid = uuid;
        this.clientUUID = clientUUID;
        this.jobUUID = jobUUID;
        this.jobStart = jobStart;
        this.jobEnd = jobEnd;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getClientUUID() {
        return clientUUID;
    }

    public void setClientUUID(UUID clientUUID) {
        this.clientUUID = clientUUID;
    }

    public UUID getJobUUID() {
        return jobUUID;
    }

    public void setJobUUID(UUID jobUUID) {
        this.jobUUID = jobUUID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ------------------------------------------------------------------------------------------------
}
