package it.redhat.demo.client;


import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.configuration.NearCacheConfiguration;
import org.infinispan.client.hotrod.configuration.NearCacheConfigurationBuilder;
import org.infinispan.client.hotrod.configuration.NearCacheMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class HotRodClientFactory {

    private static final Logger log = LoggerFactory.getLogger(HotRodClientFactory.class);

    private static final String DEFAULT_HOTROD_BIND_ADDRESS = "127.0.0.1";
    private static final String DEFAULT_HOTROD_PORT = "11222";
    private static final String ISPN_HOTROD_PROXY_HOST = "infinispan.hotrod.client.host";
    private static final String ISPN_HOTROD_PROXY_PORT = "infinispan.hotrod.client.port";

    @ApplicationScoped
    @Produces
    @HotRodClient
    public RemoteCacheManager getCacheContainer() {
        log.info("Create remote cache manager");
        String host = System.getProperty(ISPN_HOTROD_PROXY_HOST, DEFAULT_HOTROD_BIND_ADDRESS);
        try {
            Integer port = Integer.parseInt(System.getProperty(ISPN_HOTROD_PROXY_PORT, DEFAULT_HOTROD_PORT));

            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.nearCache().mode(NearCacheMode.LAZY);
            builder.nearCache().maxEntries(100);

//            ProtoStreamMarshaller mm = new ProtoStreamMarshaller();
//            builder.marshaller(mm);

            builder.addServer()
                    .host(host)
                    .port(port);
            RemoteCacheManager remoteCacheManager = new RemoteCacheManager(builder.build());

//            SerializationContext serCtx = mm.getSerializationContext();
//            configureProtobufMarshaller(serCtx);

            return remoteCacheManager;
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Infinspan Proxy portNumber should be an integer.", nfe);
        } catch (Exception ioe) {
            throw new RuntimeException("Error Connecting to Infinispan", ioe);
        }
    }

/*
    private void configureProtobufMarshaller(RemoteCacheManager remoteCacheManager, SerializationContext serCtx) {
        ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
        String generatedSchema;
        try {
            generatedSchema = protoSchemaBuilder.fileName("twb.proto")
                    .packageName("it.redhat.demo.model")
                    .addClass(Pojo.class)
                    .build(serCtx);

            String cacheName = ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME;
            RemoteCache<String, String> metadataCache = remoteCacheManager.getCache(cacheName);
            metadataCache.put("twb.proto", generatedSchema);

            String errors = metadataCache.get(ProtobufMetadataManagerConstants.ERRORS_KEY_SUFFIX);
            if (errors != null) {
                throw new IllegalStateException("Some Protobuf schema files contain errors:\n" + errors);
            }
        } catch (Exception e) {
            throw new IllegalStateException("An error occurred initializing HotRod protobuf marshaller:", e);
        }
    }
*/
}
