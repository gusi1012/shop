package de.shop.bestellverwaltung.domain;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.shop.kundenverwaltung.domain.Kunde;

@XmlRootElement
public class Bestellung {
	private Long id;
	private Boolean ausgeliefert;
	
	@XmlTransient
	private Kunde kunde;
	private URI kundeUri;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getAusgeliefert() {
		return ausgeliefert;
	}
	public void setAusgeliefert(Boolean ausgeliefert) {
		this.ausgeliefert = ausgeliefert;
	}
	public Kunde getKunde() {
		return kunde;
	}
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}
	public URI getKundeUri() {
		return kundeUri;
	}
	public void setKundeUri(URI kundeUri) {
		this.kundeUri = kundeUri;
	}
	public Bestellung(Long id, Boolean ausgeliefert, Kunde kunde, URI kundeUri) {
		super();
		this.id = id;
		this.ausgeliefert = ausgeliefert;
		this.kunde = kunde;
		this.kundeUri = kundeUri;
	}
	@Override
	public String toString() {
		return "Bestellung [id=" + id + ", ausgeliefert=" + ausgeliefert
				+ ", kunde=" + kunde + ", kundeUri=" + kundeUri + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ausgeliefert == null) ? 0 : ausgeliefert.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kunde == null) ? 0 : kunde.hashCode());
		result = prime * result
				+ ((kundeUri == null) ? 0 : kundeUri.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bestellung other = (Bestellung) obj;
		if (ausgeliefert == null) {
			if (other.ausgeliefert != null)
				return false;
		} else if (!ausgeliefert.equals(other.ausgeliefert))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kunde == null) {
			if (other.kunde != null)
				return false;
		} else if (!kunde.equals(other.kunde))
			return false;
		if (kundeUri == null) {
			if (other.kundeUri != null)
				return false;
		} else if (!kundeUri.equals(other.kundeUri))
			return false;
		return true;
	}
	

}
