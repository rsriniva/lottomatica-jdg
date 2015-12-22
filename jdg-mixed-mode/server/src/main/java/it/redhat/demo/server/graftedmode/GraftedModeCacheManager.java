package it.redhat.demo.server.graftedmode;

import org.infinispan.Cache;
import org.infinispan.manager.AbstractDelegatingEmbeddedCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class GraftedModeCacheManager extends AbstractDelegatingEmbeddedCacheManager {

    public GraftedModeCacheManager(EmbeddedCacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    public <K, V> Cache<K, V> getCache() {
        return new GraftedModeCache<K, V>(super.<K, V>getCache());
    }

    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) {
        return new GraftedModeCache<K, V>(super.<K, V>getCache(cacheName));
    }

    @Override
    public <K, V> Cache<K, V> getCache(String cacheName, boolean createIfAbsent) {
        return new GraftedModeCache<K, V>(super.<K, V>getCache(cacheName, createIfAbsent));
    }

}
