package farmacie.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import farmacie.statics.FarmacieConstants;

import farmacie.model.Etalon;
import farmacie.model.Producator;
import farmacie.dao.EtalonDao;

@Repository
public class EtalonDaoHibernate implements FarmacieConstants, EtalonDao {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private SessionFactory sessionFactory;		
	
	@Override
	public void addEtalon(Etalon etalon, List<String> errors) {
		log.debug("Salvare element in tabela etaloane: " + etalon.toString());
		try {
			sessionFactory.getCurrentSession().save(etalon);
		} catch ( ConstraintViolationException constrEx){
			log.debug("Etalonul cu numele: " + etalon.getNume() + " se afla deja in baza de date!");
			errors.add(DUPLICATE_ENTRY_ERROR);
			sessionFactory.getCurrentSession().clear();
		} catch (Exception e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}			
	}

	@Override
	public void deleteEtalon(Etalon etalon, List<String> errors) {
		log.debug("Stergere element din tabela etaloane: " + etalon.toString());
		
		try {
			sessionFactory.getCurrentSession().delete(etalon);
		} catch (HibernateException e){
			log.debug("Eroare de sistem: " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}		
	}

	@Override
	public List<Etalon> getEtalonById(int id) {
		String query = "from Etalon where id='" + id + "'";
		
		log.debug("Selectare etaloane din tabela etaloane dupa id: " + id);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<Etalon> getEtalonByNume(String nume) {
		String query = "from Atribut where nume='" + nume + "'";
		
		log.debug("Selectare etaloane din tabela etaloane dupa nume: " + nume);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public void updateEtalon(Etalon etalon, List<String> errors) {
		log.debug("Update etalon la: " + etalon.toString());
		
		try {
			sessionFactory.getCurrentSession().update(etalon);
			sessionFactory.getCurrentSession().flush();
		}catch (ConstraintViolationException constrEx){
			log.debug("Etalon duplicat:" + etalon.getNume());
			errors.add(DUPLICATE_ENTRY_ERROR);
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
		}			
	}
	
	@Override
	public List<Etalon> searchEtalonByName(String crumb, int nrOfRec) {
		log.debug("Selectare primiele " + nrOfRec + " etaloane cu numele asemanator cu: " + crumb);
		
		crumb = crumb.toLowerCase();
		
		String query = "from Etalon where lower(nume) like '%" + crumb + "%' order by nume ASC";
		return sessionFactory.getCurrentSession().createQuery(query).setMaxResults(nrOfRec).list();
	}	

}
