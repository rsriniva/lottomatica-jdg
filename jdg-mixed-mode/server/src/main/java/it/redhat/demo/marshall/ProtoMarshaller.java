package it.redhat.demo.marshall;

import it.redhat.demo.model.Pojo;
import org.infinispan.commons.io.ByteBuffer;
import org.infinispan.commons.io.ByteBufferImpl;
import org.infinispan.commons.marshall.AbstractMarshaller;
import org.infinispan.protostream.ProtobufUtil;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.protostream.config.Configuration;

import java.io.IOException;

public class ProtoMarshaller extends AbstractMarshaller {

    private static final SerializationContext serializationContext = ProtobufUtil.newSerializationContext(new Configuration.Builder().build());

    public ProtoMarshaller() throws IOException {
        ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
        protoSchemaBuilder.fileName("twb.proto")
                .packageName("it.redhat.demo.model")
                .addClass(Pojo.class)
//                .addClass(CacheNode.class)
//                .addClass(CacheEdge.class)
//                .addClass(TreeCacheDTO.class)
                .build(serializationContext);
    }

    public Object objectFromByteBuffer(byte[] buf, int offset, int length) throws IOException, ClassNotFoundException {
        return ProtobufUtil.fromWrappedByteArray(serializationContext, buf, offset, length);
    }

    public boolean isMarshallable(Object o) {
        return !(o instanceof String) && !(o instanceof Long) && !(o instanceof Integer) && !(o instanceof Double) && !(o instanceof Float) && !(o instanceof Boolean) && !(o instanceof byte[]) ? this.serializationContext.canMarshall(o.getClass()) : true;
    }

    protected ByteBuffer objectToBuffer(Object o, int estimatedSize) throws IOException, InterruptedException {
        byte[] bytes = ProtobufUtil.toWrappedByteArray(serializationContext, o);
        return new ByteBufferImpl(bytes, 0, bytes.length);
    }
}
