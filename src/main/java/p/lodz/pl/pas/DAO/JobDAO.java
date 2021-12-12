package p.lodz.pl.pas.DAO;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.model.Job;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class JobDAO implements DAO<Job> {
    ArrayList<Job> jobs;

    public JobDAO() {
        jobs = new ArrayList<>();
        this.create(new Job(UUID.randomUUID(), "Cleanup code", "Cleanup code in this program"));
        this.create(new Job(UUID.randomUUID(), "Pass ISRP", "Pass ISRP and achive greatness"));
        this.create(new Job(UUID.randomUUID(), "Find meaning in life", "Yes"));
        this.create(new Job(UUID.randomUUID(), "Finish pas", "Finish this task and move on to another"));
    }

    @Override
    public ArrayList<Job> readAll() {
        return jobs;
    }

    @Override
    public Job readOne(UUID uuid) throws ItemNotFoundException {
        Optional<Job> optional = jobs.stream().parallel().filter(j -> j.getUuid().equals(uuid)).findFirst();
        return optional.orElseThrow(() -> new ItemNotFoundException(
                "Job with UUID " + uuid + " not found"
        ));
    }

    @Override
    public boolean create(Job object) {
        return jobs.add(object);
    }

    @Override
    public boolean delete(UUID uuid) throws ItemNotFoundException {
        return jobs.remove(readOne(uuid));
    }

    @Override
    public boolean update(Job object) throws ItemNotFoundException {
        Job j = readOne(object.getUuid());
        j.setName(object.getName());
        j.setDescription(object.getDescription());
        return true;
    }
}
