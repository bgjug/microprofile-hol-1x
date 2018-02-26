package bg.jug.microprofile.hol.subscribers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 26.02.2018.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class SubscribersResource {

    @Inject
    private SubscribersRepository subscribersRepository;

    @GET
    @Path("/all")
    @Produces("application/json")
    public Response getSubscribers() {
        return Response.ok(buildSubscriberJsonArray(subscribersRepository.getSubscribers()).build()).build();
    }

    @GET
    @Path("/find/{id}")
    @Produces("application/json")
    public Response findSubscriberById(@PathParam("id") Long subscriberId) {
        return Response.ok(buildSubscriberJson(subscribersRepository.findSubscriberById(subscriberId)).build()).build();
    }


    @POST
    public void addSubscriber(String subscriberString) {
        Subscriber subscriber = readSubscriberFromJson(subscriberString);
        subscribersRepository.addSubscriber(subscriber);
    }

    // Helpers
    private JsonObjectBuilder buildSubscriberJson(Subscriber subscriber) {
        JsonObjectBuilder result = Json.createObjectBuilder();
        result.add("address", subscriber.getAddress())
                .add("email", subscriber.getEmail())
                .add("firstName", subscriber.getFirstName())
                .add("lastName", subscriber.getLastName())
                .add("subscribedUntil", subscriber.getSubscribedUntil().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        if (subscriber.getId() != null)
            result.add("id", subscriber.getId());
        return result;
    }

    private JsonArrayBuilder buildSubscriberJsonArray(List<Subscriber> subscribers) {
        JsonArrayBuilder result = Json.createArrayBuilder();
        subscribers.forEach(e -> {
            result.add(buildSubscriberJson(e));
        });
        return result;
    }

    private Subscriber readSubscriberFromJson(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject subscriberObject = reader.readObject();
        reader.close();
        return new Subscriber(Long.valueOf(subscriberObject.getString("id")), subscriberObject.getString("firstName"), subscriberObject.getString("lastName"), subscriberObject.getString("email"), subscriberObject.getString("address"), LocalDate.parse(subscriberObject.getString("subscribedUntil"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
