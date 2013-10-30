package de.shop.kundenverwaltung.rest;

import java.lang.invoke.MethodHandles;
import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;

import de.shop.kundenverwaltung.domain.Adresse;
import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.kundenverwaltung.service.Kundenservice;
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
	
	@Context
	private UriInfo uriInfo;
	
	private Kundenservice ks;
	

/*	
	@POST
	@Consumes({APPLICATION_JSON, APPLICATION_XML, TEXT_XML})
	@Produces
	public Response createKunde(@Valid Kunde kunde) {
		kunde.setId(null);//ToDo noch null ändern
		
		final Adresse adresse = kunde.getAdresse();
		if (adresse != null) {
			adresse.setKunde(kunde);
		}
	
	
	kunde = ks.createKunde(kunde);
	LOGGER.tracef("Kunde: %s", kunde);
	
	return Response.created(getUriKunde(kunde, uriInfo)).build();
	
	}	
*/
	

	
	 
	
	
	
	

}
