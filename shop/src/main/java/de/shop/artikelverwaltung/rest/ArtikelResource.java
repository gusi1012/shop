package de.shop.artikelverwaltung.rest;

/**
 * @author Gruppe 9
 */

import static de.shop.util.Constants.SELF_LINK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;

import java.lang.invoke.MethodHandles;
import java.net.URI;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.transaction.Transactional;
// import javax.validation.Valid;
// import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Artikel;
//import de.shop.artikelverwaltung.service.ArtikelService;
import de.shop.util.Mock;
import de.shop.util.rest.UriHelper;
import de.shop.util.rest.NotFoundException;
import de.shop.util.interceptor.Log;

@Path("rest/artikel")
@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
@Consumes
@Transactional
@Log
public class ArtikelResource {
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private static final String NOT_FOUND_ID = "artikel.notFound.id";
	
	@Context
	private UriInfo uriInfo;
	
	/*@Inject
	private Artikelservice as; */
	
	@Inject
	private UriHelper uriHelper;
	
	@PostConstruct
	private void postConstruct() {
		LOGGER.debugf("CDI-faehiges Bean %s wurde erzeugt", this);
	}
	
	@PreDestroy
	private void preDestroy() {
		LOGGER.debugf("CDI-faehiges Bean %s wird geloescht", this);
	}
	
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Response findArtikelById(@PathParam("id") Long id) {
		final Artikel artikel = Mock.findArtikelById(id);
		if (artikel == null) {
			throw new NotFoundException(NOT_FOUND_ID, id);
		}

		return Response.ok(artikel)
                       .links(getTransitionalLinks(artikel, uriInfo))
                       .build();
	}
	
	private Link[] getTransitionalLinks(Artikel artikel, UriInfo uriInfo) {
		final Link self = Link.fromUri(getUriArtikel(artikel, uriInfo))
                              .rel(SELF_LINK)
                              .build();

		return new Link[] { self };
	}
	
	public URI getUriArtikel(Artikel artikel, UriInfo uriInfo) {
		return uriHelper.getUri(ArtikelResource.class, "findArtikelById", artikel.getId(), uriInfo);
	}
}
