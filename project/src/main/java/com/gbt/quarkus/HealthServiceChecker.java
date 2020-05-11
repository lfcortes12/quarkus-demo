package com.gbt.quarkus;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Readiness
@ApplicationScoped
public class HealthServiceChecker implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        try {
            return HealthCheckResponse
                    .named("instance")
                    .withData("ip", InetAddress.getLocalHost().getHostAddress())
                    .up().build();
        } catch (UnknownHostException e) {
            return HealthCheckResponse.down("DOWN");
        }
    }

}
