package com.redhat.ejb;

import org.infinispan.client.hotrod.annotation.*;
import org.infinispan.client.hotrod.event.*;

@ClientListener
public class EventLogListener {

  @ClientCacheEntryModified
  @ClientCacheEntryRemoved
    
  @ClientCacheEntryCreated
  public void entryAdded(ClientEvent event) {
    System.out.println("Element added! "+event);
  }



}