package farmacie.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import farmacie.statics.FarmacieConstants;

import farmacie.model.Arbore_categorii;
import farmacie.dao.Arbore_categoriiDao;

@Repository
public class Arbore_categoriiDaoHibernate implements FarmacieConstants,
		Arbore_categoriiDao {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addArbore_categorii(Arbore_categorii arbore_categorii, List<String> errors){
			log.debug("Salvare element in tabela arbore_categorii: " + arbore_categorii.toString());
			
			try{
				sessionFactory.getCurrentSession().save(arbore_categorii);
			} catch (ConstraintViolationException constrEx){
				log.debug("Cheie duplicata pentru tabela arbore_categorii:" + arbore_categorii.toString());
				errors.add(DUPLICATE_ENTRY_ERROR);
				sessionFactory.getCurrentSession().clear();
			} catch (HibernateException e){
				log.debug("Eroare de sistem! " + e.getMessage());
				errors.add(SYSTEM_ERROR);
				sessionFactory.getCurrentSession().clear();
			}
	}

	@Override
	public void deleteArbore_categorii(Arbore_categorii arboreCategorii, List<String> errors){
			log.debug("Stergere element din tabela arbore_categorii: " + arboreCategorii.toString());
			try {
				sessionFactory.getCurrentSession().delete(arboreCategorii);
			} catch (HibernateException e){
				log.debug("Eroare de sistem! " + e.getMessage());
				errors.add(SYSTEM_ERROR);
				sessionFactory.getCurrentSession().clear();
			}
	}

	@Override
	public List<Arbore_categorii> getArbore_categoriiById_cat_copil(int idCatCopil){
		String query = "from Arbore_categorii where id_cat_copil='" + idCatCopil + "'";
		log.debug("Obtinere lista din tabela arbore_categorii dupa id-ul: " + idCatCopil);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<Arbore_categorii> getArbore_categoriiById_cat_parinte(int idCatParinte){
		String query = "from Arbore_categorii where id_cat_parinte='" + idCatParinte + "'";
		log.debug("Obtinere lista din tabela arbore_categorii dupa id-ul:" + idCatParinte);
		return sessionFactory.getCurrentSession().createQuery(query).list();	
	}

	@Override
	public List<Integer> getCathegoriesUnderCat(int idCat) {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(idCat);
		int poz = 0;
		
		while (poz < ids.size()){
			List<Integer> childCat = getChildCatByIdCatParent(ids.get(poz));
			if (childCat != null && !childCat.isEmpty()){
				ids.addAll(getChildCatByIdCatParent(ids.get(poz)));
			}
			poz++;
		}
		
		return ids;
	}

	@Override
	public List<Integer> getChildCatByIdCatParent(int idCatParent) {
		String query = "select ab.id_cat_copil from Arbore_categorii ab where ab.id_cat_parinte='" + idCatParent + "'";
		log.debug("Obtinere lista de copii a categoriei parinte: " + idCatParent);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

}
