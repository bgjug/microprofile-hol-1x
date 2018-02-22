package bg.jug.microprofile.hol.subscribers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloResource {

    @GET
    public Response hello() {
        return Response.ok("Hello subscribers").build();
    }
}
