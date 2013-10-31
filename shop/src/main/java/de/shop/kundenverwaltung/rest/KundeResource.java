package de.shop.kundenverwaltung.rest;

import static de.shop.util.Constants.ADD_LINK;
//import static de.shop.util.Constants.FIRST_LINK;
//import static de.shop.util.Constants.LAST_LINK;
import static de.shop.util.Constants.REMOVE_LINK;
import static de.shop.util.Constants.SELF_LINK;
import static de.shop.util.Constants.UPDATE_LINK;


import java.lang.invoke.MethodHandles;
import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;
//import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;

import de.shop.kundenverwaltung.domain.Adresse;
import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.kundenverwaltung.service.Kundenservice;
import de.shop.kundenverwaltung.service.Kundenservice.FetchType;
import de.shop.util.Mock;
import de.shop.util.rest.NotFoundException;
import de.shop.util.rest.UriHelper;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;

@Path("/Kunde")
@Produces({APPLICATION_JSON, APPLICATION_XML + ";qs=0.75",TEXT_XML + ";qs=0.5"})


public class KundeResource {
	
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	public static final String Version ="0.1";
	public static final String KUNDEN_ID_PATH_PARAM = "kundeId";
	private static final String NOT_FOUND_ID = "kunde.notFound.id";
	
	@Context
	private UriInfo uriInfo;
	
	@Inject 
	private UriHelper uriHelper; 
	
	
	@Inject
	private Kundenservice ks;
	
	
	
	@GET
	@Path("{" + KUNDEN_ID_PATH_PARAM + ":[1-9][0-9]*}")
	public Response findKundeById(@PathParam(KUNDEN_ID_PATH_PARAM) Long id) {
		
		final Kunde kunde = Mock.findKundeById(id);
		if (kunde == null) {
			throw new NotFoundException("Kein Kunde mit der ID " + id + " gefunden.");
		}
		
		setStructuralLinks(kunde, uriInfo);
		
		return Response.ok(kunde)
                       .links(getTransitionalLinks(kunde, uriInfo))
                       .build();
	}
	
	public void setStructuralLinks(Kunde kunde, UriInfo uriInfo) {
		
		final URI uri = getUriBestellungen(kunde, uriInfo);
		kunde.setBestellungenUri(uri);
	}

	private URI getUriBestellungen(Kunde kunde, UriInfo uriInfo) {
		return uriHelper.getUri(KundeResource.class, "findBestellungenByKundeId", kunde.getId(), uriInfo);
	}	
	
	public Link[] getTransitionalLinks(Kunde kunde, UriInfo uriInfo) {
		final Link self = Link.fromUri(getUriKunde(kunde, uriInfo))
	                          .rel(SELF_LINK)
	                          .build();
		
		final Link add = Link.fromUri(uriHelper.getUri(KundeResource.class, uriInfo))
                             .rel(ADD_LINK)
                             .build();

		final Link update = Link.fromUri(uriHelper.getUri(KundeResource.class, uriInfo))
                                .rel(UPDATE_LINK)
                                .build();

		final Link remove = Link.fromUri(uriHelper.getUri(KundeResource.class, "deleteKunde", kunde.getId(), uriInfo))
                                .rel(REMOVE_LINK)
                                .build();
		
		return new Link[] { self, add, update, remove };
	}
	
	public URI getUriKunde(Kunde kunde, UriInfo uriInfo) {
		return uriHelper.getUri(KundeResource.class, "findKundeById", kunde.getId(), uriInfo);
	}
	
	@POST
	@Consumes({APPLICATION_JSON, APPLICATION_XML, TEXT_XML})
	@Produces
	public Response createKunde(@Valid Kunde kunde) {
		kunde.setId(null);
		
		final Adresse adresse = kunde.getAdresse();
		if (adresse != null) {
			adresse.setKunde(kunde);
		}
	
	
	kunde = ks.createKunde(kunde);
	LOGGER.tracef("Kunde: %s", kunde);
	
	return Response.created(getUriKunde(kunde, uriInfo)).build();
	
	}	

	
	@PUT
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public void updateKunde(@Valid Kunde kunde) {
		
		final Kunde origKunde = ks.findKundeById(kunde.getId(), FetchType.NUR_KUNDE);
		if (origKunde == null) {
			throw new NotFoundException(NOT_FOUND_ID, kunde.getId());
		}
		LOGGER.tracef("Kunde vorher: %s", origKunde);
	
		
		origKunde.setValues(kunde);
		LOGGER.tracef("Kunde nachher: %s", origKunde);
		
	
		kunde = ks.updateKunde(origKunde);
		if (kunde == null) {
			throw new NotFoundException(NOT_FOUND_ID, origKunde.getId());
		}
	}
	

}
