package de.hilling.jee.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;

@Health()
@ApplicationScoped
public class Checker implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("Basic")
                                  .withData("connections", 2)
                                  .up()
                                  .build();
    }
}
