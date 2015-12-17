package it.redhat.demo.listener;

import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryModified;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryRemoved;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.event.ClientCacheEntryCreatedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryModifiedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryRemovedEvent;

@ClientListener(includeCurrentState = true)
public class EventLogListener {

    @ClientCacheEntryCreated
    public void entryCreated(ClientCacheEntryCreatedEvent event) {
        System.out.println("[ClientListener] Key added " + event.getKey() + " event[" + event + "]");
    }

    @ClientCacheEntryRemoved
    public void entryRemoved(ClientCacheEntryRemovedEvent event) {
        System.out.println("[ClientListener] Key removed " + event.getKey() + " event[" + event + "]");
    }

    @ClientCacheEntryModified
    public void entryModified(ClientCacheEntryModifiedEvent event) {
        System.out.println("[ClientListener] Key modified " + event.getKey() + " event[" + event + "]");
    }
}