package farmacie.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import farmacie.statics.FarmacieConstants;

import farmacie.model.Producator;
import farmacie.dao.ProducatorDao;

@Repository
public class ProducatorDaoHibernate implements FarmacieConstants, ProducatorDao {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private SessionFactory sessionFactory;		
	
	@Override
	public void addProducator(Producator producator, List<String> errors) {
		log.debug("Salvare element in tabela producatori: " + producator.toString());
		try {
			sessionFactory.getCurrentSession().save(producator);
		} catch ( ConstraintViolationException constrEx){
			log.debug("Producatorul cu numele: " + producator.getNume() + " se afla deja in baza de date!");
			errors.add(DUPLICATE_ENTRY_ERROR);
			sessionFactory.getCurrentSession().clear();
		} catch (Exception e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}
	}

	@Override
	public void deleteProducator(Producator producator, List<String> errors) {
		log.debug("Stergere element din tabela producatori: " + producator.toString());
		try {
			sessionFactory.getCurrentSession().delete(producator);
		} catch (HibernateException e){
			log.debug("Eroare de sistem: " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}
	}

	@Override
	public List<Producator> getProducatorById(int id) {
		String query = "from Producator where id='" + id + "'";
		
		log.debug("Selectare producatori din tabela producatori dupa id: " + id);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<Producator> getProducatorByNume(String nume) {
		String query = "from Producator where nume='" + nume + "'";
		
		log.debug("Selectare producatori din tabela producatori dupa nume: " + nume);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public void updateProducator(Producator producator, List<String> errors) {
		log.debug("Update producator la:" + producator.toString());
		
		try {
			sessionFactory.getCurrentSession().update(producator);
			sessionFactory.getCurrentSession().flush();
		}catch (ConstraintViolationException constrEx){
			log.debug("Producator duplicat:" + producator.getNume());
			errors.add(DUPLICATE_ENTRY_ERROR);
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
		}
		
		
	}

	@Override
	public List<Producator> searchProducatorByName(String crumb, int nrOfRec) {
		log.debug("Selectare primii " + nrOfRec + " producatori cu numele asemanator cu: " + crumb);
		
		crumb = crumb.toLowerCase();
		
		String query = "from Producator where lower(nume) like '%" + crumb + "%' order by nume ASC";
		return sessionFactory.getCurrentSession().createQuery(query).setMaxResults(nrOfRec).list();
	}

}
