package p.lodz.pl.pas.controller;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

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
    void createTicket() {
        WebTarget target = client.target("http://localhost:8080/api");
    }

    @Test
    void getTicketList() {
    }

    @Test
    void findUser() {
    }

    @Test
    void deleteUser() {
    }
}