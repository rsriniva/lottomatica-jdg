package it.redhat.demo.listener;

import org.infinispan.client.hotrod.annotation.*;
import org.infinispan.client.hotrod.event.*;

@ClientListener
public class EventLogListener {

  @ClientCacheEntryModified
  @ClientCacheEntryRemoved
    
  @ClientCacheEntryCreated
  public void entryAdded(ClientEvent event) {
    
    ClientCacheEntryCreatedEvent e = (ClientCacheEntryCreatedEvent)event;
    System.out.println("[ClientListener] Key added " +e.getKey());
  }



}