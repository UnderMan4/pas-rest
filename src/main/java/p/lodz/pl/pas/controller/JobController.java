package p.lodz.pl.pas.controller;

import p.lodz.pl.pas.model.Job;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class JobController {
    ArrayList<Job> jobList;

    public JobController() {
        jobList = new ArrayList<>();
        this.createJob("Cleanup code", "Cleanup code in this program");
        this.createJob("Pass ISRP", "Pass ISRP and achive greatness");
        this.createJob("Find meaning in life", "Yes");
        this.createJob("Finish pas", "Finish this task and move on to another");
    }

    public boolean createJob(String name, String description) {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        }
        while (!checkIfUUIDExists(uuid));
        return jobList.add(new Job(uuid, name, description));
    }

    public ArrayList<Job> getJobList() {
        return jobList;
    }

    public Job findJob(UUID uuid) {
        Optional<Job> optional = jobList.stream().filter(j -> j.getUuid().equals(uuid)).findFirst();
        return optional.orElse(null);
    }

    public boolean checkIfUUIDExists(UUID uuid) {
        for ( Job j : jobList ) {
            if (j.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public boolean editJob(UUID uuid, String name, String description) {
        if (!checkIfUUIDExists(uuid)) {
            return false;
        }
        Job j = findJob(uuid);
        j.setName(name);
        j.setDescription(description);
        return true;
    }

    public boolean removeJob(UUID uuid) {
        return jobList.removeIf(j -> j.getUuid().equals(uuid));
    }


}
