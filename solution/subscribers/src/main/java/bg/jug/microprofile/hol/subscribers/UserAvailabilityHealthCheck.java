package bg.jug.microprofile.hol.subscribers;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Health
@ApplicationScoped
public class UserAvailabilityHealthCheck implements HealthCheck {

    @Inject
    @ConfigProperty(name = "usersServiceUrl", defaultValue = "http://localhost:9100/users")
    private String usersUrl;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder response = HealthCheckResponse.named("usersAvailable");

        Client client = ClientBuilder.newClient();
        try {
            client.target(usersUrl)
                    .path("all")
                    .request()
                    .get();
            return response.up().build();
        } catch (Exception ce) {
            return response.down().build();
        }

    }
}
