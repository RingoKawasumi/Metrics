package com.zhujie.study.GettingStarted;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhujie on 16/5/9.
 */
public class QueueManager {

    private static final MetricRegistry metrics = new MetricRegistry();

    private final Queue queue;

    public QueueManager(String name) {
        this.queue = new ArrayBlockingQueue(20);
        metrics.register(MetricRegistry.name(QueueManager.class, name, "size"),
                new Gauge<Integer>() {
                    @Override
                    public Integer getValue() {
                        return queue.size();
                    }
                });
    }

    public static void main(String[] args) {
        QueueManager queueManager = new QueueManager("request");
        startReport();
        queueManager.queue.add("a");
        wait1Seconds();
        queueManager.queue.add("b");
        wait1Seconds();
        queueManager.queue.add("c");
        wait1Seconds();
    }

    static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void wait1Seconds() {
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {

        }
    }
}
