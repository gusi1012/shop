package de.shop.kundenverwaltung.domain;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

//import org.codehaus.jackson.annotate.JsonSubTypes;
//import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import de.shop.bestellverwaltung.domain.Bestellung;


@XmlRootElement
//@XmlSeeAlso({ Firmenkunde.class, Privatkunde.class })
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
/*@JsonSubTypes({
	@Type(value = Privatkunde.class, name = Kunde.KUNDE),
	@Type(value = Firmenkunde.class, name = AbstractKunde.FIRMENKUNDE) }) */
public class Kunde implements Serializable {
	private static final long serialVersionUID = 7401524595142572933L;
	
	//public static final String KUNDE = "K";
	
	private Long id;
	private String nachname;
	private String email;
	private Adresse adresse;	

	public Kunde(Long id, String nachname, String email) {
		super();
		this.id = id;
		this.nachname = nachname;
		this.email = email;
		//this.adresse = adresse;
	//	this.bestellungen = bestellungen;
	//	this.bestellungenUri = bestellungenUri;
	}


	
	public Kunde() {
		super();
	}

	@XmlTransient
	private List<Bestellung> bestellungen;
	
	private URI bestellungenUri;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	public List<Bestellung> getBestellungen() {
		return bestellungen;
	}
	public void setBestellungen(List<Bestellung> bestellungen) {
		this.bestellungen = bestellungen;
	}

	public URI getBestellungenUri() {
		return bestellungenUri;
	}
	public void setBestellungenUri(URI bestellungenUri) {
		this.bestellungenUri = bestellungenUri;
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
		}
		else if (!email.equals(other.email))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Kunde [id=" + id + ", nachname=" + nachname + ", email=" + email
			   + ", bestellungenUri=" + bestellungenUri + "]";
	}
}



// package de.shop.kundenverwaltung.domain;

/**
 * @author Gruppe 9
 */

