package bg.jug.microprofile.hol.subscribers;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


/**
 * Created by Dmitry Alexandrov on 26.02.2018.
 */
@ApplicationScoped
public class SubscribersRepository {

    private AtomicLong sequence = new AtomicLong(0);

    private Map<Long, Subscriber> subscribers = new HashMap<>();

    public List<Subscriber> getSubscribers() {
        return subscribers.values().stream().collect(Collectors.toList());
    }

    public Subscriber findSubscriberById(Long subscriberId) {
        return subscribers.get(subscriberId);
    }


    public void addSubscriber(Subscriber subscriber) {
        Long id = sequence.addAndGet(1);
        subscriber.setId(id);
        subscribers.put(id, subscriber);
    }


    @PostConstruct
    public void addTestData() {
        Subscriber johnSmith = new Subscriber("John", "Smith", "johnsmith@example.org", "Anfield Road 192", LocalDate.of(2016, 6, 1));
        Subscriber amandaHirsh = new Subscriber("Amanda", "Hirsh", "amandahirsh@example.org", "Stamford Bridge 64", LocalDate.of(2017, 3, 15));
        Subscriber andrewJameson = new Subscriber("Andrew", "Jameson", "andrew@whiskey.org", "Old Trafford 89", LocalDate.of(2017, 12, 1));
        Subscriber anastaciaBrooks = new Subscriber("Anastacia", "Brooks", "anastaciabrooks@example.org", "Highburry 122", LocalDate.of(2017, 3, 2));
        Subscriber audreyRose = new Subscriber("Audrey", "Rose", "audrey@horror.org", "Goodison Park 41", LocalDate.of(2017, 5, 6));

        addSubscriber(johnSmith);
        addSubscriber(amandaHirsh);
        addSubscriber(andrewJameson);
        addSubscriber(anastaciaBrooks);
        addSubscriber(audreyRose);
    }
}
