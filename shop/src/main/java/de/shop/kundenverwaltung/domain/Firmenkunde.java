package de.shop.kundenverwaltung.domain;

import java.net.URI;
import java.security.Timestamp;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.shop.bestellverwaltung.domain.Bestellung;

@XmlRootElement
public class Firmenkunde extends Kunde {

	
	private Long Steuernummer;
	
	public Firmenkunde(Long id, String vorname, String nachname, String email,
			String telefonnummer, Adresse adresse, String bankverbindung,
			Date registrierdatum, Timestamp erzeugt,
			List<Bestellung> bestellungen, URI bestellungUri, Long Steuernummer) {
		super(id, vorname, nachname, email, telefonnummer, adresse, bankverbindung,
				registrierdatum, erzeugt, bestellungen, bestellungUri);
		Steuernummer = this.Steuernummer;
		// TODO Auto-generated constructor stub
	}
	public Firmenkunde()
	{
		super();
	}

	@Override
	public String toString() {
		return "Firmenkunde [Steuernummer=" + Steuernummer + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((Steuernummer == null) ? 0 : Steuernummer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Firmenkunde other = (Firmenkunde) obj;
		if (Steuernummer == null) {
			if (other.Steuernummer != null)
				return false;
		} else if (!Steuernummer.equals(other.Steuernummer))
			return false;
		return true;
	}

}
