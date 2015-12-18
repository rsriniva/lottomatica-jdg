package it.redhat.demo.cache;

import org.infinispan.manager.CacheContainer;

public interface CacheContainerFactory {

	public abstract CacheContainer getCacheContainer();
	
}
