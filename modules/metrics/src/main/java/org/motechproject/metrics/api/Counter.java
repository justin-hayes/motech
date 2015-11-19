package org.motechproject.metrics.api;

public interface Counter extends Counting{
    void inc();
    void inc(long n);
    void dec();
    void dec(long n);
}
