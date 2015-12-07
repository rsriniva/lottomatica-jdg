package com.redhat.ejb;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author francesco
 */
import java.util.Iterator;
import java.util.Map;
 
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.ServerStatistics;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

@Singleton

public class HotRodListener {
 
    private RemoteCacheManager cacheManager;
    private RemoteCache<String, Object> cache;
 
    
    
    public void execute() {
 
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.addServer()
                .host("127.0.0.1")
                .port(Integer.parseInt("11222"));
        cacheManager = new RemoteCacheManager(builder.build());
 
        cache = cacheManager.getCache("MyCoolCache");
        cache.addClientListener(new EventLogListener());
        System.out.println("Sleeping");
        try {
            Thread.sleep(60000);
        } catch (InterruptedException ex) {
           ex.printStackTrace();        
        }
        
        
  
        System.out.println("Dumping cache Data");
        System.out.println("==========================");
        Set set = this.cache.keySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
     
    }
 
   
}