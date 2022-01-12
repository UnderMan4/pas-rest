package p.lodz.pl.pas.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import p.lodz.pl.pas.RegexList;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.LoginNotUnique;
import p.lodz.pl.pas.manager.TicketManager;
import p.lodz.pl.pas.manager.UserManager;
import p.lodz.pl.pas.model.AccessLevel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("user")
public class UserController {
    @Inject
    UserManager userManager;

    @Inject
    TicketManager ticketManager;


    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String json) throws LoginNotUnique {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String login = jsonObject.get("login").getAsString();
        String name = jsonObject.get("name").getAsString();
        String surname = jsonObject.get("surname").getAsString();
        Boolean active = jsonObject.get("active").getAsBoolean();
        AccessLevel accessLevel = AccessLevel.valueOf(jsonObject.get("accessLevel").getAsString());
        if (!verifyLogin(login)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid login").build();
        } else if (!verifyName(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid name").build();
        } else if (!verifySurname(surname)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid surname").build();
        }

        try {
            userManager.createUser(login, name, surname, active, accessLevel);
            return Response.status(Response.Status.CREATED).build();
        } catch (LoginNotUnique e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    private boolean verifyLogin(String login) {
        return RegexList.Login.matcher(login).matches();
    }
    private boolean verifyName(String name) {
        return RegexList.Surname.matcher(name).matches();
    }
    private boolean verifySurname(String surname) {
        return RegexList.Surname.matcher(surname).matches();
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserList() {
        Gson gson = new Gson();
        return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(userManager.getUserList())).build();
    }


    @POST
    @Path("editUserWithUUID")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUserWithUUID(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        UUID uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
        String login = jsonObject.get("login").getAsString();
        String name = jsonObject.get("name").getAsString();
        String surname = jsonObject.get("surname").getAsString();
        Boolean active  = jsonObject.get("active").getAsBoolean();
        AccessLevel accessLevel = AccessLevel.valueOf(jsonObject.get("accessLevel").getAsString());

        if (!verifyLogin(login)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid login").build();
        } else if (!verifyName(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid name").build();
        } else if (!verifySurname(surname)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid surname").build();
        }

        try {
            userManager.editUserWithUUID(uuid, login, name, surname, active, accessLevel);
            return Response.status(Response.Status.ACCEPTED).entity("User edited").build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("setUserActive")
    public Response setUserActive(@QueryParam("UUID") UUID uuid, @QueryParam("status") boolean status) {
        try {
            userManager.setUserActive(uuid, status);
            return Response.status(Response.Status.ACCEPTED).entity("User activated").build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUser(@QueryParam("UUID") UUID uuid) {
        Gson gson = new Gson();
        try {
            return Response.status(Response.Status.ACCEPTED).entity(
                    gson.toJson(userManager.findUser(uuid))
            ).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("searchByLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserByLogin(@QueryParam("login") String login) {
        Gson gson = new Gson();
        try {
            return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(userManager.findUsersByLogin(login))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("searchByUUID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserByUUID(@QueryParam("UUID") String uuid) {
        Gson gson = new Gson();
        try {
            return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(userManager.searchByUUID(uuid))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }


}
