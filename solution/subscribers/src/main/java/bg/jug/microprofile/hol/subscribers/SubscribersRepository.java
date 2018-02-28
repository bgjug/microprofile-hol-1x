package bg.jug.microprofile.hol.subscribers;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


/**
 * Created by Dmitry Alexandrov on 26.02.2018.
 */
@ApplicationScoped
public class SubscribersRepository {

    private Map<String, Subscriber> subscribers = new HashMap<>();

    public List<Subscriber> getSubscribers() {
        return subscribers.values().stream().collect(Collectors.toList());
    }

    public Optional<Subscriber> findSubscriberByEmail(String email) {
        return Optional.ofNullable(subscribers.get(email));
    }


    public void addSubscriber(Subscriber subscriber) {
        subscribers.put(subscriber.getEmail(), subscriber);
    }


    @PostConstruct
    public void addTestData() {
        Subscriber frodoBaggins = new Subscriber("Frodo", "Baggins",
                "frodo@example.org", "Anfield Road 192", LocalDate.of(2016, 6, 1));
        Subscriber aragornSonOfAragorn = new Subscriber("Aragorn", "son of Aratorn",
                "aragorn@example.org",  "Stamford Bridge 64", LocalDate.of(2017, 3, 15));
        Subscriber legolas = new Subscriber("Legolas", "son of Thranduil",
                "aragorn@example.org","Old Trafford 89", LocalDate.of(2017, 12, 1));

        addSubscriber(frodoBaggins);
        addSubscriber(aragornSonOfAragorn);
        addSubscriber(legolas);
    }
}
