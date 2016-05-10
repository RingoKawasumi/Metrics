package com.zhujie.study.GettingStarted;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

/**
 * Created by zhujie on 16/5/9.
 */
public class JmxReportSample {
    static final MetricRegistry metrics = new MetricRegistry();

    public static void main(String[] args) {
        startReport();
        Meter requests = metrics.meter("requests");
        requests.mark();
        wait5Seconds();
    }

    static void startReport() {
        JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
        reporter.start();
    }

    static void wait5Seconds() {
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {

        }
    }
}
