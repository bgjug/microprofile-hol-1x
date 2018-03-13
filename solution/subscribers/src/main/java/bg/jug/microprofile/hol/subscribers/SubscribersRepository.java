package bg.jug.microprofile.hol.subscribers;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metric;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.*;


/**
 * Created by Dmitry Alexandrov on 26.02.2018.
 */
@ApplicationScoped
public class SubscribersRepository {

    private Map<String, Subscriber> subscribers = new HashMap<>();

    public List<Subscriber> getSubscribers() {
        return new ArrayList<>(subscribers.values());
    }

    public Optional<Subscriber> findSubscriberByEmail(String email) {
        return Optional.ofNullable(subscribers.get(email));
    }

    @Metered(name = "Subscriber added")
    public void addSubscriber(Subscriber subscriber) {
        subscribers.put(subscriber.getEmail(), subscriber);
        //used for metrics
        subscribersDBCounter.inc();
    }

    @PostConstruct
    public void addTestData() {
        Subscriber frodoBaggins = new Subscriber("Frodo", "Baggins",
                "frodo@example.org", LocalDate.of(2018, 6, 1));
        Subscriber aragornSonOfAragorn = new Subscriber("Aragorn", "son of Aratorn",
                "aragorn@example.org",  LocalDate.of(2019, 3, 15));
        Subscriber legolas = new Subscriber("Legolas", "son of Thranduil",
                "legolas@example.org",LocalDate.of(2018, 12, 1));

        addSubscriber(frodoBaggins);
        addSubscriber(aragornSonOfAragorn);
        addSubscriber(legolas);
    }

    /* Metrics */

    @Inject
    @Metric
    private Counter subscribersDBCounter;

    //The official workaround
    private void init(@Observes @Initialized(ApplicationScoped.class) Object startEvent) {
        // to trigger instance creation and hence activate the gauge
    }

    @Gauge(name = "Subscribers DB usage", unit = MetricUnits.NONE, absolute = true)
    public int getDBUsage(){
        return subscribers.size();
    }

}
