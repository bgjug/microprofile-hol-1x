package bg.jug.microprofile.hol.content;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class AuthorClient {

    private static final String AUTHORS_URL = "http://localhost:9110/authors";

    public JsonObject findAuthorByEmail(String email) {
        Client client = ClientBuilder.newClient();
        Response response = client.target(AUTHORS_URL).path("findByEmail/" + email)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        JsonObject author = response.readEntity(JsonObject.class);
        response.close();
        return author;
    }
}
