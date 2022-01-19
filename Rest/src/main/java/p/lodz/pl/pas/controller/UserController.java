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

import static p.lodz.pl.pas.conversion.GsonLocalDateTime.getGsonSerializer;

@Path("user")
public class UserController {
    @Inject
    UserManager userManager;

    @Inject
    TicketManager ticketManager;
    
    private Response createUser(String json, AccessLevel userType) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String login = jsonObject.get("login").getAsString();
        String name = jsonObject.get("name").getAsString();
        String surname = jsonObject.get("surname").getAsString();
        Boolean active = jsonObject.get("active").getAsBoolean();
        if (!verifyLogin(login)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid login").build();
        } else if (!verifyName(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid name").build();
        } else if (!verifySurname(surname)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid surname").build();
        }

        try {
            userManager.createUser(login, name, surname, active, userType);
            return Response.status(Response.Status.CREATED).build();
        } catch (LoginNotUnique e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @POST
    @Path("createNormalUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUserNormalUser(String json) throws LoginNotUnique {
        return createUser(json, AccessLevel.NormalUser);
    }

    @POST
    @Path("createUserAdministrator")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUserAdministrator(String json) throws LoginNotUnique {
        return createUser(json, AccessLevel.UserAdministrator);
    }

    @POST
    @Path("createAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAdmin(String json) throws LoginNotUnique {
        return createUser(json, AccessLevel.Admin);
    }

    @POST
    @Path("createResourceAdministrator")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createResourceAdministrator(String json) throws LoginNotUnique {
        return createUser(json, AccessLevel.ResourceAdministrator);
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
        Gson gson = getGsonSerializer();
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
        try {
            return Response.status(Response.Status.ACCEPTED).entity(
                    getGsonSerializer().toJson(userManager.findUser(uuid))
            ).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("searchByLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserByLogin(@QueryParam("login") String login) {
        try {
            return Response.status(Response.Status.ACCEPTED).entity(getGsonSerializer().toJson(userManager.findUsersByLogin(login))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("searchByUUID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserByUUID(@QueryParam("UUID") String uuid) {
        try {
            return Response.status(Response.Status.ACCEPTED).entity(getGsonSerializer().toJson(userManager.searchByUUID(uuid))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }


}
