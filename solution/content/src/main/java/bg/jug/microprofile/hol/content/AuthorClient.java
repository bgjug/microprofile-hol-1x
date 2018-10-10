package bg.jug.microprofile.hol.content;

import bg.jug.microprofile.hol.content.client.Author;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class AuthorClient {

    @Inject
    @ConfigProperty(name = "authorsServiceUrl", defaultValue = "http://localhost:9110/authors")
    private String authorsUrl;

    @Inject
    @RestClient
    private AuthorClient injectedAuthorsClient;


    @Retry
    @Fallback(fallbackMethod = "defaultAuthor")
    @Timeout(800)
//    @CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.6, delay = 2000L, successThreshold = 2)
    public JsonObject findAuthorByEmail(String email) {
        System.out.println("Looking up author");
        Client client = ClientBuilder.newClient();

        //FIXME: issue with marshalling
//        JsonObject author = injectedAuthorsClient.findAuthorByEmail(email);
        //FIXME: currentry using fallback:

        Response response = client.target(authorsUrl).path("findByEmail/" + email)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        JsonObject author = response.readEntity(JsonObject.class);
        response.close();
        return author;
    }

    public JsonObject defaultAuthor(String email) {
        return Json.createObjectBuilder()
                .add("firstName", "")
                .add("lastName", "Unkown")
                .add("bio", "Try again later")
                .add("email", email)
                .build();
    }
}
