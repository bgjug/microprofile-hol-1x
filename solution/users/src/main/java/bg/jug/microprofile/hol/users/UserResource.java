package bg.jug.microprofile.hol.users;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserRepository userRepository;

    @POST
    @Path("/find")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findUserByEmailAndPassword(JsonObject loginDetails) {
        return userRepository.findByLoginDetails(loginDetails.getString("email"), loginDetails.getString("password"))
                .map(user -> Response.ok(user.toJson()).build())
                .orElse(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
