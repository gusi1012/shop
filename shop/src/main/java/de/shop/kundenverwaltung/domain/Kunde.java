package de.shop.kundenverwaltung.domain;

import java.net.URI;
import java.security.Timestamp;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.shop.bestellverwaltung.domain.Bestellung;


@XmlRootElement
public class Kunde {
	@NotNull
	private Long id;
	
	@NotNull(message ="{kunde.vorname.notNull}")
	@Size(min = 2, max = 32)
	@Pattern(regexp = "[A-ZÄÖÜ][a-zäöüß]+")
	private String vorname;
	
	@NotNull(message ="{kunde.nachname.notNull}")
	@Size(min = 2, max = 32, message= "{kunde.nachname.length}")
	@Pattern(regexp = "[A-ZÄÖÜ][a-zäöüß]+")
	private String nachname;
	
	@NotNull
	@Size(min = 2, max = 128)
	@Pattern(regexp = "[\\w.%-]+@[\\w.%-]+\\.[A-Za-z]{2,4}")
	private String email;
	
	@NotNull
	@Size(min = 2, max = 50)
	@Pattern(regexp = "[1-9]")
	private String telefonnummer;
	
	@NotNull
	@Valid
	private Adresse adresse;
	
	@NotNull
	@Size(min = 6, max = 20)	//ToDo max Bankverbindung noch richtig machen
	private String bankverbindung;
	
	@Past
	private Date registrierdatum;
	private Timestamp erzeugt;
	
	@XmlTransient
	private List<Bestellung> bestellungen;
	
	private URI bestellungUri;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getBankverbindung() {
		return bankverbindung;
	}

	public void setBankverbindung(String bankverbindung) {
		this.bankverbindung = bankverbindung;
	}

	public Date getRegistrierdatum() {
		return registrierdatum;
	}

	public void setRegistrierdatum(Date registrierdatum) {
		this.registrierdatum = registrierdatum;
	}

	public Timestamp getErzeugt() {
		return erzeugt;
	}

	public void setErzeugt(Timestamp erzeugt) {
		this.erzeugt = erzeugt;
	}

	public List<Bestellung> getBestellungen() {
		return bestellungen;
	}

	public void setBestellungen(List<Bestellung> bestellungen) {
		this.bestellungen = bestellungen;
	}

	public URI getBestellungUri() {
		return bestellungUri;
	}

	public void setBestellungUri(URI bestellungUri) {
		this.bestellungUri = bestellungUri;
	}

	public Kunde(Long id, String vorname, String nachname, String email,
			String telefonnummer, Adresse adresse, String bankverbindung,
			Date registrierdatum, Timestamp erzeugt,
			List<Bestellung> bestellungen, URI bestellungUri) {
		super();
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.email = email;
		this.telefonnummer = telefonnummer;
		this.adresse = adresse;
		this.bankverbindung = bankverbindung;
		this.registrierdatum = registrierdatum;
		this.erzeugt = erzeugt;
		this.bestellungen = bestellungen;
		this.bestellungUri = bestellungUri;
	}

	@Override
	public String toString() {
		return "Kunde [id=" + id + ", vorname=" + vorname + ", nachname="
				+ nachname + ", email=" + email + ", telefonnummer="
				+ telefonnummer + ", adresse=" + adresse + ", bankverbindung="
				+ bankverbindung + ", registrierdatum=" + registrierdatum
				+ ", erzeugt=" + erzeugt + ", bestellungen=" + bestellungen
				+ ", bestellungUri=" + bestellungUri + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Kunde other = (Kunde) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	

}
