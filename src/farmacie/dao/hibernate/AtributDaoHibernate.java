package farmacie.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import farmacie.statics.FarmacieConstants;

import farmacie.model.Atribut;
import farmacie.model.Atribut_produs;
import farmacie.model.CategorieAAtributelor;
import farmacie.dao.AtributDao;

@Repository
public class AtributDaoHibernate implements FarmacieConstants, AtributDao {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public void addAtribut(Atribut atribut, List<String> errors) {
		log.debug("Salvare element in tabela atribute: " + atribut.toString());
		
		try{
			sessionFactory.getCurrentSession().save(atribut);
		}catch (ConstraintViolationException constrEx){
			log.debug("Atribut duplicat:" + atribut.getNume());
			errors.add(DUPLICATE_ENTRY_ERROR);
			sessionFactory.getCurrentSession().clear();
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}	
	
	}

	@Override
	public void deleteAtribut(Atribut atribut, List<String> errors) {
		log.debug("Stergere element din tabela atribute: " + atribut.toString());
		try {
			sessionFactory.getCurrentSession().delete(atribut);
 		}catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}

	}

	@Override
	public List<Atribut> getAtributById(int id) {
		log.debug("Selectare lista atribute dupa id: " + id);
		String query = "from Atribut where id='" + id + "'";
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<Atribut> getAtributByNume(String nume) {
		log.debug("Selectare lista atribute dupa nume: " + nume);
		String query = "from Atribut where nume='" + nume + "'";
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<Atribut> searchAtribut(String crumb, CategorieAAtributelor catAttr, int nrOfRec) {
		log.debug("Selectare primele " + nrOfRec + " atribute dupa criteriul:" + crumb);
		
		crumb = crumb.toLowerCase();
		
		String query = "from Atribut where lower(nume) like '%" + crumb + "%' and categorieAAtributelor.id=" + catAttr.getId() + "order by nume ASC";
		return sessionFactory.getCurrentSession().createQuery(query).setMaxResults(nrOfRec).list();

	}

	@Override
	public long numberOfSearchedResults(String crumb, CategorieAAtributelor catAttr) {
		long nrOfResults = 0;
		
		Session session = sessionFactory.getCurrentSession();
		
		Criterion criterion = Restrictions.ilike("nume", "%" + crumb + "%");
		Criteria criteria = session.createCriteria(Atribut.class).add(criterion);
		
		criteria.setProjection(Projections.countDistinct("id"));
		nrOfResults = (Long)criteria.list().get(0);
		
		criteria.setProjection(null);
		
		return nrOfResults;
	}

	@Override
	public void updateAtribut(Atribut atribut, List<String> errors) {
		log.debug("Update atribut la:" + atribut.toString());
		
		try {
			sessionFactory.getCurrentSession().update(atribut);
			sessionFactory.getCurrentSession().flush();
		}catch (ConstraintViolationException constrEx){
			log.debug("Atribut duplicat:" + atribut.getNume());
			errors.add(DUPLICATE_ENTRY_ERROR);
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
		}
		
	}

	@Override
	public List<Atribut_produs> getAtributProdusByIdAtribut(int id) {
		String query = "select a.atribute_produse from Atribut a where a.id=" + id;
		
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

}
