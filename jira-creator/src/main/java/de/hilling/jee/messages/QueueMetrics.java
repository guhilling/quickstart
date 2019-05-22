package de.hilling.jee.messages;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
@Startup
public class QueueMetrics {

    private final AtomicLong queueLength = new AtomicLong(0);

    public void incQueueLength() {
        queueLength.incrementAndGet();
    }

    public void decrementQueueLength() {
        queueLength.decrementAndGet();
    }

    @Gauge(unit = MetricUnits.NONE, absolute = true)
    public long getQueueLength() {
        return queueLength.get();
    }
}
