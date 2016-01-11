package it.redhat.demo.marshall;

import it.redhat.demo.model.Pojo;
import org.infinispan.Cache;
import org.infinispan.commons.io.ByteBuffer;
import org.infinispan.commons.io.ByteBufferImpl;
import org.infinispan.commons.marshall.AbstractMarshaller;
import org.infinispan.factories.annotations.Inject;
import org.infinispan.factories.annotations.Start;
import org.infinispan.factories.scopes.Scope;
import org.infinispan.factories.scopes.Scopes;
import org.infinispan.manager.CacheContainer;
import org.infinispan.protostream.ProtobufUtil;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.protostream.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ProtoMarshaller extends AbstractMarshaller {

    private static final Logger log = LoggerFactory.getLogger(ProtoMarshaller.class);

    private static final SerializationContext serializationContext = ProtobufUtil.newSerializationContext(new Configuration.Builder().build());

    private static final String PROTO_SCHEMA_NAME = "twb.proto";
    private static final String PROTO_CACHE_NAME_METADATA = "___protobuf_metadata";

    public void registerProto(CacheContainer cacheContainer) {
        try {
            ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder()
                    .fileName(PROTO_SCHEMA_NAME)
                    .packageName(Pojo.class.getPackage().getName())
                    .addClass(Pojo.class);

            String cacheName = PROTO_CACHE_NAME_METADATA;
            Cache<String, String> metadataCache = cacheContainer.getCache(cacheName);
            metadataCache.put(PROTO_SCHEMA_NAME, protoSchemaBuilder.build(serializationContext));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object objectFromByteBuffer(byte[] buf, int offset, int length) throws IOException, ClassNotFoundException {
        return ProtobufUtil.fromWrappedByteArray(this.getSerializationContext(), buf, offset, length);
    }

    public boolean isMarshallable(Object o) {
        return !(o instanceof String) && !(o instanceof Long) && !(o instanceof Integer) && !(o instanceof Double) && !(o instanceof Float) && !(o instanceof Boolean) && !(o instanceof byte[]) ? this.getSerializationContext().canMarshall(o.getClass()) : true;
    }

    protected ByteBuffer objectToBuffer(Object o, int estimatedSize) throws IOException, InterruptedException {
        byte[] bytes = ProtobufUtil.toWrappedByteArray(this.getSerializationContext(), o);
        return new ByteBufferImpl(bytes, 0, bytes.length);
    }

    public SerializationContext getSerializationContext() {
        return serializationContext;
    }
}
