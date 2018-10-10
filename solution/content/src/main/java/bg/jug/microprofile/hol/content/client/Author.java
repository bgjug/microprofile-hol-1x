package bg.jug.microprofile.hol.content.client;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.Serializable;

/**
 * Created by Dmitry Alexandrov.
 */
public class Author implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private int salary;

    public Author(){
        //must have
    }

    public Author(String firstName, String lastName, String email, String bio, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bio = bio
        ;
        this.salary = salary;
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

    public String getBio() {
        return bio;
    }

    public int getSalary() {
        return salary;
    }

    public JsonObject toJson(){
        JsonObjectBuilder result = Json.createObjectBuilder();
        result.add("lastName",getLastName())
                .add("firstName",getFirstName())
                .add("email",getEmail())
                .add("salary",getSalary())
                .add("bio",getBio());
        return result.build();
    }

    public static Author fromJson(JsonObject authorObject){
        return new Author(authorObject.getString("firstName"),
                authorObject.getString("lastName"),
                authorObject.getString("email"),
                authorObject.getString("bio"),
                authorObject.getJsonNumber("salary").intValue());
    }
}
