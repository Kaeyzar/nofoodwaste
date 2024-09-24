package com.nofoodwaste.resources.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;
import jakarta.enterprise.context.ApplicationScoped;

/**
 *  Classes that implement the MicroProfile HealthCheck interface and have appropriate annotations
 *  <code>@Liveness, @Readiness, @Startup</code> will be picked up as health check providers.
 * <p>
 *  It's likely best to make them <code>@Singleton or @ApplicationScoped</code>, unless it is
 *  needed to have a new instance created for each request.
 */
@Readiness
@ApplicationScoped
public class ExampleReadinessCheck implements HealthCheck {


    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder healthCheckResponseBuilder =  HealthCheckResponse.named(
                "There is no database yet, but this is where we'd check for it's availability.");

        // Do a database connection check
        healthCheckResponseBuilder.up();

        return healthCheckResponseBuilder.build();
    }
}
