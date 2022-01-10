package p.lodz.pl.pas.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerTest {
    ClientBuilder clientBuilder;
    Client client;

    @BeforeEach
    void setUp() {
        clientBuilder = ClientBuilder.newBuilder();
        client = clientBuilder.build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createUser() {
        WebTarget target = client.target("http://localhost:8080/api/user");
        String json = """
                {
                  "login": Login",
                  "name": "Imie uzytkownika",
                  "surname": "Nazwisko",
                  "active": "Status uzytkownika",
                  "AccessLevel": "Poziom dostepu"
                }
                """;
        Response response = target.path("create").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
        assertNotNull(response);
        assertEquals(201, response.getStatus());
        assertEquals(response.readEntity(UUID.class).getClass(), UUID.class);
    }

    @Test
    void getUserList() {
        String listJson = "[{\"login\":\"jkowalski\",\"name\":\"Jan\",\"surname\":\"Kowalski\",\"active\":\"true\":\"accessLevel\":\"User\"}," +
                "[{\"login\":\"jjjkowal\",\"name\":\"Jaroslaw\",\"surname\":\"Kowalski\",\"active\":\"true\":\"accessLevel\":\"ResourceAdministrator\"}," +
                "[{\"login\":\"ttttt\",\"name\":\"Jan\",\"surname\":\"Kowalski\",\"active\":\"true\":\"accessLevel\":\"User\"}," +
                "[{\"login\":\"Restitutor\",\"name\":\"Lucius\",\"surname\":\"Aurelianus\",\"active\":\"true\":\"accessLevel\":\"UserAdministrator\"}]";
        WebTarget target = client.target("http://localhost:8080/api/user");
        Response response = target.path("list").request(MediaType.APPLICATION_JSON_TYPE).get();
        assertNotNull(response);
        assertEquals(202, response.getStatus());
        assertEquals(response.readEntity(String.class), listJson);
    }

    @Test
    void editUserWithUUID() {
        String json = """
                {
                  "login": "Login",
                  "name": "Imie uzytkownika",
                  "surname": "Nazwisko",
                  "active": "true",
                  "AccessLevel": "User"
                }
                """;
        WebTarget target = client.target("http://localhost:8080/api/user");

        String jsonUpdated = """
                {
                  "login": "Zedytowany login",
                  "name": "Zedytowane imie uzytkownika",
                  "surname": "Zedytowane nazwisko",
                  "active": "true",
                  "AccessLevel": "User"
                }
                """;
        Response response = target.path("editUserWithUUID").request(MediaType.APPLICATION_JSON).post(Entity.entity(jsonUpdated, MediaType.APPLICATION_JSON_TYPE));
        assertNotNull(response);
        assertEquals(200, response.getStatus());


        // String s = target.queryParam("UUID", uuid).request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        // Gson gson = new Gson();
        // Job j = gson.fromJson(s, Job.class);
        // assertEquals(j.getLogin(), "Zedytowany login");
        // assertEquals(j.getName(), "Zedytowane imie uzytkownika");
        // assertEquals(j.getSurname(), "Zedytowane nazwisko");
        // assertEquals(j.getActive(), "Zedytowany status uzytkownika");
        // assertEquals(j.getAccessLevel(), "Zedytowany poziom dostepu");
    }

}

