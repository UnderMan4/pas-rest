package p.lodz.pl.pas.controller;

import org.junit.jupiter.api.*;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.model.Job;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JobControllerTest {

    ClientBuilder clientBuilder;
    Client client;
    WebTarget target;

    @BeforeEach
    void setUp() {
        clientBuilder = ClientBuilder.newBuilder();
        client = clientBuilder.build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createJob() {
        WebTarget target = client.target("http://localhost:8080/api/job");
        String json = """
                {
                   "name": "Nazwa zadania",
                  "description": "Opis zadania"
                }
                """;
        Response response = target.path("create").request(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
        assertNotNull(response);
        assertEquals(201, response.getStatus());
        assertEquals(response.readEntity(UUID.class).getClass(), UUID.class);
    }

    @Test
    void getList() {
    }

    @Test
    void findJob() {
    }

    @Test
    void updateJob() {
    }

    @Test
    void removeJob() {
    }
}