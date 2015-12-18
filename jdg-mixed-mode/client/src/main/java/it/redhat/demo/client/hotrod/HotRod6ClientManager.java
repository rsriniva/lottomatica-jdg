package it.redhat.demo.client.hotrod;

import it.redhat.demo.client.ProtocolServerManager;
import it.redhat.demo.listener.EventLogListener;
import it.redhat.demo.model.CacheNode;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@HotRodClient
public class HotRod6ClientManager implements ProtocolServerManager {

    public static final Logger LOGGER = LoggerFactory.getLogger(HotRod6ClientManager.class);

    @Inject
    private RemoteCacheManager cacheManager;

    @Override
    public synchronized void bootstrapProtocolClient() {
        boolean done = false;
        try {
            RemoteCache<String, CacheNode> cache = cacheManager.getCache("MyCoolCache");
            EventLogListener listener = new EventLogListener(cache);
//            EventLogListener listener = new EventLogListener();
            cache.addClientListener(listener);
            LOGGER.info("Added Listener");

            done = true;
        } catch (Exception e) {
            String message = "Failed to start the HotRodServer.";
            LOGGER.error(message, e);
            throw new RuntimeException(message, e);
        } finally {
            if (!done) {
                stop();
            }
        }
    }

    @Override
    public synchronized void stop() {
        if (cacheManager != null) {
            cacheManager.stop();
        }
    }

}