/*import java.net.URI;
import java.security.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import de.shop.bestellverwaltung.domain.Bestellung;

@XmlSeeAlso({Firmenkunde.class, Privatkunde.class})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@Type(value = Privatkunde.class, name = Kunde.PRIVATKUNDE),
	@Type(value = Firmenkunde.class, name = Kunde.FIRMENKUNDE)})

@NamedQueries({
	@NamedQuery(name  = Kunde.FIND_KUNDEN,
                query = "SELECT k"
				        + " FROM   Kunde k"),
	@NamedQuery(name  = Kunde.FIND_KUNDEN_FETCH_BESTELLUNGEN,
				query = "SELECT  DISTINCT k"
						+ " FROM Kunde k LEFT JOIN FETCH k.bestellungen"),
	@NamedQuery(name  = Kunde.FIND_KUNDEN_ORDER_BY_ID,
		        query = "SELECT   k"
				        + " FROM  Kunde k"
		                + " ORDER BY k.id"),
	@NamedQuery(name  = Kunde.FIND_IDS_BY_PREFIX,
		        query = "SELECT   k.id"
		                + " FROM  Kunde k"
		                + " WHERE CONCAT('', k.id) LIKE :" + Kunde.PARAM_KUNDE_ID_PREFIX
		                + " ORDER BY k.id"),
	@NamedQuery(name  = Kunde.FIND_KUNDEN_BY_NACHNAME,
	            query = "SELECT k"
				        + " FROM   Kunde k"
	            		+ " WHERE  UPPER(k.nachname) = UPPER(:" + Kunde.PARAM_KUNDE_NACHNAME + ")"),
	@NamedQuery(name  = Kunde.FIND_NACHNAMEN_BY_PREFIX,
   	            query = "SELECT   DISTINCT k.nachname"
				        + " FROM  Kunde k "
	            		+ " WHERE UPPER(k.nachname) LIKE UPPER(:"
	            		+ Kunde.PARAM_KUNDE_NACHNAME_PREFIX + ")"),
	// FIXME https://hibernate.atlassian.net/browse/HHH-8285 : @NamedEntityGraph ab Java EE 7 bzw. JPA 2.1
	@NamedQuery(name  = Kunde.FIND_KUNDEN_BY_NACHNAME_FETCH_BESTELLUNGEN,
	            query = "SELECT DISTINCT k"
			            + " FROM   Kunde k LEFT JOIN FETCH k.bestellungen"
			            + " WHERE  UPPER(k.nachname) = UPPER(:" + Kunde.PARAM_KUNDE_NACHNAME + ")"),
	// FIXME https://hibernate.atlassian.net/browse/HHH-8285 : @NamedEntityGraph ab Java EE 7 bzw. JPA 2.1
	@NamedQuery(name  = Kunde.FIND_KUNDE_BY_ID_FETCH_BESTELLUNGEN,
	            query = "SELECT DISTINCT k"
			            + " FROM   Kunde k LEFT JOIN FETCH k.bestellungen"
			            + " WHERE  k.id = :" + Kunde.PARAM_KUNDE_ID),
	// FIXME https://hibernate.atlassian.net/browse/HHH-8285 : @NamedEntityGraph ab Java EE 7 bzw. JPA 2.1
   	@NamedQuery(name  = Kunde.FIND_KUNDE_BY_ID_FETCH_WARTUNGSVERTRAEGE,
   	            query = "SELECT DISTINCT k"
   			            + " FROM   Kunde k LEFT JOIN FETCH k.wartungsvertraege"
   			            + " WHERE  k.id = :" + Kunde.PARAM_KUNDE_ID),
   	@NamedQuery(name  = Kunde.FIND_KUNDE_BY_EMAIL,
   	            query = "SELECT DISTINCT k"
   			            + " FROM   Kunde k"
   			            + " WHERE  k.email = :" + Kunde.PARAM_KUNDE_EMAIL),
    @NamedQuery(name  = Kunde.FIND_KUNDEN_BY_PLZ,
	            query = "SELECT k"
				        + " FROM  Kunde k"
			            + " WHERE k.adresse.plz = :" + Kunde.PARAM_KUNDE_ADRESSE_PLZ),
	@NamedQuery(name = Kunde.FIND_KUNDEN_BY_DATE,
			    query = "SELECT k"
			            + " FROM  Kunde k"
			    		+ " WHERE k.seit = :" + Kunde.PARAM_KUNDE_SEIT),
	@NamedQuery(name = Kunde.FIND_PRIVATKUNDEN_FIRMENKUNDEN,
			    query = "SELECT k"
			            + " FROM  Kunde k"
			    		+ " WHERE TYPE(k) IN (Privatkunde, Firmenkunde)")
})

@XmlRootElement
public abstract class Kunde {
	public static final String PRIVATKUNDE= "P";
	public static final String FIRMENKUNDE= "F";
	//TODO Kapieren !!
	
	private static final String PREFIX = "AbstractKunde.";
	public static final String FIND_KUNDEN = PREFIX + "findKunden";
	public static final String FIND_KUNDEN_FETCH_BESTELLUNGEN = PREFIX + "findKundenFetchBestellungen";
	public static final String FIND_KUNDEN_ORDER_BY_ID = PREFIX + "findKundenOrderById";
	public static final String FIND_IDS_BY_PREFIX = PREFIX + "findIdsByPrefix";
	public static final String FIND_KUNDEN_BY_NACHNAME = PREFIX + "findKundenByNachname";
	public static final String FIND_NACHNAMEN_BY_PREFIX = PREFIX + "findNachnamenByPrefix";
	// FIXME https://hibernate.atlassian.net/browse/HHH-8285 : @NamedEntityGraph ab Java EE 7 bzw. JPA 2.1
	public static final String FIND_KUNDEN_BY_NACHNAME_FETCH_BESTELLUNGEN =
		                       PREFIX + "findKundenByNachnameFetchBestellungen";
	public static final String FIND_KUNDE_BY_ID_FETCH_WARTUNGSVERTRAEGE =
		                       PREFIX + "findKundenByNachnameFetchWartungsvertraege";
	public static final String FIND_KUNDE_BY_ID_FETCH_BESTELLUNGEN =
		                       PREFIX + "findKundeByIdFetchBestellungen";
	
	public static final String FIND_KUNDE_BY_EMAIL = PREFIX + "findKundeByEmail";
	public static final String FIND_KUNDEN_BY_PLZ = PREFIX + "findKundenByPlz";
	public static final String FIND_KUNDEN_BY_DATE = PREFIX + "findKundenByDate";
	public static final String FIND_PRIVATKUNDEN_FIRMENKUNDEN = PREFIX + "findPrivatkundenFirmenkunden";
	
	public static final String PARAM_KUNDE_ID = "kundeId";
	public static final String PARAM_KUNDE_ID_PREFIX = "idPrefix";
	public static final String PARAM_KUNDE_NACHNAME = "nachname";
	public static final String PARAM_KUNDE_NACHNAME_PREFIX = "nachnamePrefix";
	public static final String PARAM_KUNDE_ADRESSE_PLZ = "plz";
	public static final String PARAM_KUNDE_SEIT = "seit";
	public static final String PARAM_KUNDE_EMAIL = "email";
	
	public static final String GRAPH_BESTELLUNGEN = "bestellungen";
	public static final String GRAPH_WARTUNGSVERTRAEGE = "wartungsvertraege";
	
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
	
	public void setValues(Kunde k) {
		nachname = k.nachname;
		vorname = k.vorname;
		email = k.email;
	}

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

	public Kunde()
	{
		super();
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
	
	public URI getBestellungenUri() {
		return bestellungUri;
	}
	
	public void setBestellungenUri(URI bestellungUri) {
		this.bestellungUri = bestellungUri;
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
	

} */
