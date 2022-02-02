package p.lodz.pl.pas.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

import static p.lodz.pl.pas.RegexList.JOB_NAME_PATTERN;


public class Job implements SingableEntity{
    private UUID uuid;

    @Pattern(regexp = JOB_NAME_PATTERN, message = "Name must be between 2 and 20 characters")
    private String name;

    @Size(max = 400, message = "Max description length is 400")
    private String description;

    public Job(UUID uuid, String name, String description) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
    }

    public Job(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ------------------------------------------------------------------------------------------------


    @Override
    public String getSingablePayload() {
        return getUuid().toString();
    }
}
