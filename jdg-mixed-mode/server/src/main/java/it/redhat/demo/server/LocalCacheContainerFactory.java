package it.redhat.demo.server;

import it.redhat.demo.server.graftedmode.GraftedModeCacheManager;
import org.infinispan.commons.util.Util;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.io.InputStream;

public class LocalCacheContainerFactory {

    private static final Logger log = LoggerFactory.getLogger(LocalCacheContainerFactory.class);

    private static final String INFINISPAN_CONFIG_FILE_NAME = "infinispan.xml";

    @Produces
    @ApplicationScoped
    @LocalCacheContainer
    public EmbeddedCacheManager createCacheManager() {
        log.info("Start embedded cache manager");
        InputStream configurationIS = null;
        try {
            configurationIS = this.getClass().getClassLoader().getResourceAsStream(INFINISPAN_CONFIG_FILE_NAME);
            return new GraftedModeCacheManager(new DefaultCacheManager(configurationIS, true));
        } catch (IOException ioe) {
            throw new RuntimeException("Error loading Infinispan CacheManager.", ioe);
        } finally {
            if (configurationIS != null) {
                Util.close(configurationIS);
            }
        }
    }

    public void disposeCacheManager(@Disposes @LocalCacheContainer EmbeddedCacheManager cacheContainer) {
        log.info("Stop embedded cache manager");
        cacheContainer.stop();
    }

}