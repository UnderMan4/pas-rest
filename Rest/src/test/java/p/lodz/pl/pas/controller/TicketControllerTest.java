package p.lodz.pl.pas.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p.lodz.pl.pas.model.Ticket;
import p.lodz.pl.pas.model.TicketStatus;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static p.lodz.pl.pas.conversion.GsonLocalDateTime.getGsonSerializer;

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
                    "jobStart": "2021-12-09T08:00:00",
                    "jobEnd": "2021-12-13T16:00:00",
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
                    "jobStart": "2021-08-01T08:00:00",
                    "jobEnd": "2021-11-20T16:00:00",
                    "description": "Pass isrp and enjoy life",
                    "status": "ToDo"
                  }
                ]""";
        WebTarget target = client.target("http://localhost:8080/api/ticket");
        Response response = target.path("list").request(MediaType.APPLICATION_JSON_TYPE).get();
        assertNotNull(response);
        assertEquals(202, response.getStatus());
        assertEquals(json, response.readEntity(String.class));

    }

    @Test
    void findTicket() {
        String uuid = "86600b3f-2d48-4d88-a26b-45ec2eeb4845";
        WebTarget target = client.target("http://localhost:8080/api/ticket");
        String s = target.queryParam("UUID", uuid).request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        Ticket j = getGsonSerializer().fromJson(s, Ticket.class);
        assertEquals(j.getJobStart(), LocalDateTime.parse("2021-12-09T08:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        assertEquals(j.getJobEnd(), LocalDateTime.parse("2021-12-13T16:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        assertEquals(j.getDescription(), "Cleanup code ticket description");
        assertEquals(j.getStatus(), TicketStatus.ToDo);
    }

}