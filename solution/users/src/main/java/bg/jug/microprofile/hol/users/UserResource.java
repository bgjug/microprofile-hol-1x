package bg.jug.microprofile.hol.users;

import org.eclipse.microprofile.metrics.annotation.Metered;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTH_TOKEN_PREFIX = "Bearer ";

    @Inject
    private UserRepository userRepository;

    @POST
    @Path("/find")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findUserByEmailAndPassword(JsonObject loginDetails) {
        return userRepository.findByLoginDetails(loginDetails.getString("email"), loginDetails.getString("password"))
                .map(user -> Response.ok(user.toJson()).header(AUTHORIZATION_HEADER, AUTH_TOKEN_PREFIX + JwtUtils.generateJWT(user)).build())
                .orElse(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Metered(name="User created or updated")
    public Response addUser(JsonObject newUser) {
        User user = User.fromJson(newUser);
        userRepository.createOrUpdate(user);
        return Response.ok().header(AUTHORIZATION_HEADER, AUTH_TOKEN_PREFIX + JwtUtils.generateJWT(user)).build();
    }

    @PUT
    @Path("/role")
    @Consumes(MediaType.APPLICATION_JSON)
    @Metered(name = "Role added to mail")
    public Response addRole(JsonObject roleDetails) {
        boolean found = userRepository.addRole(roleDetails.getString("email"), roleDetails.getString("role"));
        if (found) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/all")
    public Response getAllUsers() {
        JsonArray usersArray = userRepository.getAll()
                .stream()
                .map(User::toJson)
                .reduce(Json.createArrayBuilder(), JsonArrayBuilder::add, JsonArrayBuilder::add)
                .build();
        return Response.ok(usersArray).build();
    }
}
