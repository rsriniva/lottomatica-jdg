package it.redhat.demo.server.graftedmode;

import org.infinispan.AbstractDelegatingCache;
import org.infinispan.Cache;
import org.infinispan.container.versioning.NumericVersion;
import org.infinispan.metadata.EmbeddedMetadata;
import org.infinispan.metadata.Metadata;
import org.infinispan.util.concurrent.NotifyingFuture;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GraftedModeCache<K, V> extends AbstractDelegatingCache<K, V> {

    public GraftedModeCache(Cache<K, V> cache) {
        super(cache);
    }

    @Override
    public V put(K key, V value, long lifespan, TimeUnit lifespanUnit) {
        return this.getAdvancedCache().put(key, value, createMetadata(lifespan, lifespanUnit));
    }

    @Override
    public V putIfAbsent(K key, V value, long lifespan, TimeUnit lifespanUnit) {
        return this.getAdvancedCache().putIfAbsent(key, value, createMetadata(lifespan, lifespanUnit));
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map, long lifespan, TimeUnit lifespanUnit) {
        this.getAdvancedCache().putAll(map, createMetadata(lifespan, lifespanUnit));
    }

    @Override
    public V put(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return this.getAdvancedCache().put(key, value, createMetadata(lifespan, lifespanUnit, maxIdleTime, maxIdleTimeUnit));
    }

    @Override
    public V putIfAbsent(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return this.getAdvancedCache().putIfAbsent(key, value, createMetadata(lifespan, lifespanUnit, maxIdleTime, maxIdleTimeUnit));
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        this.getAdvancedCache().putAll(map, createMetadata(lifespan, lifespanUnit, maxIdleTime, maxIdleTimeUnit));
    }

    @Override
    public NotifyingFuture<V> putAsync(K key, V value) {
        return this.getAdvancedCache().putAsync(key, value, createMetadata());
    }

    @Override
    public NotifyingFuture<V> putAsync(K key, V value, long lifespan, TimeUnit lifespanUnit) {
        return this.getAdvancedCache().putAsync(key, value, createMetadata(lifespan, lifespanUnit));
    }

    @Override
    public NotifyingFuture<V> putAsync(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return this.getAdvancedCache().putAsync(key, value, createMetadata(lifespan, lifespanUnit, maxIdle, maxIdleUnit));
    }

    @Override
    public V put(K key, V value) {
        return this.getAdvancedCache().put(key, value, createMetadata());
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        this.getAdvancedCache().putAll(map, createMetadata());
    }

    @Override
    public void putForExternalRead(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        this.getAdvancedCache().putForExternalRead(key, value, createMetadata(lifespan, lifespanUnit, maxIdle, maxIdleUnit));
    }

    @Override
    public void putForExternalRead(K key, V value, long lifespan, TimeUnit lifespanUnit) {
        this.getAdvancedCache().putForExternalRead(key, value, createMetadata(lifespan, lifespanUnit));
    }

    @Override
    public void putForExternalRead(K key, V value) {
        this.getAdvancedCache().putForExternalRead(key, value, createMetadata());
    }

    @Override
    public NotifyingFuture<Void> putAllAsync(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException("Metadata not supported");
    }

    @Override
    public NotifyingFuture<Void> putAllAsync(Map<? extends K, ? extends V> data, long lifespan, TimeUnit unit) {
        throw new UnsupportedOperationException("Metadata not supported");
    }

    @Override
    public NotifyingFuture<Void> putAllAsync(Map<? extends K, ? extends V> data, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        throw new UnsupportedOperationException("Metadata not supported");
    }

    @Override
    public NotifyingFuture<V> putIfAbsentAsync(K key, V value) {
        throw new UnsupportedOperationException("Metadata not supported");
    }

    @Override
    public NotifyingFuture<V> putIfAbsentAsync(K key, V value, long lifespan, TimeUnit unit) {
        throw new UnsupportedOperationException("Metadata not supported");
    }

    @Override
    public NotifyingFuture<V> putIfAbsentAsync(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        throw new UnsupportedOperationException("Metadata not supported");
    }


    private Metadata createMetadata() {
        return createMetadata(null, null, null, null);
    }

    private Metadata createMetadata(long lifespan, TimeUnit lifespanUnit) {
        return createMetadata(lifespan, lifespanUnit, null, null);
    }

    private Metadata createMetadata(Long lifespan, TimeUnit lifespanUnit, Long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        EmbeddedMetadata.Builder mb = new EmbeddedMetadata.Builder();
        if (lifespan != null) {
            mb.lifespan(lifespan, lifespanUnit);
        }
        if (maxIdleTime != null) {
            mb.maxIdle(maxIdleTime, maxIdleTimeUnit);
        }
        mb.version(new NumericVersion(0));
        return mb.build();
    }
}
