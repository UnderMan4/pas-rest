package p.lodz.pl.pas.model_web;

import java.util.UUID;

public class JobDTO {
    String name;
    String description;

    public JobDTO() {
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

    @Override
    public String toString() {
        return "JobDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
