package com.zhujie.study.GettingStarted;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhujie on 15/11/2.
 */
public class GetStarted {

    static final MetricRegistry metrics = new MetricRegistry();

    static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void wait5Seconds() {
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {

        }
    }

    public static class QueueManager {
        private final Queue queue;

        public QueueManager(MetricRegistry metrics, String name) {
            this.queue = new ArrayBlockingQueue(10);
            metrics.register(MetricRegistry.name(QueueManager.class, name, "size"),
                    new Gauge<Integer>() {
                        @Override
                        public Integer getValue() {
                            return queue.size();
                        }
                    });
        }
    }

    public static class UnitTest {

        @Test
        public void testMeter() {
            startReport();
            Meter requests = metrics.meter("requests");
            requests.mark();
            wait5Seconds();
        }

        @Test
        public void testCounters() {

        }
    }

}
