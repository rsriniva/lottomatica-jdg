package it.redhat.demo.server.event;

import java.io.Serializable;

import org.infinispan.notifications.cachelistener.filter.CacheEventConverter;
import org.infinispan.notifications.cachelistener.filter.CacheEventConverterFactory;
import org.infinispan.notifications.cachelistener.filter.NamedFactory;

@NamedFactory(name = "static-converter")
public class StaticCacheEventConverterFactory implements CacheEventConverterFactory, Serializable {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public CacheEventConverter<String, String, CustomEvent> getConverter(Object[] params) {
		return new StaticCacheEventConverter();
	}
	
}
