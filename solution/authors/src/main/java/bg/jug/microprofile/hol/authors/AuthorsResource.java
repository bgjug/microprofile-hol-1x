package bg.jug.microprofile.hol.authors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 27.02.18.
 */
@RequestScoped
@Path("/")
public class AuthorsResource {

    @Inject
    private AuthorsRepository authorsRepository;

    @GET
    @Path("/all")
    @Produces("application/json")
    public Response getAuthors() {
        return Response.ok(buildAuthorJsonArray(authorsRepository.getAuthors()).build()).build();
    }

    @GET
    @Path("/findById/{id}")
    @Produces("application/json")
    public Response findAuthorById(@PathParam("id") Long authorId) {
        return Response.ok(buildAuthorJson(authorsRepository.findAuthorById(authorId)).build()).build();
    }


    @GET
    @Path("/findByNames/{names}")
    @Produces("application/json")
    public Response findAuthorByNames(@PathParam("names") final String names) {
        return Response.ok(buildAuthorJsonArray(authorsRepository.findAuthorByNames(names)).build()).build();
    }

    @POST
    public void addAuthor(String author) {
        authorsRepository.addAuthor(readAuthorFromJson(author));
    }

    private JsonObjectBuilder buildAuthorJson(Author author){
        JsonObjectBuilder result = Json.createObjectBuilder();
        result.add("lastName",author.getLastName())
        .add("firstName",author.getFirstName())
        .add("email",author.getEmail())
        .add("salary",author.getSalary())
        .add("regular",author.isRegular());
        if (author.getId()!=null) result.add("id",author.getId());
        return result;
    }

    private JsonArrayBuilder buildAuthorJsonArray(List<Author> authors){
        JsonArrayBuilder result = Json.createArrayBuilder();
        authors.forEach(e->{
            result.add(buildAuthorJson(e));
        });
        return result;
    }

    private Author readAuthorFromJson(String json){
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject authorObject = reader.readObject();
        reader.close();
        return new Author(Long.valueOf(authorObject.getString("id")),authorObject.getString("firstName"),authorObject.getString("lastName"),authorObject.getString("email"),Boolean.valueOf(authorObject.getString("regular")),Integer.valueOf(authorObject.getString("salary")));
    }
}
