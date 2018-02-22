package bg.jug.microprofile.hol.users;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloResource {

    @GET
    public Response hello() {
        return Response.ok("Hello users").build();
    }
}
