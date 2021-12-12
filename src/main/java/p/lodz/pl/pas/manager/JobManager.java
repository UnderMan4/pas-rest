package p.lodz.pl.pas.manager;

import p.lodz.pl.pas.DAO.JobDAO;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.model.Job;

import java.util.ArrayList;
import java.util.UUID;

public class JobManager {
    JobDAO jobDAO;


    public JobManager() {
        jobDAO = new JobDAO();
    }

    public synchronized boolean createJob(String name, String description) {
        return jobDAO.create(new Job(UUID.randomUUID(), name, description));
    }

    public ArrayList<Job> getJobs() {
        return jobDAO.readAll();
    }

    public Job findJob(UUID uuid) throws ItemNotFoundException {
        return jobDAO.readOne(uuid);
    }


    public synchronized boolean updateJob(UUID uuid, String name, String description) throws ItemNotFoundException {
        return jobDAO.update(new Job(uuid, name, description));
    }

    public synchronized boolean removeJob(UUID uuid) throws ItemNotFoundException {
        return jobDAO.delete(uuid);
    }


}
