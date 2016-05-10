package com.zhujie.study.GettingStarted;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhujie on 16/5/9.
 */
public class HistogramSample {
    private static final MetricRegistry metrics = new MetricRegistry();

    private static final Histogram responseSizes = metrics.histogram(MetricRegistry.name(HistogramSample.class, "response-sizes"));

    public static void main(String[] args) {
        startReport();
        responseSizes.update(new Random().nextInt(100));
        waitSeconds();
        responseSizes.update(new Random().nextInt(100));
        waitSeconds();
        responseSizes.update(new Random().nextInt(100));
        waitSeconds();
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
