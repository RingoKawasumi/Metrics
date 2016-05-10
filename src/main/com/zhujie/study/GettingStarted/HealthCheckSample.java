package com.zhujie.study.GettingStarted;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

import java.util.Map;
import java.util.Random;

/**
 * Created by zhujie on 16/5/9.
 */
public class HealthCheckSample {

    private static final HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();

    public static void main(String[] args) {
        healthCheckRegistry.register("postgres", new DatabaseHealthCheck());
        final Map<String, HealthCheck.Result> results = healthCheckRegistry.runHealthChecks();
        for (Map.Entry<String, HealthCheck.Result> entry : results.entrySet()) {
            if (entry.getValue().isHealthy()) {
                System.out.println(entry.getKey() + " is healthy");
            } else {
                System.err.println(entry.getKey() + " is UNHEALTHY: " + entry.getValue().getMessage());
                final Throwable e = entry.getValue().getError();
                if (e != null) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class DatabaseHealthCheck extends HealthCheck {

        @Override
        protected Result check() throws Exception {
            if (new Random().nextBoolean()) {
                return HealthCheck.Result.healthy();
            } else {
                return HealthCheck.Result.unhealthy("Cannot connect to database");
            }
        }
    }
}
