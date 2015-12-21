
package it.redhat.demo.test;

import it.redhat.demo.model.Pojo;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/v1")
public interface ReSTCacheInterface {

    @GET
    @Path("/{cacheName}")
    @Consumes("application/json")
    @Produces("application/json")
    Response getCacheEntries(@PathParam("cacheName") String cacheName);

    @GET
    @Path("/{cacheName}/{key}")
    @Consumes("application/json")
    @Produces("application/json")
    Pojo getCacheKey(@PathParam("cacheName") String cacheName, @PathParam("key") String key);

    @POST
    @Path("/{cache}/{key}")
    @Consumes("application/json")
    @Produces("application/json")
    Response putCacheValue(@PathParam("cache") String cacheName, @PathParam("key") String key, Pojo value);
}
