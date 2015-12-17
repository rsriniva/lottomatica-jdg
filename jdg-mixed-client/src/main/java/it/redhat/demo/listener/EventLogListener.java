package it.redhat.demo.listener;

import it.redhat.demo.model.CacheNode;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryModified;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryRemoved;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.event.ClientCacheEntryCreatedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryModifiedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryRemovedEvent;

@ClientListener
public class EventLogListener {

    private final RemoteCache<String, CacheNode> cache;

    public EventLogListener(RemoteCache<String, CacheNode> cache) {
        this.cache = cache;
    }

    @ClientCacheEntryCreated
    public void entryCreated(ClientCacheEntryCreatedEvent<String> event) {
        System.out.println("[ClientListener] Key added " + event.getKey() + " event[" + event + "]");
        Object o = cache.get(event.getKey());
        System.out.println("[ClientListener] Key added " + event.getKey() + " object[" + o + "]");
    }

    @ClientCacheEntryRemoved
    public void entryRemoved(ClientCacheEntryRemovedEvent<String> event) {
        System.out.println("[ClientListener] Key removed " + event.getKey() + " event[" + event + "]");
    }

    @ClientCacheEntryModified
    public void entryModified(ClientCacheEntryModifiedEvent<String> event) {
        System.out.println("[ClientListener] Key modified " + event.getKey() + " event[" + event + "]");
        Object o = cache.get(event.getKey());
        System.out.println("[ClientListener] Key added " + event.getKey() + " object[" + o + "]");
    }
}