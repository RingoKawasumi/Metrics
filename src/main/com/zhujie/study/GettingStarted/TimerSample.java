package com.zhujie.study.GettingStarted;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhujie on 16/5/9.
 */
public class TimerSample {
    private static final MetricRegistry metrics = new MetricRegistry();

    private static final Timer responses = metrics.timer(MetricRegistry.name(TimerSample.class, "responses"));

    public static void main(String[] args) {
        startReport();
        Timer.Context context = responses.time();
        try {
            System.out.println("ok");
            waitSeconds();
        } finally {
            context.stop();
        }
    }

    static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void waitSeconds() {
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {

        }
    }
}
