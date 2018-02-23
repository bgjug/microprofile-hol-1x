package bg.jug.microprofile.hol.gui;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;

    private User() {}

    private User(String firstName, String lastName, String email, List<String> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

    public List<String> getRoles() {
        return roles;
    }

    public static User fromJson(JsonObject userJson) {
        List<String> roles = new ArrayList<>();
        userJson.getJsonArray("roles").forEach(v -> roles.add(v.toString()));
        return new User(userJson.getString("firstName"), userJson.getString("lastName"),
                userJson.getString("email"), roles);
    }
}
