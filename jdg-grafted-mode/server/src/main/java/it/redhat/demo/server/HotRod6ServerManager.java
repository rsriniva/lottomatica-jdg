package it.redhat.demo.server;

import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.server.core.transport.Transport;
import org.infinispan.server.hotrod.HotRodServer;
import org.infinispan.server.hotrod.configuration.HotRodServerConfiguration;
import org.infinispan.server.hotrod.configuration.HotRodServerConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Singleton
@Startup
public class HotRod6ServerManager {

    public static final Logger log = LoggerFactory.getLogger(HotRod6ServerManager.class);

    private static final String DEFAULT_HOTROD_BIND_ADDRESS = "127.0.0.1";

    private static final String DEFAULT_HOTROD_PORT = "11222";

    private static final int DEFAULT_WORKER_THREADS = 160;

    private static final String ISPN_HOTROD_PROXY_HOST = "infinispan.hotrod.proxy.host";

    private static final String ISPN_HOTROD_PROXY_PORT = "infinispan.hotrod.proxy.port";

    private HotRodServer hotRodServer;

    @Inject
    @LocalCacheContainer
    private EmbeddedCacheManager cacheManager;

    @PreDestroy
    public void doStop() {
        try {
            if (hotRodServer != null) {
                log.info("HotRodServer connector is stopping.");
                hotRodServer.stop();
                Thread.currentThread().wait(SECONDS.convert(15, MILLISECONDS));
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.warn("Failed to stop the HotRodServer connector.");
        } finally {
            log.info("Stopped HotRodServer connector.");
            hotRodServer = null;
        }

    }

    @PostConstruct
    public void doStart() {
        log.info("Starting the HotRodServer.");

        try {
            HotRodServerConfiguration hotRodServerConfiguration = buildConfiguration().build();

            hotRodServer = new HotRodServer();

            // Start the server and record it
            hotRodServer.start(hotRodServerConfiguration, cacheManager);

            // Add Converter factories.
            // Seems not needed anymore in ISPN 7.0.0.CR1s
//            hotRodServer.addCacheEventConverterFactory("static-converter", new StaticCacheEventConverterFactory());

            Transport transport = hotRodServer.transport();

            log.info("HotRodServer connector started on host '" + transport.getHostName() + "' and port '" + transport.getPort() + "'.");
        } catch (Exception e) {
            log.error("Failed to start the HotRodServer.", e);
            throw new RuntimeException(e);
        }
    }

    private HotRodServerConfigurationBuilder buildConfiguration() {
        HotRodServerConfigurationBuilder builder = new HotRodServerConfigurationBuilder();

        String host = System.getProperty(ISPN_HOTROD_PROXY_HOST, DEFAULT_HOTROD_BIND_ADDRESS);
        try {
            Integer port = Integer.parseInt(System.getProperty(ISPN_HOTROD_PROXY_PORT, DEFAULT_HOTROD_PORT));
            builder.host(host).port(port);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Infinspan Proxy portNumber should be an integer.", nfe);
        }

        builder.workerThreads(DEFAULT_WORKER_THREADS)
                .idleTimeout(10000)
                .tcpNoDelay(true)
                .sendBufSize(10000)
                .recvBufSize(10000);

        builder.topologyLockTimeout(1000L);
        builder.topologyReplTimeout(5000L);

        return builder;

    }

}
