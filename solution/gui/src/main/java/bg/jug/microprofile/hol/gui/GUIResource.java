package bg.jug.microprofile.hol.gui;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class GUIResource {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTH_TOKEN_PREFIX = "Bearer ";

    static final String AUTH_COOKIE = "Authorization";

    @Inject
    @ConfigProperty(name = "usersServiceUrl", defaultValue = "http://localhost:8081/users")
    private String usersUrl;
    @Inject
    @ConfigProperty(name = "authorsServiceUrl", defaultValue = "http://localhost:8082/authors")
    private String authorsUrl;
    @Inject
    @ConfigProperty(name = "contentServiceUrl", defaultValue = "http://localhost:8083/content")
    private String contentUrl;
    @Inject
    @ConfigProperty(name = "subscribersServiceUrl", defaultValue = "http://localhost:8084/subscribers")
    private String subscribersUrl;

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
        Response loginResponse = client.target(usersUrl).path("find")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(requestBody));
        client.close();

        return handleAuthentication(loginResponse);

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response register(JsonObject newUser) {
        Client client = ClientBuilder.newClient();
        Response handleAuthentication = client.target(usersUrl).path("add")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(newUser));
        client.close();

        return handleAuthentication;
    }

    private Response handleAuthentication(Response authResponse) {
        if (authResponse.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            String headerString = authResponse.getHeaderString(AUTHORIZATION_HEADER);
            String token = headerString.substring(AUTH_TOKEN_PREFIX.length());

            return Response.ok()
                    .cookie(new NewCookie(AUTH_COOKIE, token))
                    .build();
        } else {
            return authResponse;
        }
    }

    @GET
    @Path("/nonsubscribers")
    public Response getAllNonSubscribers() {
        return buildUsersWithoutRole("subscriber");
    }

    @GET
    @Path("/nonauthors")
    public Response getAllNonAuthors() {
        return buildUsersWithoutRole("author");
    }

    private Response buildUsersWithoutRole(String missingRole) {
        Response allUsers = getAllEntities(usersUrl);
        JsonArray nonSubscribers = allUsers.readEntity(JsonArray.class).stream()
                .filter(v -> !((JsonObject) v).getJsonArray("roles").toString().contains(missingRole))
                .reduce(Json.createArrayBuilder(), JsonArrayBuilder::add, JsonArrayBuilder::add)
                .build();
        return Response.ok(nonSubscribers).build();
    }

    @GET
    @Path("/articles")
    public Response getAllArticles() {
        return getAllEntities(contentUrl);
    }

    @GET
    @Path("/article/{id}")
    public Response findArticleById(@PathParam("id") Long articleId) {
        Client client = ClientBuilder.newClient();
        Response articleResponse = client.target(contentUrl).path("findById/" + articleId)
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

    @POST
    @Path("/article")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addArticle(JsonObject articleJson, @CookieParam(AUTH_COOKIE) String authToken) {
        JsonObject sendJson = Json.createObjectBuilder()
                .add("title", articleJson.getString("title"))
                .add("content", articleJson.getString("content"))
                .build();

        Client client = ClientBuilder.newClient();
        return client.target(contentUrl).path("add")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(AUTHORIZATION_HEADER, AUTH_TOKEN_PREFIX + authToken)
                .post(Entity.json(sendJson));
    }

    @POST
    @Path("/subscriber")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSubscriber(JsonObject userJson, @CookieParam(AUTH_COOKIE) String authToken) {
        JsonObject subscriberJson = Json.createObjectBuilder()
                .add("email", userJson.getString("email"))
                .add("firstName", userJson.getString("firstName"))
                .add("lastName", userJson.getString("lastName"))
                .add("subscribedUntil", LocalDate.now().plusMonths(6).format(DATE_TIME_FORMATTER))
                .build();

        Client client = ClientBuilder.newClient();
        Response addSubscriberResponse = client.target(subscribersUrl).path("add")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(AUTHORIZATION_HEADER, AUTH_TOKEN_PREFIX + authToken)
                .post(Entity.json(subscriberJson));
        client.close();
        return addSubscriberResponse;
    }

    @POST
    @Path("/author")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAuthor(JsonObject authorJson) {
        Client client = ClientBuilder.newClient();
        Response addAuthorResponse = client.target(authorsUrl).path("add")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(authorJson));
        client.close();
        return addAuthorResponse;
    }

    private Response getAllEntities(String rootUrl) {
        Client client = ClientBuilder.newClient();
        Response response = client.target(rootUrl).path("all")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        JsonArray responseData = response.readEntity(JsonArray.class);
        client.close();
        return Response.ok(responseData).build();
    }

}
