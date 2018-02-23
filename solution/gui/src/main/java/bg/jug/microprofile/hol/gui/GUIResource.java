package bg.jug.microprofile.hol.gui;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
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

}
