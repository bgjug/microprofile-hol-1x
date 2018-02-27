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

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private LocalDate subscribedUntil;

    public Subscriber(){
        //empty constructor
    }

    public Subscriber(String firstName, String lastName, String email, String address, LocalDate subscribedUntil) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.subscribedUntil = subscribedUntil;
    }

    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public LocalDate getSubscribedUntil() {
        return subscribedUntil;
    }


    public void setSubscribedUntil(LocalDate subscribedUntil) {
        this.subscribedUntil = subscribedUntil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscriber)) return false;

        Subscriber that = (Subscriber) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        return subscribedUntil != null ? subscribedUntil.equals(that.subscribedUntil) : that.subscribedUntil == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (subscribedUntil != null ? subscribedUntil.hashCode() : 0);
        return result;
    }

    public JsonObject toJson() {
        JsonObjectBuilder result = Json.createObjectBuilder();
        result.add("address",getAddress())
                .add("email", getEmail())
                .add("firstName", getFirstName())
                .add("lastName", getLastName())
                .add("subscribedUntil", getSubscribedUntil().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return result.build();
    }

    public static Subscriber fromJson(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject subscriberObject = reader.readObject();
        reader.close();
        return new Subscriber(subscriberObject.getString("firstName"), subscriberObject.getString("lastName"), subscriberObject.getString("email"), subscriberObject.getString("address"), LocalDate.parse(subscriberObject.getString("subscribedUntil"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
