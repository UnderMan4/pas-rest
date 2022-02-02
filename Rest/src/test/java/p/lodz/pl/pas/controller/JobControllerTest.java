package p.lodz.pl.pas.controller;

// import io.restassured.internal.http.URIBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

        import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import java.net.URISyntaxException;

public class JobControllerTest {
    
    ClientBuilder clientBuilder;
    Client client;
    WebTarget target;
    String created_uuid;
    String url = "http://localhost:8080/api/job";

    public JobControllerTest() throws URISyntaxException {
    }

    @BeforeMethod
    public void setUp() {
        clientBuilder = ClientBuilder.newBuilder();
        client = clientBuilder.build();
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testCreateJob() {
    }

    @Test
    public void testGetList() throws URISyntaxException {

        // JSONObject json = new JsonObject();
        // Gson json = new Gson();
        // json.fromJson("""
        //         [
        //             {
        //                 "login":"jkowalski",
        //                 "name":"Jan",
        //                 "surname":"Kowalski",
        //                 "uuid":"54ceb043-5f89-41bb-a29b-f2c0e9909dad",
        //                 "active":true,
        //                 "accessLevel":"User"
        //             },
        //             {
        //                 "login":"jjjkowal",
        //                 "name":"Jaroslaw",
        //                 "surname":"Kowalski",
        //                 "uuid":"40d68ba5-39ba-4f2e-9e61-a55daf7b3e8e",
        //                 "active":true,
        //                 "accessLevel":"ResourceAdministrator"
        //             },
        //             {
        //                 "login":"ttttt",
        //                 "name":"Tomasz",
        //                 "surname":"Kowalski",
        //                 "uuid":"84d267cf-6dc4-40cd-b1d3-000733a85458",
        //                 "active":true,
        //                 "accessLevel":"User"
        //             },
        //             {
        //                 "login":"Restitutor",
        //                 "name":"Lucius",
        //                 "surname":"Aurelianus",
        //                 "uuid":"295eea09-5541-42e4-ac24-126a0d87607e",
        //                 "active":true,
        //                 "accessLevel":"UserAdministrator"
        //             }
        //         ]
        //         """, '');
        // RestAssured.get(url + "/list").then()
        //         .statusCode(202).assertThat()
        //         .body("", equalTo());
    }

    @Test
    public void testFindJob() {
    }

    @Test
    public void testUpdateJob() {
    }

    @Test
    public void testRemoveJob() {
    }
}