package bg.jug.microprofile.hol.content.client;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Dmitry Alexandrov.
 */
@Path("/")
@Consumes("application/json")
@RegisterRestClient
@RegisterProvider(AuthorExceptionMapper.class)
public interface AuthorsClient {

    @GET
    List<Author> getAllAuthors();

    @GET
    @Path("/findByEmail/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    Author findAuthorByEmail(@PathParam("email") String email);
}
