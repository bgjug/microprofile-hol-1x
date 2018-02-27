package bg.jug.microprofile.hol.authors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by Dmitry Alexandrov on 27.02.18.
 */
@ApplicationScoped
public class AuthorsRepository {

    private AtomicLong sequence = new AtomicLong(0);

    private Map<Long,Author> authors = new HashMap<>();

    public List<Author> getAuthors(){
        return new ArrayList<>(authors.values());
    }

    public Author findAuthorById(Long authorId){
        return authors.get(authorId);
    }

    public List<Author> findAuthorByNames(String names){
        return authors.values().stream().filter(e->(e.getFirstName()+" "+e.getLastName()).contains(names)).collect(Collectors.toList());
    }

    public void addAuthor(Author author){
        Long authorId = sequence.addAndGet(1);
        author.setId(authorId);

        authors.put(authorId,author);
    }
    @PostConstruct
    public void addTestData() {
        Author bilboBaggins = new Author("Bilbo", "Baggins", "bilbo@shire.com", true, 1000);
        Author spiderman = new Author("Spider", "Man", "spiderman@comics.com", false, 860);
        Author captainPower = new Author("Captain", "Power", "power@futuresoldiers.com", true, 750);

        addAuthor(bilboBaggins);
        addAuthor(spiderman);
        addAuthor(captainPower);
    }

}
