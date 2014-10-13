package farmacie.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import farmacie.statics.FarmacieConstants;

import farmacie.model.Categorie;
import farmacie.dao.CategorieDao;

@Repository
public class CategorieDaoHibernate implements CategorieDao, FarmacieConstants {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public void addCategorie(Categorie categorie, List<String> errors) {
		log.debug("Salvare element in tabela categorii: " + categorie.toString());
		try {
			sessionFactory.getCurrentSession().save(categorie);
		} catch (ConstraintViolationException constrEx){
			log.debug("Categorie duplicata:" + categorie.getNume());
			errors.add(DUPLICATE_ENTRY_ERROR);
			sessionFactory.getCurrentSession().clear();
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}	

	}

	@Override
	public void deleteCategorie(Categorie categorie, List<String> errors) {
		log.debug("Stergere element din tabela categorii: " + categorie.toString());
		try {
			sessionFactory.getCurrentSession().delete(categorie);
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
		}

	}

	@Override
	public List<Categorie> getCategorieById(int id) {
		String query = "from Categorie where id='" + id + "'";
		
		log.debug("Selectare categorii din tabela categorii dupa id: " + id);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<Categorie> getCategorieByNivel(int nivel) {
		String query = "from Categorie where nivel='" + nivel + "'";
		
		log.debug("Selectare categorii din tabela categorii dupa nivel: " + nivel);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}
	
	@Override
	public List<Integer> getIdCategorieByNivel(int nivel) {
		String query = "select id from Categorie where nivel='" + nivel + "'";
		
		log.debug("Selectare categorii din tabela categorii dupa nivel: " + nivel);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}	

	@Override
	public List<Categorie> getCategorieByNume(String nume) {
		String query = "from Categorie where nume='" + nume + "'";
		
		log.debug("Selectare categorii din tabela categorii dupa nume: " + nume);
		return sessionFactory.getCurrentSession().createQuery(query).list();	}

	@Override
	public List<Categorie> getToateCategoriile() {
		String query = "from Categorie";
		
		log.debug("Selectare categorii din tabela categorii.");
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public void updateCategorie(Categorie categorie, List<String> errors) {
		log.debug("Se face update cu categoria:" + categorie.toString());
		
		try {
			sessionFactory.getCurrentSession().update(categorie);
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}
	}

	@Override
	public List<Categorie> searchCategorie(String crumb, String nivel, int frunza, int nrOfRecords) {
		log.debug("Selectare primele " + nrOfRecords + " categorii de pe nivelul " + nivel + " dupa criteriul " + crumb + ". Selectam doar frunzele: " + frunza);
		
		crumb = crumb.toLowerCase();
		String query = null;
		
		if (nivel.equals("*")){
			if (frunza == -1){
				query = "from Categorie where lower(nume) like '%" + crumb + "%' order by nume ASC";
			} else {
				query = "from Categorie where lower(nume) like '%" + crumb + "%' and frunza=" + frunza + " order by nume ASC";
			}
		}
		else {
			if (frunza == -1){
				query = "from Categorie where lower(nume) like '%" + crumb + "%' and nivel=" + nivel + " order by nume ASC";
			} else {
				query = "from Categorie where lower(nume) like '%" + crumb + "%' and nivel=" + nivel + " and frunza=" + frunza + " order by nume ASC";
			}
		}

		return sessionFactory.getCurrentSession().createQuery(query).setMaxResults(nrOfRecords).list();
	}
	
	@Override
	public List<Categorie> searchCategorie(String crumb, String nivel, int nrOfRecords){
		log.debug("Selectare primele " + nrOfRecords + " categorii de pe nivelul " + nivel + " dupa criteriul " + crumb);
		
		crumb = crumb.toLowerCase();
		String query = null;
		
		if (nivel.equals("*"))
			query = "from Categorie where lower(nume) like '%" + crumb + "%' order by nume ASC";
		else
			query = "from Categorie where lower(nume) like '%" + crumb + "%' and nivel=" + nivel + " order by nume ASC";

		return sessionFactory.getCurrentSession().createQuery(query).setMaxResults(nrOfRecords).list();
		
	}
}
