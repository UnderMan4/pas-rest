package p.lodz.pl.pas.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import p.lodz.pl.pas.RegexList;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.LoginNotUnique;
import p.lodz.pl.pas.manager.UserManager;
import p.lodz.pl.pas.model.AccessLevel;
import p.lodz.pl.pas.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("user")
public class UserController {
    @Inject
    UserManager userManager;


    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String json) throws LoginNotUnique {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
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
        } else if (!verifyActive(active)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        } else if (!verifyAccessLevel(accessLevel)){
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        }

        if (userManager.createUser(
                jsonObject.get("login").getAsString(), 
                jsonObject.get("name").getAsString(), 
                jsonObject.get("surname").getAsString(), 
                jsonObject.get("active").getAsBoolean(), 
                AccessLevel.valueOf(jsonObject.get("accessLevel").getAsString())
        )){
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private boolean verifyActive(Boolean active) {
        return false;
    }

    private boolean verifyAccessLevel(AccessLevel accessLevel) {
        return false;
    }

    private boolean verifyLogin(String login) {
        return RegexList.Login.matcher(login).matches();
    }
    private boolean verifyName(String name) {
        return RegexList.User_Name.matcher(name).matches();
    }

    private boolean verifySurname(String surname) {
        return RegexList.Surname.matcher(surname).matches();
    }

    @GET
    @Path("list")
    public Response getUserList() {
        Gson gson = new Gson();
        return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(userManager.getUserList())).build();
    }

    @GET
    public Response findUser(@QueryParam("UUID") UUID uuid) {
        Gson gson = new Gson();
        try {
            return Response.status(Response.Status.ACCEPTED).entity(
                    gson.toJson(userManager.findUser(uuid))
            ).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("login")
    public Response findUser(@QueryParam("login") String login) {
        Gson gson = new Gson();
        try {
            return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(userManager.findUser(login))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("editUserWithUUID")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUserWithUUID(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        UUID uuid = UUID.fromString(jsonObject.get("UUID").getAsString());
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
        } else if (!verifyActive(active)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        } else if (!verifyAccessLevel(accessLevel)){
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        }

        try {
            userManager.editUserWithUUID(uuid, jsonObject.get("login").getAsString(), jsonObject.get("name").getAsString(), jsonObject.get("surname").getAsString(), jsonObject.get("active").getAsBoolean(), AccessLevel.valueOf(jsonObject.get("accessLevel").getAsString()));
            return Response.status(Response.Status.CREATED).entity("User edited").build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
        }
    }

    @GET
    @Path("setUserActive")
    public Response setUserActive(@QueryParam("UUID")UUID uuid, @QueryParam("status") boolean active) {

        try {
            userManager.findUser(uuid);
            return Response.status(Response.Status.CREATED).entity("User active").build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
        }
    }

}
