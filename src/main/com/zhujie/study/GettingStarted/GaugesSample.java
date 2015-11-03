package com.zhujie.study.GettingStarted;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by zhujie on 15/11/3.
 */
public class GaugesSample {

    static final MetricRegistry metrics = new MetricRegistry();

    public static void main(String[] args) {
        new QueueManager(metrics, "jobs");
        wait5Seconds();
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
}
