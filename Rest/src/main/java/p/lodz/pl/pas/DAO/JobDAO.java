package p.lodz.pl.pas.DAO;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.model.Job;
import p.lodz.pl.pas.model.Ticket;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class JobDAO implements DAO<Job> {
    ArrayList<Job> jobs;

    public JobDAO() {
        jobs = new ArrayList<>();
        this.create(new Job(UUID.fromString("b8344cdb-dc2d-42a0-8c0f-d35f676b8074"), "Cleanup code", "Cleanup code in this program"));
        this.create(new Job(UUID.fromString("858908da-ae32-4527-bdcb-13a91b0a49b9"), "Pass ISRP", "Pass ISRP and achive greatness"));
        this.create(new Job(UUID.fromString("20edf313-9c1c-43e8-ae39-e1703d3928f8"), "Find meaning in life", "Yes"));
        this.create(new Job(UUID.fromString("d749db34-3f68-47e1-9e73-fe22d421b70a"), "Finish pas", "Finish this task and move on to another"));
    }

    @Override
    public ArrayList<Job> readAll() {
        return jobs;
    }

    @Override
    public Job readOne(UUID uuid) throws ItemNotFoundException {
        Optional<Job> optional = jobs.stream().filter(j -> j.getUuid().equals(uuid)).findFirst();
        return optional.orElseThrow(() -> new ItemNotFoundException(
                "Job with UUID " + uuid + " not found"
        ));
    }

    @Override
    public boolean create(Job object) {
        if (object.getUuid() == null) {
            object.setUuid(UUID.randomUUID());
        }
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

    @Override
    public ArrayList<Job> searchByUUID(String uuid) throws ItemNotFoundException {
        List<Job> list = jobs.stream().filter(j -> j.getUuid().toString().contains(uuid)).collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new ItemNotFoundException(
                    "Job with " + uuid + " not found"
            );
        }
        return new ArrayList<Job>(list);
    }

    @Override
    public ArrayList<Job> search(String s) throws ItemNotFoundException {
        Predicate<Job> name = job -> job.getName().toLowerCase().contains(s.toLowerCase());
        Predicate<Job> description = job -> job.getDescription().toLowerCase().contains(s.toLowerCase());
        Predicate<Job> uuid = job -> job.getUuid().toString().toLowerCase().contains(s.toLowerCase());

        Predicate<Job> filter = name.or(description).or(uuid);

        List<Job> result = jobs.stream().filter(filter).collect(Collectors.toList());

        // if (result.isEmpty()) {
        //     throw new ItemNotFoundException(
        //             "Job with not found"
        //     );
        // }
        return new ArrayList<Job>(result);
    }
}
