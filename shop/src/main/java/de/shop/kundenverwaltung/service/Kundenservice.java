package de.shop.kundenverwaltung.service;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;

import de.shop.kundenverwaltung.domain.Kunde;


public class Kundenservice {

	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	public static Logger getLogger() {
		return LOGGER;
	}
	
	public enum FetchType {
		NUR_KUNDE,
		MIT_BESTELLUNGEN,
	}
	
	public enum OrderType {
		KEINE,
		ID
	}
	
	
	//The "transient" keyword also tells JPA to ignore this field when persisting the entity.
	@Inject
	private transient EntityManager em;
	/*
	 * Suche alle Kunden.
	 * @param fetch Angabe, welche Objekte aus der DB mitgeladen werden sollen, z.B. Bestellungen.
	 * @param order Sortierreihenfolge, z.B. noch ID
	 * @return Liste der Kunden
	 */
	public List<Kunde> findAllKunden(FetchType fetch, OrderType order) {
		List<Kunde> kunden;
		switch (fetch) {
			case NUR_KUNDE:
				kunden = OrderType.ID.equals(order)
				         ? em.createNamedQuery(Kunde.FIND_KUNDEN_ORDER_BY_ID, Kunde.class)
				             .getResultList()
				         : em.createNamedQuery(Kunde.FIND_KUNDEN, Kunde.class)
				             .getResultList();
				break;
			
			case MIT_BESTELLUNGEN:
				kunden = em.createNamedQuery(Kunde.FIND_KUNDEN_FETCH_BESTELLUNGEN, Kunde.class)
						   .getResultList();
				break;

			default:
				kunden = OrderType.ID.equals(order)
		                 ? em.createNamedQuery(Kunde.FIND_KUNDEN_ORDER_BY_ID, Kunde.class)
		                	 .getResultList()
		                 : em.createNamedQuery(Kunde.FIND_KUNDEN, Kunde.class)
		                     .getResultList();
				break;
		}
		return kunden;
	}
	
	
	/**
	 * Suche alle Kunden mit gleichem Nachnamen
	 * @param nachname Der gemeinsame Nachname
	 * @param fetch Angabe, welche Objekte aus der DB mitgeladen werden sollen, z.B. Bestellungen.
	 * @return Liste der gefundenen Kunden
	 */
	public List<Kunde> findKundenByNachname(String nachname, FetchType fetch) {
		List<Kunde> kunden;
		switch (fetch) {
			case NUR_KUNDE:
				kunden = em.createNamedQuery(Kunde.FIND_KUNDEN_BY_NACHNAME, Kunde.class)
						   .setParameter(Kunde.PARAM_KUNDE_NACHNAME, nachname)
						   .getResultList();
				break;
			
			case MIT_BESTELLUNGEN:
				kunden = em.createNamedQuery(Kunde.FIND_KUNDEN_BY_NACHNAME_FETCH_BESTELLUNGEN,
						                     Kunde.class)
						   .setParameter(Kunde.PARAM_KUNDE_NACHNAME, nachname)
						   .getResultList();
				break;

			default:
				kunden = em.createNamedQuery(Kunde.FIND_KUNDEN_BY_NACHNAME, Kunde.class)
						   .setParameter(Kunde.PARAM_KUNDE_NACHNAME, nachname)
						   .getResultList();
				break;
		}

		return kunden;
	}
	
	
	/**
	 * Suche alle Nachnamen mit gleichem Praefix
	 * @param nachnamePrefix Der gemeinsame Praefix
	 * @return Liste der passenden Nachnamen
	 */
	public List<String> findNachnamenByPrefix(String nachnamePrefix) {
		return em.createNamedQuery(Kunde.FIND_NACHNAMEN_BY_PREFIX, String.class)
				 .setParameter(Kunde.PARAM_KUNDE_NACHNAME_PREFIX, nachnamePrefix + '%')
				 .getResultList();
	}

