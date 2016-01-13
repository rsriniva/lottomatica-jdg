package it.redhat.demo.client;

import it.redhat.demo.listener.EventLogListener;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class HotRod6ClientManager {

    public static final Logger log = LoggerFactory.getLogger(HotRod6ClientManager.class);

    @Inject
    @HotRodClient
    private RemoteCacheManager cacheManager;

    @PostConstruct
    public void doStart() {
        try {
            RemoteCache<String, Object> cache = cacheManager.getCache("MyCoolCache");
            EventLogListener listener = new EventLogListener(cache);
            cache.addClientListener(listener);
            log.info("Added Listener");
        } catch (Exception e) {
            log.error("Failed to start the HotRodServer.", e);
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void stop() {
        if (cacheManager != null) {
            RemoteCache<Object, Object> cache = cacheManager.getCache("MyCoolCache");
            for (Object listener : cache.getListeners()) {
                cache.removeClientListener(listener);
            }
            cacheManager.stop();
        }
    }

}
