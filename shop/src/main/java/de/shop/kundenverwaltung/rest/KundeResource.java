package de.shop.kundenverwaltung.rest;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;

@Path("/Kunde")
@Produces({APPLICATION_JSON, APPLICATION_XML + ";qs=0.75",TEXT_XML + ";qs=0.5"})


public class KundeResource {
	public static final String Version ="0.1";
	public static final String KUNDEN_ID_PATH_PARAM = "kundeId";
	
	@Context
	private UriInfo uriInfo;
	
	 
	
	
	
	

}
