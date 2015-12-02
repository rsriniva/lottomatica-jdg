/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.hotrod.storedtask;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.util.Set;
import java.util.UUID;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

/* This Demo shows how to load and execute a Script Task on Infinispan
   The script is written in javascript and insert one entry in the cache
   passed as argument to the script.
*/ 
public class Demo1 {

    private RemoteCacheManager cacheManager;
    private RemoteCache<String, Object> cache;

    public Demo1() {
        String script = "// mode=local,language=javascript\n"
                + "value\n"
                + "var cache = cacheManager.getCache(\"default\");\n"
                + "cache.clear();\n"
                + "cache.put(marshaller.objectToByteBuffer(\"k0\"), marshaller.objectToByteBuffer(value));";

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.addServer()
                .host("127.0.0.1")
                .port(Integer.parseInt("11222"));
        cacheManager = new RemoteCacheManager(builder.build());
        cache = cacheManager.getCache("default");

        RemoteCache<String, String> scriptCache = cacheManager.getCache("___script_cache");

        scriptCache.put("script.js", script);

        Map<String, Object> params = new HashMap<>();
        params.put("value", UUID.randomUUID().toString());

        Object result = cache.execute("script.js", params);

        System.out.println("Dumping cache Data");
        System.out.println("==========================");

        Set set = this.cache.keySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            String key = (String) i.next();
            System.out.println("[ key: " + key + " - value: " + cache.get(key));
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Demo1();
    }

}
