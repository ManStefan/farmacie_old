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

import farmacie.dao.CategorieAAtributelorDao;
import farmacie.model.Atribut;
import farmacie.model.CategorieAAtributelor;
import farmacie.statics.FarmacieConstants;

@Repository
public class CategorieAAtributelorDaoHibernate implements FarmacieConstants, CategorieAAtributelorDao{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private SessionFactory sessionFactory;		
	
	@Override
	public void addCategorieAAtributelor(CategorieAAtributelor categorieAAtributelor, List<String> errors) {
		log.debug("Salvare element in tabela categorii_ale_atributelor: " + categorieAAtributelor.toString());
		try {
			sessionFactory.getCurrentSession().save(categorieAAtributelor);
		}catch (ConstraintViolationException constrEx){
			log.debug("Categorie a atributelor duplicata:" + categorieAAtributelor.getNume());
			errors.add(DUPLICATE_ENTRY_ERROR);
			sessionFactory.getCurrentSession().clear();
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}	
	}

	@Override
	public void deleteCategorieAAtributelor(
			CategorieAAtributelor categorieAAtributelor, List<String> errors) {
		log.debug("Stergere element din tabela categorii_ale_atributelor: " + categorieAAtributelor.toString());
		
		try{
			sessionFactory.getCurrentSession().delete(categorieAAtributelor);
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
		}
	}

	@Override
	public List<CategorieAAtributelor> getCategorieAAtributelorById(int id) {
		String query = "from CategorieAAtributelor where id='" + id + "'";
		
		log.debug("Selectare categorie de atribute din tabela categorii_ale_atributelor dupa id: " + id);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<CategorieAAtributelor> getCategorieAAtributelorByNume(
			String nume) {
		String query = "from CategorieAAtributelor where nume='" + nume + "'";
		
		log.debug("Selectare categorie a atributelor din tabela categorii_ale_atributelor dupa nume: " + nume);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<CategorieAAtributelor> searchCategorieAAtributelor(String crumb, int nrOfRec) {
		log.debug("Selectare primele " + nrOfRec + " categorii ale atributelor dupa criteriul:" + crumb);
		
		crumb = crumb.toLowerCase();
		
		String query = "from CategorieAAtributelor where lower(nume) like '%" + crumb + "%' order by nume ASC";
		return sessionFactory.getCurrentSession().createQuery(query).setMaxResults(nrOfRec).list();
	}

	@Override
	public long numberOfSearchedResults(String crumb) {
		long nrOfResults = 0;
		
		Session session = sessionFactory.getCurrentSession();
		
		Criterion criterion = Restrictions.ilike("nume", "%" + crumb + "%");
		Criteria criteria = session.createCriteria(CategorieAAtributelor.class).add(criterion);
		
		criteria.setProjection(Projections.countDistinct("id"));
		nrOfResults = (Long)criteria.list().get(0);
		
		criteria.setProjection(null);
		
		return nrOfResults;
	}

	@Override
	public void updateCategorieAAtributelor(CategorieAAtributelor newCat, List<String> errors) {
		log.debug("Update categorie a atributelor: " + newCat.toString());
		
		try {
			sessionFactory.getCurrentSession().update(newCat);
			sessionFactory.getCurrentSession().flush();
		}catch (ConstraintViolationException constrEx){
			log.debug("Categorie a atributelor duplicata:" + newCat.getNume());
			errors.add(DUPLICATE_ENTRY_ERROR);
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
		}		
	}

	@Override
	public List<Atribut> getAtributForCategory(int attrCatId) {
		log.debug("Selectare atributele corespunsatoare categoriei cu id-ul:" + attrCatId);
		
		String query = "select c.atribute from CategorieAAtributelor c where c.id='" + attrCatId + "'";
		
		return  sessionFactory.getCurrentSession().createQuery(query).list();
	}

}
