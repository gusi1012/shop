package de.shop.util.rest;

/**
 * @author Gruppe 9
 */

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
public class UriHelper {
	public URI getUri(Class<?> clazz,UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder()
				.path(clazz)
				.build();
	}
	
	public URI getUri(Class<?> clazz, String methodeName, Long id, UriInfo uriInfo)
	{
		return uriInfo.getBaseUriBuilder()
				.path(clazz)
				.path(clazz,methodeName)
				.build(id);
	}

}
