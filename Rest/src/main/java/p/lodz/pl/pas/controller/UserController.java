package p.lodz.pl.pas.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.LoginNotUnique;
import p.lodz.pl.pas.manager.UserManager;
import p.lodz.pl.pas.model.AccessLevel;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.UUID;

import static p.lodz.pl.pas.conversion.GsonLocalDateTime.getGsonSerializer;

@Path("user")
@RolesAllowed({"Admin", "UserAdministrator"})
public class UserController {
    @Inject
    UserManager userManager;
    
    private Response createUser(String json, AccessLevel userType) {
        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            String login = jsonObject.get("login").getAsString();
            String password = jsonObject.get("password").getAsString();
            String name = jsonObject.get("name").getAsString();
            String surname = jsonObject.get("surname").getAsString();
            Boolean active = jsonObject.get("active").getAsBoolean();
            userManager.createUser(login, password, name, surname, active, userType);
            return Response.status(Response.Status.CREATED).build();
        } catch (JsonSyntaxException | NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid json name").build();
        } catch (LoginNotUnique e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("createNormalUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUserNormalUser(@NotNull String json) {
        return createUser(json, AccessLevel.NormalUser);
    }

    @POST
    @Path("createUserAdministrator")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUserAdministrator(@NotNull String json) {
        return createUser(json, AccessLevel.UserAdministrator);
    }

    @POST
    @Path("createAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAdmin(@NotNull String json) {
        return createUser(json, AccessLevel.Admin);
    }

    @POST
    @Path("createResourceAdministrator")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createResourceAdministrator(@NotNull String json) {
        return createUser(json, AccessLevel.ResourceAdministrator);
    }


    @GET
    @RolesAllowed({"Admin", "UserAdministrator"})
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserList() {
        Gson gson = getGsonSerializer();
        return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(userManager.getUserList())).build();
    }

    @POST
    @Path("editUserWithUUID")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUserWithUUID(@NotNull String json) {
        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            UUID uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
            String login = jsonObject.get("login").getAsString();
            String password = jsonObject.get("password").getAsString();
            String name = jsonObject.get("name").getAsString();
            String surname = jsonObject.get("surname").getAsString();
            Boolean active = jsonObject.get("active").getAsBoolean();
            AccessLevel accessLevel = AccessLevel.valueOf(jsonObject.get("accessLevel").getAsString());
            userManager.editUserWithUUID(uuid, login, password, name, surname, active, accessLevel);
            return Response.status(Response.Status.ACCEPTED).entity("User edited").build();
        } catch (JsonSyntaxException | NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid json name").build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("setUserActive")
    public Response setUserActive(@QueryParam("UUID") @NotNull UUID uuid, @QueryParam("status") @NotNull boolean status) {
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
    public Response findUserByLogin(@QueryParam("login") @NotNull String login) {
        try {
            return Response.status(Response.Status.ACCEPTED).entity(getGsonSerializer().toJson(userManager.findUsersByLogin(login))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("searchByUUID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserByUUID(@QueryParam("UUID") @NotNull String uuid) {
        try {
            return Response.status(Response.Status.ACCEPTED).entity(getGsonSerializer().toJson(userManager.searchByUUID(uuid))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("_self")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response findSelf(@Context SecurityContext securityContext) {
        try {
            return Response.status(Response.Status.ACCEPTED).entity(
                    getGsonSerializer().toJson(userManager.findUsersByLogin(securityContext.getUserPrincipal().getName()))
            ).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }


}
