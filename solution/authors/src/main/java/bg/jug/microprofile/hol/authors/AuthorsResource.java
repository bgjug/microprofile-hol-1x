package bg.jug.microprofile.hol.authors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 27.02.18.
 */
@RequestScoped
@Path("/")
public class AuthorsResource {

    private static final String USER_URL = "http://localhost:9100/users";

    @Inject
    private AuthorsRepository authorsRepository;

    @GET
    @Path("/all")
    public Response getAuthors() {
        return Response.ok(buildAuthorJsonArray(authorsRepository.getAuthors()).build()).build();
    }

    @GET
    @Path("/findByEmail/{email}")
    public Response findAuthorById(@PathParam("email") String email) {
        return (authorsRepository.findAuthorByEmail(email)).map(author -> Response.ok(author.toJson()).build()).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }


    @POST
    public void addAuthor(String authorString) {
        Author author = Author.fromJson(authorString);

        JsonObject requestBody = Json.createObjectBuilder()
                .add("email", author.getEmail())
                .add("role", "author")
                .build();
        Client client = ClientBuilder.newClient();
        Response addResponse = client.target(USER_URL).path("role")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.json(requestBody));

        if (addResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            //FIXME: is this ok?
            authorsRepository.addAuthor(author);
        }
        client.close();
    }


    private JsonArrayBuilder buildAuthorJsonArray(List<Author> authors){
        JsonArrayBuilder result = Json.createArrayBuilder();
        authors.forEach(e->{
            result.add(e.toJson());
        });
        return result;
    }

}
