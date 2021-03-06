package de.shop.bestellverwaltung.rest;

import static de.shop.util.Constants.SELF_LINK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.kundenverwaltung.rest.KundeResource;
import de.shop.util.Mock;
import de.shop.util.rest.UriHelper;
import de.shop.util.rest.NotFoundException;


@Path("/bestellungen")
@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
@Consumes
public class Bestellungresource {
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private UriHelper uriHelper;
	
	@Inject
	private KundeResource kundeResource;
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Response findBestellungById(@PathParam("id") Long id) {
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		final Bestellung bestellung = Mock.findBestellungById(id);
		if (bestellung == null) {
			throw new NotFoundException("Keine Bestellung mit der ID " + id + " gefunden.");
		}
		
		setStructuralLinks(bestellung, uriInfo);
		
		// Link-Header setzen
		final Response response = Response.ok(bestellung)
                                          .links(getTransitionalLinks(bestellung, uriInfo))
                                          .build();
		
		return response;
	}
	
	public void setStructuralLinks(Bestellung bestellung, UriInfo uriInfo) {
		// URI fuer Kunde setzen
		final Kunde kunde = bestellung.getKunde();
		if (kunde != null) {
			final URI kundeUri = kundeResource.getUriKunde(bestellung.getKunde(), uriInfo);
			bestellung.setKundeUri(kundeUri);
		}
	}
	
	private Link[] getTransitionalLinks(Bestellung bestellung, UriInfo uriInfo) {
		final Link self = Link.fromUri(getUriBestellung(bestellung, uriInfo))
                              .rel(SELF_LINK)
                              .build();
		return new Link[] { self };
	}
	
	public URI getUriBestellung(Bestellung bestellung, UriInfo uriInfo) {
		return uriHelper.getUri(Bestellungresource.class, "findBestellungById", bestellung.getId(), uriInfo);
	}
}

//package de.shop.bestellverwaltung.rest;
//
///**
// * @author Gruppe 9
// */
//
//import static de.shop.util.Constants.SELF_LINK;
//import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
//import static javax.ws.rs.core.MediaType.APPLICATION_XML;
//import static javax.ws.rs.core.MediaType.TEXT_XML;
//
//import java.net.URI;
//
//import javax.inject.Inject;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.NotFoundException;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Link;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//
//import de.shop.bestellverwaltung.domain.Bestellung;
//import de.shop.kundenverwaltung.domain.Kunde;
//import de.shop.kundenverwaltung.rest.KundeResource;
//import de.shop.util.Mock;
//import de.shop.util.rest.UriHelper;
//
//
//@Path("rest/bestellungen")
//@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
//@Consumes
//public class Bestellungresource {
//	@Context
//	private UriInfo uriInfo;
//	
//	@Inject
//	private UriHelper uriHelper;
//	
//	@Inject
//	private KundeResource kundeResource;
//	
//	@GET
//	@Path("{id:[1-9][0-9]*}")
//	public Response findBestellungById(@PathParam("id") Long id) {
//		// TODO Anwendungskern statt Mock, Verwendung von Locale
//		final Bestellung bestellung = Mock.findBestellungById(id);
//		if (bestellung == null) {
//			throw new NotFoundException("Keine Bestellung mit der ID " + id + " gefunden.");
//		}
//		
//		setStructuralLinks(bestellung, uriInfo);
//		
//		// Link-Header setzen
//		final Response response = Response.ok(bestellung)
//                                          .links(getTransitionalLinks(bestellung, uriInfo))
//                                          .build();
//		
//		return response;
//	}
//	
//	public void setStructuralLinks(Bestellung bestellung, UriInfo uriInfo) {
//		// URI fuer Kunde setzen
//		final Kunde kunde = bestellung.getKunde();
//		if (kunde != null) {
//			final URI kundeUri = kundeResource.getUriKunde(bestellung.getKunde(), uriInfo);
//			bestellung.setKundeUri(kundeUri);
//		}
//	}
//	
//	private Link[] getTransitionalLinks(Bestellung bestellung, UriInfo uriInfo) {
//		final Link self = Link.fromUri(getUriBestellung(bestellung, uriInfo))
//                              .rel(SELF_LINK)
//                              .build();
//		return new Link[] { self };
//	}
//	
//	public URI getUriBestellung(Bestellung bestellung, UriInfo uriInfo) {
//		return uriHelper.getUri(Bestellungresource.class, "findBestellungById", bestellung.getId(), uriInfo);
//	}
//}
