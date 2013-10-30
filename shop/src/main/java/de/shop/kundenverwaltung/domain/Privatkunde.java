package de.shop.kundenverwaltung.domain;

import java.net.URI;
import java.security.Timestamp;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.shop.bestellverwaltung.domain.Bestellung;

@XmlRootElement
public class Privatkunde extends Kunde{

	public Privatkunde(Long id, String vorname, String nachname, String email,
			String telefonnummer, Adresse adresse, String bankverbindung,
			Date registrierdatum, Timestamp erzeugt,
			List<Bestellung> bestellungen, URI bestellungUri) {
		super(id, vorname, nachname, email, telefonnummer, adresse, bankverbindung,
				registrierdatum, erzeugt, bestellungen, bestellungUri);
		// TODO Auto-generated constructor stub
	}
	public Privatkunde()
	{
		super();
	}
	

}
