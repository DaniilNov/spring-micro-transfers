package ru.otus.java.pro.mt.core.transfers.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CustomMetricsService {
    private final Counter customMetricCounter;
    private final AtomicInteger customMetricGauge;
    private final Counter receivedTransferRequestsCounter;
    private final Counter successfulTransfersCounter;
    private final Counter failedTransfersCounter;

    public CustomMetricsService(MeterRegistry meterRegistry) {
        customMetricCounter = Counter.builder("custom_metric_name")
                .description("Description of custom metric")
                .tags("environment", "development")
                .register(meterRegistry);

        customMetricGauge = meterRegistry.gauge("custom_gauge", new AtomicInteger(0));

        receivedTransferRequestsCounter = Counter.builder("received_transfer_requests")
                .description("Number of received transfer requests")
                .register(meterRegistry);

        successfulTransfersCounter = Counter.builder("successful_transfers")
                .description("Number of successful transfers")
                .register(meterRegistry);

        failedTransfersCounter = Counter.builder("failed_transfers")
                .description("Number of failed transfers")
                .register(meterRegistry);
    }

    public void incrementCustomMetric() {
        customMetricCounter.increment();
    }

    public void changeCustomGauge() {
        customMetricGauge.set((int)(Math.random() * 1000));
    }

    public void incrementReceivedTransferRequests() {
        receivedTransferRequestsCounter.increment();
    }

    public void incrementSuccessfulTransfers() {
        successfulTransfersCounter.increment();
    }

    public void incrementFailedTransfers() {
        failedTransfersCounter.increment();
    }
}
