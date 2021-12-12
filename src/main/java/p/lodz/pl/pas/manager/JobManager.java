package p.lodz.pl.pas.manager;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.model.Job;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class JobManager {
    ArrayList<Job> jobs;

    public JobManager() {
        jobs = new ArrayList<>();
        this.createJob("Cleanup code", "Cleanup code in this program");
        this.createJob("Pass ISRP", "Pass ISRP and achive greatness");
        this.createJob("Find meaning in life", "Yes");
        this.createJob("Finish pas", "Finish this task and move on to another");
    }

    public synchronized boolean createJob(String name, String description) {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        }
        while (!checkIfUUIDExists(uuid));
        return jobs.add(new Job(uuid, name, description));
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public Job findJob(UUID uuid) throws ItemNotFoundException {
        Optional<Job> optional = jobs.stream().filter(j -> j.getUuid().equals(uuid)).findFirst();
        return optional.orElseThrow(() -> new ItemNotFoundException(
                "Job with UUID " + uuid + " not found"
        ));
    }

    public boolean checkIfUUIDExists(UUID uuid) {
        for ( Job j : jobs) {
            if (j.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public synchronized boolean editJob(UUID uuid, String name, String description) {
        if (!checkIfUUIDExists(uuid)) {
            return false;
        }
        Job j = null;
        try {
            j = findJob(uuid);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        j.setName(name);
        j.setDescription(description);
        return true;
    }

    public synchronized boolean removeJob(UUID uuid) {
        return jobs.removeIf(j -> j.getUuid().equals(uuid));
    }


}
