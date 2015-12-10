package it.redhat.demo.client.hotrod;

import com.bp.ist.dps.common.model.Deal;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.protostream.annotations.ProtoSchemaBuilderException;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

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
  RemoteCacheManager cacheManager = null;
    @Produces
    @ApplicationScoped
    @HotRodClient
    public RemoteCacheManager getCacheContainer() {
      
        // Retrieve Infinispan config file.
        try {
            ProtoStreamMarshaller mm = new ProtoStreamMarshaller();

            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.addServer()
                    .host("127.0.0.1")
                    .port(DEFAULT_HOTROD_PORT).marshaller(mm);

            cacheManager = new RemoteCacheManager(builder.build()); 
            
           SerializationContext serCtx = ProtoStreamMarshaller
               .getSerializationContext(cacheManager);
       configureProtobufMarshaller(serCtx);


        } catch (Exception ioe) {
            throw new RuntimeException("Error Connecting to Infinispan", ioe);
        }
        return cacheManager;
    }
  private void configureProtobufMarshaller(SerializationContext serCtx) {
       // generate and register a Protobuf schema and marshallers based
       // on annotated classes
       ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
       String generatedSchema;
       try {
           // generate the 'twb.proto' schema file based on the annotations on
           // passed classes and register it with the SerializationContext of the
           // client
           generatedSchema = protoSchemaBuilder.fileName("twb.proto")
                   .packageName("it.redhat.demo.model")
                   .addClass(Deal.class)
                   .build(serCtx);

           // register the schemas with the server too
           RemoteCache<String, String> metadataCache = cacheManager
                   .getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
           metadataCache.put("twb.proto", generatedSchema);

           String errors = metadataCache.get(ProtobufMetadataManagerConstants.ERRORS_KEY_SUFFIX);
           if (errors != null) {
               throw new IllegalStateException("Some Protobuf schema files contain errors:\n"+ errors);
           }

       } catch ( Exception e) {
           throw new IllegalStateException( "An error occurred initializing HotRod protobuf marshaller:", e);
       }
   }
}
