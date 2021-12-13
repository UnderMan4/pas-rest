package p.lodz.pl.pas.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p.lodz.pl.pas.model.Job;
import p.lodz.pl.pas.model.Ticket;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class TicketControllerTest {

    ClientBuilder clientBuilder;
    Client client;
    WebTarget target;
    String created_uuid;

    @BeforeEach
    void setUp() {
        clientBuilder = ClientBuilder.newBuilder();
        client = clientBuilder.build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createSlashRemoveTicket() {
        WebTarget target = client.target("http://localhost:8080/api/ticket");
        String json = """
                {
                    "uuid": "86600b3f-2d48-4d88-a26b-45ec2eeb4845",
                    "user": {
                      "login": "jkowalski",
                      "name": "Jan",
                      "surname": "Kowalski",
                      "uuid": "54ceb043-5f89-41bb-a29b-f2c0e9909dad",
                      "active": true,
                      "accessLevel": "User"
                    },
                    "job": {
                      "uuid": "b8344cdb-dc2d-42a0-8c0f-d35f676b8074",
                      "name": "Cleanup code",
                      "description": "Cleanup code in this program"
                    },
                    "jobStart": "gru 9, 2021",
                    "jobEnd": "gru 13, 2021",
                    "description": "Cleanup code ticket description",
                    "status": "ToDo"
                  }
                """;
    }

    @Test
    void getTicketList() {
        String json = """
                [
                  {
                    "uuid": "86600b3f-2d48-4d88-a26b-45ec2eeb4845",
                    "user": {
                      "login": "jkowalski",
                      "name": "Jan",
                      "surname": "Kowalski",
                      "uuid": "54ceb043-5f89-41bb-a29b-f2c0e9909dad",
                      "active": true,
                      "accessLevel": "User"
                    },
                    "job": {
                      "uuid": "b8344cdb-dc2d-42a0-8c0f-d35f676b8074",
                      "name": "Cleanup code",
                      "description": "Cleanup code in this program"
                    },
                    "jobStart": "gru 9, 2021",
                    "jobEnd": "gru 13, 2021",
                    "description": "Cleanup code ticket description",
                    "status": "ToDo"
                  },
                  {
                    "uuid": "229ef12a-13a9-4d0f-9ae7-239dfd4c17b9",
                    "user": {
                      "login": "ttttt",
                      "name": "Tomasz",
                      "surname": "Kowalski",
                      "uuid": "84d267cf-6dc4-40cd-b1d3-000733a85458",
                      "active": true,
                      "accessLevel": "User"
                    },
                    "job": {
                      "uuid": "858908da-ae32-4527-bdcb-13a91b0a49b9",
                      "name": "Pass ISRP",
                      "description": "Pass ISRP and achive greatness"
                    },
                    "jobStart": "lis 1, 2021",
                    "jobEnd": "lis 20, 2021",
                    "description": "Pass isrp and enjoy life",
                    "status": "ToDo"
                  }
                ]
                """;
        WebTarget target = client.target("http://localhost:8080/api/ticket");
        Response response = target.path("list").request(MediaType.APPLICATION_JSON_TYPE).get();
        assertNotNull(response);
        assertEquals(202, response.getStatus());
        assertEquals(response.readEntity(String.class), json);

    }

    @Test
    void findTicket() {
        String uuid = "229ef12a-13a9-4d0f-9ae7-239dfd4c17b9";
        WebTarget target = client.target("http://localhost:8080/api/ticket");
        String s = target.queryParam("UUID", uuid).request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        Gson gson = new Gson();
        Ticket j = gson.fromJson(s, Ticket.class);
        assertEquals(j.getJobStart(), "lis 1, 2021");
        assertEquals(j.getJobEnd(), "lis 20, 2021");
        assertEquals(j.getDescription(), "Pass isrp and enjoy life");
        assertEquals(j.getStatus(), "ToDo");
    }

}