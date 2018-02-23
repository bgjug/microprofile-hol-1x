package bg.jug.microprofile.hol.users;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> roles;

    public User(String firstName, String lastName, String email, String password, List<String> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                    .add("firstName", firstName)
                    .add("lastName", lastName)
                    .add("email", email)
                    .add("roles", roles.stream().reduce(Json.createArrayBuilder(), JsonArrayBuilder::add, JsonArrayBuilder::add).build())
                .build();
    }
}
