package it.redhat.demo.cache;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
public class InfinispanDemoApplicationCacheManager implements ApplicationCacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfinispanDemoApplicationCacheManager.class);

    private static final String APPLICATION_DEFAULT_CACHE_NAME = "MyCoolCache";

    @Inject
    @LocalCacheContainer
    private CacheContainer cacheContainer;

    public InfinispanDemoApplicationCacheManager() {
    }

    public <K, V> Cache<K, V> getCache() {
        return cacheContainer.getCache(APPLICATION_DEFAULT_CACHE_NAME);
    }

    public <K, V> Cache<K, V> getCache(String cacheName) {
        return cacheContainer.getCache(APPLICATION_DEFAULT_CACHE_NAME);
    }

    public void startCache() {
        getCache().start();
    }

    public void startCache(String cacheName) {
        getCache(cacheName).start();

    }

    public void stopCache() {
        getCache().stop();
    }

    public void stopCache(String cacheName) {
        getCache(cacheName).stop();
    }

    @Override
    public CacheContainer getCacheContainer() {
        return cacheContainer;
    }

    private void addListeners(Cache cache) {
        LOGGER.info("Registering Infinispan EventListeners with Cache: " + cache.getName());

    }

}
