package bg.jug.microprofile.hol.subscribers;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class SubscribersListFullCheck implements HealthCheck {

    @Inject
    private SubscribersRepository subscribersRepository;

    @Inject
    @ConfigProperty(name = "maxSubscribers", defaultValue = "10")
    private int maximumSubscribers;


    @Override
    public HealthCheckResponse call() {
        int numberOfSubscribers = subscribersRepository.getSubscribers().size();
        return HealthCheckResponse.named("subscribersListFull")
                .withData("Number of subscribers", numberOfSubscribers)
                .withData("Maximum subscriber", maximumSubscribers)
                .state(numberOfSubscribers <= maximumSubscribers)
                .build();
    }
}
