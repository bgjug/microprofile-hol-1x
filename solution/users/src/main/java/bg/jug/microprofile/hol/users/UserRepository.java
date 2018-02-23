package bg.jug.microprofile.hol.users;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class UserRepository {

    private Map<String, User> users = new ConcurrentHashMap<>();

    @PostConstruct
    public void initUsers() {
        users.put("bilbo@example.org", new User("Bilbo", "Baggins",
                "bilbo@example.org", "bilbo123", Collections.singletonList("admin")));
        users.put("frodo@example.org", new User("Frodo", "Baggins",
                "frodo@example.org", "frodo123", Arrays.asList("author", "subscriber")));
        users.put("gandalf@example.org", new User("Gandalf", "the Grey",
                "gandalf@example.org", "gandalf123", Collections.singletonList("author")));
        users.put("aragorn@example.org", new User("Aragorn", "son of Aratorn",
                "aragorn@example.org", "aragorn123", Collections.singletonList("subscriber")));
        users.put("legolas@example.org", new User("Legolas", "son of Thranduil",
                "aragorn@example.org", "aragorn123", Collections.singletonList("subscriber")));
        users.put("gimli@example.org", new User("Gimli", "son of Gloin",
                "gimli@example.org", "gimli123", Collections.emptyList()));
    }

    public Optional<User> findByLoginDetails(String email, String password) {
        User user = users.get(email);
        if (user == null || !user.getPassword().equals(password)) {
            return Optional.empty();
        } else {
            return Optional.of(user);
        }
    }
}
