package farmacie.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import farmacie.statics.FarmacieConstants;

import farmacie.model.Atribut_produs;
import farmacie.model.Produs;
import farmacie.dao.Atribut_produsDao;

@Repository
public class Atribut_produsDaoHibernate implements Atribut_produsDao,
		FarmacieConstants {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public void addAtribut_produs(Atribut_produs atributProdus, List<String> errors){
		log.debug("Salvare element in tabela atribute_produse: " + atributProdus.toString());
		
		try {
			sessionFactory.getCurrentSession().save(atributProdus);
		}catch ( ConstraintViolationException constrEx){
			log.debug("Produsul cu numele: " + atributProdus.getProdus().getNume() + " are deja atributul " + atributProdus.getAtribut().getNume());
			errors.add(DUPLICATE_ENTRY_ERROR);
			sessionFactory.getCurrentSession().clear();
		}catch (Exception e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}			
	}

	@Override
	public void deleteAtribut_produs(Atribut_produs atributProdus, List<String> errors) {
		log.debug("Stergere element din tabela atribute_produse: " + atributProdus.toString());
		
		try {
			sessionFactory.getCurrentSession().delete(atributProdus);
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}			
	}

	@Override
	public List<Atribut_produs> getAtribut_produsById_atribut(int idAtribut) {
		return null;
	}

	@Override
	public List<Atribut_produs> getAtribut_produsById_prod(int idProd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteAllAtribut_produsByProdus(Produs produs,
			List<String> errors) {
		String query = "delete from Atribut_produs at where at.produs.id = " + produs.getId();
		try {
			return sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}		
		
		return -1;
		
		
	}
	

}
