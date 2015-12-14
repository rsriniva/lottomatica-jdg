package it.redhat.demo.web.listener;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

 import it.redhat.demo.client.ProtocolServerManager;
//import org.jboss.ddoyle.infinispan.demo.server.hotrod.HotRod;
import it.redhat.demo.client.hotrod.HotRodClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application Lifecycle Listener implementation class CacheStartListener
 * 
 */

@WebListener
public class JdgStartListener implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(JdgStartListener.class);
	
	//@Inject
	//private ApplicationCacheManager cacheManager;
	
	@Inject
	@HotRodClient
	private ProtocolServerManager protocolServerManager;
	
	//@Inject
	//private CacheStatisticsMBean cacheStatistics;
	
	//TODO: Make this configurable or remove this option.
	private boolean startCachesOnStartup = true;
	
	/**
	 * Default constructor.
	 */
	public JdgStartListener() {
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent scEvent) {
		//Fire up the Hotrod Listener
		protocolServerManager.bootstrapProtocolClient();
	}
	

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		LOGGER.info("Stopping the HotRod server.");
		protocolServerManager.stop();
	}

}
