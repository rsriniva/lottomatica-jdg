package org.jboss.ddoyle.infinispan.demo.server.hotrod;

import com.redhat.listener.EventLogListener;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

import org.infinispan.server.core.transport.Transport;
 import org.jboss.ddoyle.infinispan.demo.server.ProtocolServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@HotRodClient
public class HotRod6ClientManager implements ProtocolServerManager {
	public static final Logger LOGGER = LoggerFactory.getLogger(HotRod6ClientManager.class);

	private static final int DEFAULT_HOTROD_PORT = 11222;

	private static final int DEFAULT_WORKER_THREADS = 160;

	private static final String bindAddress = "127.0.0.1";

	private static final String ISPN_HOTROD_PROXY_HOST = "infinispan.hotrod.proxy.host";

	private static final String ISPN_HOTROD_PROXY_PORT = "infinispan.hotrod.proxy.port";

 
 
           
       
	@Inject
	private RemoteCacheManager cacheManager;

	@Override
	public void bootstrapProtocolClient() {
            RemoteCache<String, Object> cache=null;

/*
		LOGGER.info("Starting the HotRodServer.");

	
		try {
			// Configure the configuration builder.
	     ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.addServer()
                .host(bindAddress)
                .port(DEFAULT_HOTROD_PORT);
        cacheManager = new RemoteCacheManager(builder.build());
 */
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
 

	/*
	 * private HotRodServerConfigurationBuilder setConverter(HotRodServerConfigurationBuilder builder) { return
	 * builder.converterFactory("static-converter", new StaticConverterFactory()); }
	 */
/*
	private void startProtocolServer(ProtocolServerConfiguration configuration) {
              	
	}
*/
	@Override
	public synchronized void stop() {
		if (cacheManager != null)
                    cacheManager.stop();
	}

  
}
