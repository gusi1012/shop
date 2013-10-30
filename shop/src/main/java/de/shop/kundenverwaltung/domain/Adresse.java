package de.shop.kundenverwaltung.domain;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

public class Adresse {
	@NotNull
	private Long id;
	private String postleitzahl;
	private String wohnort;
	private String straﬂe;
	private String hausnummer;
	
	@XmlTransient 
	private Kunde kunde;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPostleitzahl() {
		return postleitzahl;
	}

	public void setPostleitzahl(String postleitzahl) {
		this.postleitzahl = postleitzahl;
	}

	public String getWohnort() {
		return wohnort;
	}

	public void setWohnort(String wohnort) {
		this.wohnort = wohnort;
	}

	public String getStraﬂe() {
		return straﬂe;
	}

	public void setStraﬂe(String straﬂe) {
		this.straﬂe = straﬂe;
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}

	public Kunde getKunde() {
		return kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public Adresse(Long id, String postleitzahl, String wohnort, String straﬂe,
			String hausnummer, Kunde kunde) {
		super();
		this.id = id;
		this.postleitzahl = postleitzahl;
		this.wohnort = wohnort;
		this.straﬂe = straﬂe;
		this.hausnummer = hausnummer;
		this.kunde = kunde;
	}

	@Override
	public String toString() {
		return "Adresse [id=" + id + ", postleitzahl=" + postleitzahl
				+ ", wohnort=" + wohnort + ", straﬂe=" + straﬂe
				+ ", hausnummer=" + hausnummer + ", kunde=" + kunde + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hausnummer == null) ? 0 : hausnummer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kunde == null) ? 0 : kunde.hashCode());
		result = prime * result
				+ ((postleitzahl == null) ? 0 : postleitzahl.hashCode());
		result = prime * result + ((straﬂe == null) ? 0 : straﬂe.hashCode());
		result = prime * result + ((wohnort == null) ? 0 : wohnort.hashCode());
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
		Adresse other = (Adresse) obj;
		if (hausnummer == null) {
			if (other.hausnummer != null)
				return false;
		} else if (!hausnummer.equals(other.hausnummer))
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
		if (postleitzahl == null) {
			if (other.postleitzahl != null)
				return false;
		} else if (!postleitzahl.equals(other.postleitzahl))
			return false;
		if (straﬂe == null) {
			if (other.straﬂe != null)
				return false;
		} else if (!straﬂe.equals(other.straﬂe))
			return false;
		if (wohnort == null) {
			if (other.wohnort != null)
				return false;
		} else if (!wohnort.equals(other.wohnort))
			return false;
		return true;
	}

}
