package it.redhat.demo.server;

import org.infinispan.server.core.ProtocolServer;

/**
 * Manages various aspects of the {@link ProtocolServer}, including configuration, lifecycle, etc.
 *
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public interface ProtocolServerManager {

    void bootstrapProtocolServer();

    void stop();

}
