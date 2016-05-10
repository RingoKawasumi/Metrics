package com.zhujie.study.GettingStarted;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhujie on 16/5/9.
 */
public class CounterSample {
    private static final MetricRegistry metrics = new MetricRegistry();

    private static final Counter pendingJobs = metrics.counter(MetricRegistry.name(CounterSample.class, "pending-jobs"));

    private static Queue<Job> queue = new ArrayBlockingQueue(20);

    public static void main(String[] args) {
        startReport();
        addJob(new Job());
        waitSeconds();
        addJob(new Job());
        waitSeconds();
        takeJob();
        waitSeconds();
    }

    public static void addJob(Job job) {
        pendingJobs.inc();
        queue.offer(job);
    }

    public static Job takeJob() {
        pendingJobs.dec();
        return queue.poll();
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

    private static class Job {
        private String name;

    }


}
