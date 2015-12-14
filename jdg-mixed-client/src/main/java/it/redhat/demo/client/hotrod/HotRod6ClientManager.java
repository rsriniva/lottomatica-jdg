package it.redhat.demo.client.hotrod;

import it.redhat.demo.listener.EventLogListener;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
 import it.redhat.demo.client.ProtocolServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@HotRodClient
public class HotRod6ClientManager implements ProtocolServerManager {
	public static final Logger LOGGER = LoggerFactory.getLogger(HotRod6ClientManager.class);

	private static final int DEFAULT_HOTROD_PORT = 11222;

	private static final int DEFAULT_WORKER_THREADS = 160;

	private static final String bindAddress = "127.0.0.1";       
       
	@Inject
	private RemoteCacheManager cacheManager;

	@Override
	public void bootstrapProtocolClient() {
            RemoteCache<String, Object> cache=null;

        boolean done = false;    
  	try {
      	
        cache = cacheManager.getCache("MyCoolCache");
        cache.addClientListener(new EventLogListener());
        System.out.println("Added Listener");

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
		if (cacheManager != null)
                    cacheManager.stop();
	}

  
}
