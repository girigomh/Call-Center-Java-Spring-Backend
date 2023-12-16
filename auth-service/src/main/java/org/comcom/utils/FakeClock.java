package org.comcom.utils;

import java.time.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Controllable clock for testing.
 */
public class FakeClock extends Clock {
    private final AtomicLong millis;
    private final ZoneId zone;

    public FakeClock() {
        this(Instant.parse("2023-01-01T00:00:00Z").toEpochMilli(), ZoneOffset.UTC);
    }

    public FakeClock(long epochMillis, ZoneId zone) {
        this.millis = new AtomicLong(epochMillis);
        this.zone = zone;
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return new FakeClock(millis.get(), zone);
    }

    @Override
    public Instant instant() {
        return Instant.ofEpochMilli(millis.get()).atZone(zone).toInstant();
    }

    public void add(Duration duration) {
        millis.addAndGet(duration.toMillis());
    }

    public void add(Period period) {
        millis.updateAndGet(currentMillis -> {
            Instant instant = Instant.ofEpochMilli(currentMillis).atZone(zone).plus(period).toInstant();
            return instant.toEpochMilli();
        });
    }

    public void add(long amount, TimeUnit unit) {
        millis.addAndGet(TimeUnit.MILLISECONDS.convert(amount, unit));
    }

    @Override
    public long millis() {
        return millis.get();
    }
}
