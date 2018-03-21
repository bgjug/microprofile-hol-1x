package bg.jug.microprofile.hol.users;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> roles;

    public User(String firstName, String lastName, String email, String password,
                String... roles) {
        this(firstName, lastName, email, password, Stream.of(roles).collect(Collectors.toList()));
    }

    public User(String firstName, String lastName, String email, String password,
                List<String> roles) {
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

    public static User fromJson(JsonObject userJson) {
        return new User(userJson.getString("firstName"),
                userJson.getString("lastName"),
                userJson.getString("email"),
                userJson.getString("password"),
                userJson.getJsonArray("roles").stream().map(JsonValue::toString).collect(Collectors.toList()));
    }
}
