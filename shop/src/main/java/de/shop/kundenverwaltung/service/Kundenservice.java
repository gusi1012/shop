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
	
	
	
	@Inject
	private transient EntityManager em;

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
	
	

	public List<String> findNachnamenByPrefix(String nachnamePrefix) {
		return em.createNamedQuery(Kunde.FIND_NACHNAMEN_BY_PREFIX, String.class)
				 .setParameter(Kunde.PARAM_KUNDE_NACHNAME_PREFIX, nachnamePrefix + '%')
				 .getResultList();
	}


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

	
	public List<Long> findIdsByPrefix(String idPrefix) {
		return em.createNamedQuery(Kunde.FIND_IDS_BY_PREFIX, Long.class)
				 .setParameter(Kunde.PARAM_KUNDE_ID_PREFIX, idPrefix + '%')
				 .getResultList();
	}
	
	
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
	
	public List<Kunde> findKundenByPLZ(String plz) {
		return em.createNamedQuery(Kunde.FIND_KUNDEN_BY_PLZ, Kunde.class)
                 .setParameter(Kunde.PARAM_KUNDE_ADRESSE_PLZ, plz)
                 .getResultList();
	}

	
	public List<Kunde> findKundenBySeit(Date seit) {
		return em.createNamedQuery(Kunde.FIND_KUNDEN_BY_DATE, Kunde.class)
                 .setParameter(Kunde.PARAM_KUNDE_SEIT, seit)
                 .getResultList();
	}
	
	
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
	
	public <T extends Kunde> T updateKunde(T kunde) {
		if (kunde == null) {
			return null;
		}
		
		em.detach(kunde);
		
		
		final Kunde	tmp = findKundeByEmail(kunde.getEmail());
		if (tmp != null) {
			em.detach(tmp);
			if (tmp.getId().longValue() != kunde.getId().longValue()) {
				throw new EmailExistsException(kunde.getEmail());
			}
		}

		em.merge(kunde);
		return kunde;
	}
	
}
