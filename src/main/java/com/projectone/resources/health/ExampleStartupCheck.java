package com.projectone.resources.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Startup;
import jakarta.enterprise.context.ApplicationScoped;

/**
 *  Classes that implement the MicroProfile HealthCheck interface and have appropriate annotations
 *  <code>@Liveness, @Readiness, @Startup</code> will be picked up as health check providers.
 * <p>
 *  It's likely best to make them <code>@Singleton or @ApplicationScoped</code>, unless it is
 *  needed to have a new instance created for each request.
 */
@Startup
@ApplicationScoped
public class ExampleStartupCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.up("Startup successful.");
    }
}
