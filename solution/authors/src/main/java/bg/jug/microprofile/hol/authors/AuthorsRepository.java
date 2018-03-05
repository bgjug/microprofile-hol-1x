package bg.jug.microprofile.hol.authors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by Dmitry Alexandrov on 27.02.18.
 */
@ApplicationScoped
public class AuthorsRepository {

    private Map<String,Author> authors = new HashMap<>();

    public List<Author> getAuthors(){
        return new ArrayList<>(authors.values());
    }

    public Optional<Author> findAuthorByEmail(String email){
        return Optional.ofNullable(authors.get(email));
    }

    public void addAuthor(Author author){
        authors.put(author.getEmail(),author);
    }
    @PostConstruct
    public void addTestData() {
        Author gandalf = new Author("Gandalf", "the Grey", "gandalf@example.org",
                "very very old!", 1000);
        Author frodo = new Author("Frodo", "Baggins", "frodo@example.org",
                "don't touch my precious!", 750);
        addAuthor(gandalf);
        addAuthor(frodo);
    }

}