	/**
	 * Suche einen Kunden zu gegebener ID.
	 * @param id Die gegebene ID.
	 * @param fetch Angabe, welche Objekte aus der DB mitgeladen werden sollen, z.B. Bestellungen.
	 * @return Der gefundene Kunde oder null.
	 */
	public Kunde findKundeById(Long id, FetchType fetch) {
		if (id == null) {
			return null;
		}
		
		Kunde kunde = null;
			switch (fetch) {
				case NUR_KUNDE:
					kunde = em.find(Kunde.class, id);
					break;
				
				case MIT_BESTELLUNGEN:
					try {
						kunde = em.createNamedQuery(Kunde.FIND_KUNDE_BY_ID_FETCH_BESTELLUNGEN, Kunde.class)
								  .setParameter(Kunde.PARAM_KUNDE_ID, id)
								  .getSingleResult();
					}
					catch (NoResultException e) {
						kunde = null;
					}
					break;
	
				default:
					kunde = em.find(Kunde.class, id);
					break;
			}

		return kunde;
	}

	/**
	 * Suche nach IDs mit gleichem Praefix.
	 * @param idPrefix Der gemeinsame Praefix.
	 * @return Liste der passenden Praefixe.
	 */
	public List<Long> findIdsByPrefix(String idPrefix) {
		return em.createNamedQuery(Kunde.FIND_IDS_BY_PREFIX, Long.class)
				 .setParameter(Kunde.PARAM_KUNDE_ID_PREFIX, idPrefix + '%')
				 .getResultList();
	}
	
	/**
	 * Suche einen Kunden zu gegebener Email-Adresse.
	 * @param email Die gegebene Email-Adresse.
	 * @return Der gefundene Kunde oder null.
	 */
	public Kunde findKundeByEmail(String email) {
		try {
			return em.createNamedQuery(Kunde.FIND_KUNDE_BY_EMAIL, Kunde.class)
					 .setParameter(Kunde.PARAM_KUNDE_EMAIL, email)
					 .getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
	/**
	 * Die Kunden mit gleicher Postleitzahl suchen.
	 * @param plz Die Postleitzahl
	 * @return Liste der passenden Kunden.
	 */
	public List<Kunde> findKundenByPLZ(String plz) {
		return em.createNamedQuery(Kunde.FIND_KUNDEN_BY_PLZ, Kunde.class)
                 .setParameter(Kunde.PARAM_KUNDE_ADRESSE_PLZ, plz)
                 .getResultList();
	}

	/**
	 * Diejenigen Kunden suchen, die seit einem bestimmten Datum registriert sind. 
	 * @param seit Das zu vergleichende Datum
	 * @return Die Liste der passenden Kunden
	 */
	public List<Kunde> findKundenBySeit(Date seit) {
		return em.createNamedQuery(Kunde.FIND_KUNDEN_BY_DATE, Kunde.class)
                 .setParameter(Kunde.PARAM_KUNDE_SEIT, seit)
                 .getResultList();
	}
	
	/**
	 * Alle Privat- und Firmenkunden suchen.
	 * @return Liste der Privat- und Firmenkunden.
	 */
	public List<Kunde> findPrivatkundenFirmenkunden() {
		return em.createNamedQuery(Kunde.FIND_PRIVATKUNDEN_FIRMENKUNDEN, Kunde.class)
                 .getResultList();
	}
	
	public <T extends Kunde> T createKunde(T kunde) {
		if(kunde==null) {
			return kunde;
		}
		try {
			em.createNamedQuery(Kunde.FIND_KUNDE_BY_EMAIL, Kunde.class)
				.setParameter(Kunde.PARAM_KUNDE_EMAIL, kunde.getEmail())
				.getSingleResult();
			throw new EmailExistsException(kunde.getEmail());
		}
		catch(NoResultException e) {
			LOGGER.trace("Email_adress existiert noch nicht");
		}
		
		em.persist(kunde);
		return kunde;
	}
	
}
