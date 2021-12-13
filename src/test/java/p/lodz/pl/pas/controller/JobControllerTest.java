package p.lodz.pl.pas.controller;

import org.junit.jupiter.api.*;

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

        String listJson = "[{\"uuid\":\"b8344cdb-dc2d-42a0-8c0f-d35f676b8074\",\"name\":\"Cleanup code\",\"description\":\"Cleanup code in this program\"},{\"uuid\":\"858908da-ae32-4527-bdcb-13a91b0a49b9\",\"name\":\"Pass ISRP\",\"description\":\"Pass ISRP and achive greatness\"},{\"uuid\":\"20edf313-9c1c-43e8-ae39-e1703d3928f8\",\"name\":\"Find meaning in life\",\"description\":\"Yes\"},{\"uuid\":\"d749db34-3f68-47e1-9e73-fe22d421b70a\",\"name\":\"Finish pas\",\"description\":\"Finish this task and move on to another\"}]";
        WebTarget target = client.target("http://localhost:8080/api/job");
        Response response = target.path("list").request(MediaType.APPLICATION_JSON_TYPE).get();
        assertNotNull(response);
        assertEquals(202, response.getStatus());
        assertEquals(response.readEntity(String.class), listJson);
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