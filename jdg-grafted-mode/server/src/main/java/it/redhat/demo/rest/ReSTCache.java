package it.redhat.demo.rest;

import it.redhat.demo.model.Pojo;
import it.redhat.demo.server.LocalCacheContainer;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.Map;

@ApplicationPath("/")
@Path("/v1")
public class ReSTCache extends Application {

    private static final Logger log = LoggerFactory.getLogger(ReSTCache.class);

    @Inject
    @LocalCacheContainer
    private CacheContainer cacheContainer;

    @GET
    @Path("/{cacheName}")
    @Produces("application/json")
    public Response getCacheEntries(@PathParam("cacheName") String cacheName) {
        log.info(String.format("Get all entries for cache [%s]", cacheName));

        try {
            Cache<String, Object> cache = cacheContainer.getCache(cacheName);
            Map<String, Object> map = new HashMap<>();
            for (Map.Entry<String, Object> entry : cache.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
            return Response.ok(map).build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{cacheName}/{key}")
    @Produces("application/json")
    public Response getCacheKey(@PathParam("cacheName") String cacheName, @PathParam("key") String key) {
        log.info(String.format("Get entry for cache [%s] and key [%s]", cacheName, key));

        try {
            Object o = cacheContainer.getCache(cacheName).get(key);
            System.out.println("Get Object [" + o + "]");
            return Response.status(Response.Status.OK).entity(o).build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{cache}/{key}")
    @Produces("application/json")
    public Response putCacheValue(@Context UriInfo uriInfo, @PathParam("cache") String cacheName, @PathParam("key") String key, Pojo value) {
        log.info(String.format("Create entry for cache [%s] and key [%s]  and value [%s]", cacheName, key, value));

        try {
            Cache<Object, Object> cache = cacheContainer.getCache(cacheName);
            cache.put(key, value);
            return Response.created(uriInfo.getRequestUri()).build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
