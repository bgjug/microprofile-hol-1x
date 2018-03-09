package bg.jug.microprofile.hol.subscribers;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Metered;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 26.02.2018.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SubscribersResource {

    @Inject
    @ConfigProperty(name = "usersServiceUrl", defaultValue = "http://localhost:9100/users")
    private String usersUrl;

    @Inject
    private SubscribersRepository subscribersRepository;

    @GET
    @Path("/all")
    public Response getSubscribers() {
        return Response.ok(buildSubscriberJsonArray(subscribersRepository.getSubscribers()).build()).build();
    }

    @GET
    @Path("/findByEmail/{email}")
    public Response findSubscriberById(@PathParam("email") String email) {
        return (subscribersRepository.findSubscriberByEmail(email))
                .map(subscriber -> Response.ok(subscriber.toJson()).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Path("/add")
    @Metered(name = "Subscriber added")
    public Response addSubscriber(String subscriberString) {
        Subscriber subscriber = Subscriber.fromJson(subscriberString);

        JsonObject requestBody = Json.createObjectBuilder()
                .add("email", subscriber.getEmail())
                .add("role", "subscriber")
                .build();
        Client client = ClientBuilder.newClient();
        Response addResponse = client.target(usersUrl).path("role")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.json(requestBody));

        if (addResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            subscribersRepository.addSubscriber(subscriber);
        }
        client.close();
        return Response.ok().build();
    }

    // Helpers
    private JsonArrayBuilder buildSubscriberJsonArray(List<Subscriber> subscribers) {
        JsonArrayBuilder result = Json.createArrayBuilder();
        subscribers.forEach(e -> result.add(e.toJson()));
        return result;
    }

}
