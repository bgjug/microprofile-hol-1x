package bg.jug.microprofile.hol.authors;

import ws.ament.hammock.johnzon.JohnzonExtension;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        return authorsRepository.findAuthorByEmail(email)
                .map(author -> Response.ok(author.toJson()).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }


    @POST
    @Path("/add")
    public Response addAuthor(JsonObject authorJson) {
        Author author = Author.fromJson(authorJson);

        JsonObject requestBody = Json.createObjectBuilder()
                .add("email", author.getEmail())
                .add("role", "author")
                .build();
        Client client = ClientBuilder.newClient();
        /*
         * The JSON Body writer is not registered automatically in the Hammock JAX-RS client
         * runtime, so we need to do this manually
         */
        client.register(JohnzonExtension.class);
        Response addResponse = client.target(USER_URL)
                .path("role")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.json(requestBody));
        int status = addResponse.getStatus();
        client.close();

        if (status == Response.Status.OK.getStatusCode()) {
            authorsRepository.addAuthor(author);
        } // TODO think what to do if role was not added to user microservice
        return Response.ok().build();
    }


    private JsonArrayBuilder buildAuthorJsonArray(List<Author> authors){
        JsonArrayBuilder result = Json.createArrayBuilder();
        authors.forEach(e->{
            result.add(e.toJson());
        });
        return result;
    }

}
