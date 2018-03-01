package bg.jug.microprofile.hol.subscribers;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Dmitry Alexandrov on 26.02.2018.
 */
public class Subscriber {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate subscribedUntil;

    public Subscriber(String firstName, String lastName, String email, LocalDate subscribedUntil) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.subscribedUntil = subscribedUntil;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getSubscribedUntil() {
        return subscribedUntil;
    }

    public JsonObject toJson() {
        JsonObjectBuilder result = Json.createObjectBuilder();
        result.add("email", getEmail())
                .add("firstName", getFirstName())
                .add("lastName", getLastName())
                .add("subscribedUntil", getSubscribedUntil().format(DATE_TIME_FORMATTER));

        return result.build();
    }

    public static Subscriber fromJson(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject subscriberObject = reader.readObject();
        reader.close();
        return new Subscriber(subscriberObject.getString("firstName"),
                subscriberObject.getString("lastName"),
                subscriberObject.getString("email"),
                LocalDate.parse(subscriberObject.getString("subscribedUntil"), DATE_TIME_FORMATTER));
    }
}
