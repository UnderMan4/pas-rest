package p.lodz.pl.pas.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import p.lodz.pl.pas.conversion.GsonLocalDateTime;
import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.exceptions.LoginNotUnique;
import p.lodz.pl.pas.filter.SignatureValidatorFilter;
import p.lodz.pl.pas.filter.SignatureVerifier;
import p.lodz.pl.pas.manager.UserManager;
import p.lodz.pl.pas.model.AccessLevel;
import p.lodz.pl.pas.model.User;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static p.lodz.pl.pas.conversion.GsonLocalDateTime.getGsonSerializer;

@Path("user")
@RolesAllowed({"Admin", "UserAdministrator"})
public class UserController {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

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
    @PermitAll
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserList() {
        return Response.status(Response.Status.ACCEPTED).entity(getGsonSerializer().toJson(userManager.getUserList())).build();
    }

    /**
     * @param json     user in json format with accessLevel to specify user class
     * @param tagValue If-match tag from findUserByUUID
     * @return result of user edit
     */
    @POST
    @Path("update")
    @SignatureValidatorFilter
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(String json, @HeaderParam("If-match") @NotNull @NotEmpty String tagValue) {
        try {
            User user = GsonLocalDateTime.getGsonSerializer().fromJson(json, User.class);
            if (!SignatureVerifier.verifyEntityIntegrity(tagValue, user)) {
                return Response.status(Response.Status.PRECONDITION_FAILED).build();
            }
            userManager.editUserWithUUID(user.getUuid(), user.getLogin(), user.getPassword(), user.getName(), user.getSurname(), user.getActive(), user.getUserAccessLevel());
            return Response.status(Response.Status.ACCEPTED).entity("User edited").build();
        } catch (JsonSyntaxException | NullPointerException | ItemNotFoundException e) {
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

    /**
     * Returns with etag for user editing
     *
     * @param uuid exact login to find user by
     * @return user object
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserByUUID(@QueryParam("UUID") UUID uuid) {
        try {
            User user = userManager.findUser(uuid);
            EntityTag tag = new EntityTag(SignatureVerifier.calculateEntitySignature(user));
            return Response.status(Response.Status.ACCEPTED).entity(
                    getGsonSerializer().toJson(user)
            ).tag(tag).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * @param login fragment or whole login of a user
     * @return list of users that contains the login
     */
    @GET
    @Path("searchByLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUsersByLogin(@QueryParam("login") @NotNull String login) {
        try {
            return Response.status(Response.Status.ACCEPTED).entity(getGsonSerializer().toJson(userManager.findUsersByLogin(login))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * @param uuid fragment or whole UUID of a user
     * @return list of users that contains the uuid
     */
    @GET
    @Path("searchByUUID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUsersByUUID(@QueryParam("UUID") @NotNull String uuid) {
        try {
            return Response.status(Response.Status.ACCEPTED).entity(getGsonSerializer().toJson(userManager.searchByUUID(uuid))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("search")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("s") String s) {
        try {
            Gson gson = new Gson();
            return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(userManager.search(s))).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    // ------------------------------------------------------------------------------------------------


    @GET
    @Path("_self")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response findSelf(@Context SecurityContext securityContext) {
        try {
            return Response.status(Response.Status.ACCEPTED).entity(
                    getGsonSerializer().toJson(userManager.findUserByLogin(securityContext.getUserPrincipal().getName()))
            ).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("changePassword")
    @PermitAll
    @Consumes(MediaType.TEXT_PLAIN)
    public Response changePassword(@Context SecurityContext securityContext, @NotNull @NotEmpty String password) {
        try {
            LOGGER.log(Level.INFO, "Password: " + password);
            Gson gson = new Gson();
            return Response.status(Response.Status.ACCEPTED).entity(
                    getGsonSerializer().toJson(userManager.changePassword(securityContext.getUserPrincipal().getName(), password))
            ).build();
        } catch (ItemNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
