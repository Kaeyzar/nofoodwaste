package com.projectone.resources.health;

import java.text.DecimalFormat;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;
import jakarta.enterprise.context.ApplicationScoped;

/**
 *  Classes that implement the MicroProfile HealthCheck interface and have appropriate annotations
 *  <code>@Liveness, @Readiness, @Startup</code> will be picked up as health check providers.
 * <p>
 *  It's likely best to make them <code>@Singleton or @ApplicationScoped</code>, unless it is
 *  needed to have a new instance created for each request.
 */
@Liveness
@ApplicationScoped
public class ExampleLivenessCheck implements HealthCheck {

    @ConfigProperty(name = "quarkus.application.name")
    String appName;

    private final DecimalFormat decimalFormat = new DecimalFormat("###,### KB");

    @Override
    public HealthCheckResponse call() {

        final Runtime runtime = Runtime.getRuntime();
        final long totalMemory =  runtime.totalMemory();
        final long freeMemory =  runtime.freeMemory();
        final long maxMemory = runtime.maxMemory();
        final long usedMemory = totalMemory - freeMemory;
        final double usagePercent = (double) usedMemory / (double) maxMemory;

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse
                .named("Liveness of " + appName)
                .withData("memory.total", decimalFormat.format(totalMemory / 1024))
                .withData("memory.free", decimalFormat.format(freeMemory / 1024))
                .withData("memory.max", decimalFormat.format(maxMemory / 1024))
                .withData("memory.usagePercent", "%f2".formatted(usagePercent));

        // The required amount of free memory before the liveliness check fails
        // (and prompts a restart by e.g. Kubernetes) should be set in the application config
        if(usagePercent < 0.9) {
            responseBuilder.up();
        } else {
            responseBuilder.withData("error.message", "Out of Memory");
            responseBuilder.down();
        }

        return responseBuilder.build();
    }
}
