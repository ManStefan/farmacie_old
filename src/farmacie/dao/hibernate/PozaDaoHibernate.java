package farmacie.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import farmacie.statics.FarmacieConstants;

import farmacie.model.Poza;
import farmacie.dao.PozaDao;

@Repository
public class PozaDaoHibernate implements PozaDao, FarmacieConstants {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public void addPoza(Poza poza, List<String> errors) {
		log.debug("Salvare element in tabela poze: " + poza.toString());
		
		try{
			sessionFactory.getCurrentSession().save(poza);
		} catch ( ConstraintViolationException constrEx){
			log.debug("Poza cu numele: " + poza.getPoza() + " se afla deja in baza de date!");
			errors.add(DUPLICATE_ENTRY_ERROR);
			sessionFactory.getCurrentSession().clear();
		} catch (Exception e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}			
	}

	@Override
	public void deletePoza(Poza poza, List<String> errors) {
		log.debug("Stergere element din tabela poze: " + poza.toString());
		
		try {
			sessionFactory.getCurrentSession().delete(poza);
		} catch (HibernateException e){
			log.debug("Eroare de sistem: " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}			
	}

	@Override
	public List<Poza> getPozaByIdPoza(int id) {
		String query = "from Poza where id='" + id + "'";
		
		log.debug("Selectare poze din tabela poze dupa id: " + id);
		return sessionFactory.getCurrentSession().createQuery(query).list();		
	}

	@Override
	public List<Poza> getPozaByIdProdus(int idProd) {
		String query = "from Poza where id_prod='" + idProd + "'";
		
		log.debug("Selectare poze din tabela poze dupa id_prod: " + idProd);
		return sessionFactory.getCurrentSession().createQuery(query).list();	}

}
