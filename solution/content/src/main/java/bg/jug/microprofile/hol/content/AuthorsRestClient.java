package bg.jug.microprofile.hol.content;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Dmitry Alexandrov.
 */
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
@RegisterProvider(AuthorExceptionMapper.class)
public interface AuthorsRestClient {

    @GET
    @Path("/findByEmail/{email}")
    JsonObject findAuthorByEmail(@PathParam("email") String email);
}
