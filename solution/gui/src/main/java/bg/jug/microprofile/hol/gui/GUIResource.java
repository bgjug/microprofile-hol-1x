package bg.jug.microprofile.hol.gui;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class GUIResource {

    private static final String USER_URL = "http://localhost:9100/users";
    private static final String CONTENT_URL = "http://localhost:9120/content";

    @Inject
    private UserContext userContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/login")
    public Response login(@FormParam("email") String email,
                          @FormParam("password") String password) {
        JsonObject requestBody = Json.createObjectBuilder()
                .add("email", email)
                .add("password", password)
                .build();
        Client client = ClientBuilder.newClient();
        Response loginResponse = client.target(USER_URL).path("find")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(requestBody));

        if (loginResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            userContext.setLoggedUser(User.fromJson(loginResponse.readEntity(JsonObject.class)));
        }
        client.close();
        return loginResponse;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response register(JsonObject newUser) {
        Client client = ClientBuilder.newClient();
        Response loginResponse = client.target(USER_URL).path("add")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(newUser));

        if (loginResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            userContext.setLoggedUser(User.fromJson(newUser));
        }
        client.close();
        return loginResponse;
    }

    @GET
    @Path("/articles")
    public Response getAllArticles() {
        Client client = ClientBuilder.newClient();
        Response articlesResponse = client.target(CONTENT_URL).path("all")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        JsonArray responseData = articlesResponse.readEntity(JsonArray.class);
        client.close();
        return Response.ok(responseData).build();
    }

    @GET
    @Path("/article/{id}")
    public Response findArticleById(@PathParam("id") Long articleId) {
        Client client = ClientBuilder.newClient();
        Response articleResponse = client.target(CONTENT_URL).path("findById/" + articleId)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        Response response;
        if (articleResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            response = Response.ok(articleResponse.readEntity(JsonObject.class)).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        client.close();
        return response;
    }

}
