package it.redhat.demo.client.hotrod;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a local CacheContainer in Infinispan 'Library-Mode'.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class HotRodClientFactory implements HotRodFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(HotRodClientFactory.class);
	
 		private static final int DEFAULT_HOTROD_PORT = 11222;

	@Produces @ApplicationScoped @HotRodClient
	public RemoteCacheManager getCacheContainer() {
		RemoteCacheManager cacheManager=null;
		// Retrieve Infinispan config file.
  			try {
				
		     ConfigurationBuilder builder = new ConfigurationBuilder();
                     builder.addServer()
                          .host("127.0.0.1")
                          .port(DEFAULT_HOTROD_PORT);
                     cacheManager = new RemoteCacheManager(builder.build());

			} catch ( Exception ioe) {
				throw new RuntimeException("Error Connecting to Infinispan", ioe);
			}
 		return cacheManager;
	}
 	
 
}
