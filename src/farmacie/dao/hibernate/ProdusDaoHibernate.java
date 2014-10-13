package farmacie.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import farmacie.statics.FarmacieConstants;

import farmacie.model.Produs;
import farmacie.dao.ProdusDao;

@Repository
public class ProdusDaoHibernate implements FarmacieConstants, ProdusDao {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public void addProdus(Produs produs, List<String> errors) {
		log.debug("Salvare element in tabela produse: " + produs.toString());
		try {
			sessionFactory.getCurrentSession().save(produs);
		} catch ( ConstraintViolationException constrEx){
			log.debug("Produsul cu numele: " + produs.getNume() + " se afla deja in baza de date!");
			errors.add(DUPLICATE_ENTRY_ERROR);
			sessionFactory.getCurrentSession().clear();
		} catch (Exception e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}	
		
	}

	@Override
	public List<Produs> getProduseByCantitate(int cantMin, int cantMax) {
		String query="from Produs where cantitate>=" + cantMin + " and cantitate<=" + cantMax;
		
		log.debug("Selectare produse din tabela produse dupa cantitate : intre " + cantMin + " si " + cantMax);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<Produs> getProduseByData(Date dataMin, Date dataMax) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = dateFormat.format(dataMin);
        String endDate = dateFormat.format(dataMax);
		String query = "from Produs where data_inreg between '" + startDate + "' and '" + endDate + "'";
		
		log.debug("Selectare produse din tabela produse dupa data : intre " + dataMin + " si " + dataMax);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<Produs> getProduseById(int id) {
		String query = "from Produs where id='" + id + "'";
		
		log.debug("Selectare produse din tabela produse dupa id: " + id);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<Produs> getProduseByNume(String nume) {
		String query = "from Produs where nume='" + nume + "'";
		
		log.debug("Selectare produse din tabela produse dupa nume: " + nume);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<Produs> getProduseByPret(double pretMin, double pretMax) {
		String query="from Produs where pret>=" + pretMin + " and pret<=" + pretMax;
		
		log.debug("Selectare produse din tabela produse dupa pret : intre " + pretMin + " si " + pretMax);
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public void updateProdus(Produs produs, List<String> errors) {
		
		log.debug("Modificare produs in tabele produse. Noul produs: " + produs.toString());
		try {
			sessionFactory.getCurrentSession().update(produs);
			sessionFactory.getCurrentSession().flush();
		}catch (ConstraintViolationException constrEx){
			log.debug("Produs duplicat:" + produs.getNume());
			errors.add(DUPLICATE_ENTRY_ERROR);
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
		}	
	}

	@Override
	public void deleteProdus(Produs produs, List<String> errors) {
		log.debug("Stergere element din tabela produse: " + produs.toString());
		
		try {
			sessionFactory.getCurrentSession().delete(produs);
		}catch (HibernateException e){
				log.debug("Eroare de sistem! " + e.getMessage());
				errors.add(SYSTEM_ERROR);
				sessionFactory.getCurrentSession().clear();
		}
	}

	@Override
	public int deleteProdus(int id, List<String> errors){
		log.debug("Sterg produsul cu id-ul: " + id);
		
		try {
			String query = "delete from Produs where id=" + id; 
			return sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
		}catch (HibernateException e){
				log.debug("Eroare de sistem! " + e.getMessage());
				errors.add(SYSTEM_ERROR);
				sessionFactory.getCurrentSession().clear();
				return 0;
		}		
		
	}
	
	@Override
	public List<Produs> getProduseOrderedByDateAsc() {
		String query = "from Produs order by data_inreg";
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}
	
	@Override
	public List<Produs> searchProduseSimplu(String crumb, Integer idCategorie, int nrOfRecords){
		log.debug("Selectez primele " + nrOfRecords + " produse din categoria " + idCategorie + " dupa criteriul " + crumb);
		
		crumb = crumb.toLowerCase();
		String query = null;
		
		if (idCategorie == null){
			query = "from Produs where lower(nume) like '%" + crumb + "%'";
		} else {
			query = "select produs from Produs as produs WHERE produs.categorie.id = " + idCategorie + " " +
										  "and lower(produs.nume) like '%" + crumb + "%'";
		}
		
		return sessionFactory.getCurrentSession().createQuery(query).setMaxResults(nrOfRecords).list();
	}
	
	@Override
	public boolean existaProduseCuEtalon(int idEtalon){
		
		String query = "select count(*) from Produs as produs WHERE produs.etalon.id = " + idEtalon;
		
		long nrOfRec = (Long)sessionFactory.getCurrentSession().createQuery(query).list().get(0);
		
		if (nrOfRec > 0){
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean existaProduseCuProducator(int idProducator){
		
		String query = "select count(*) from Produs as produs WHERE produs.producator.id = " + idProducator;
		
		long nrOfRec = (Long)sessionFactory.getCurrentSession().createQuery(query).list().get(0);
		
		if (nrOfRec > 0){
			return true;
		} else {
			return false;
		}
	}	
	
	@Override
	public boolean existaProduseCuCategorie(int idCategorie){
		
		String query = "select count(*) from Produs as produs WHERE produs.categorie.id = " + idCategorie;
		
		long nrOfRec = (Long)sessionFactory.getCurrentSession().createQuery(query).list().get(0);
		
		if (nrOfRec > 0){
			return true;
		} else {
			return false;
		}
	}	
	
	@Override
	public int stergeProduseleDinCategoria(int idCat, List<String> errors){
		log.debug("Sterg toate produsele din categoria cu id-ul: " + idCat);
		
		String query = "delete from Produs as produs WHERE produs.categorie.id = :idCat";
		
		int nrOfRows = 0;
		
		try {
			nrOfRows = sessionFactory.getCurrentSession().createQuery(query).setInteger("idCat", idCat).executeUpdate();
		}catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
			sessionFactory.getCurrentSession().clear();
		}	
		
		return nrOfRows;
	}

	@Override
	public void mergeProdus(Produs produs, List<String> errors) {
		log.debug("Modificare produs in tabele produse. Noul produs: " + produs.toString());
		try {
			sessionFactory.getCurrentSession().merge(produs);
		}catch (ConstraintViolationException constrEx){
			log.debug("Produs duplicat:" + produs.getNume());
			errors.add(DUPLICATE_ENTRY_ERROR);
		} catch (HibernateException e){
			log.debug("Eroare de sistem! " + e.getMessage());
			errors.add(SYSTEM_ERROR);
		}		
	}

	@Override
	public Map<Integer, List<Integer>> getNewestNProdsByCat(int n) {
		log.debug("Obtin cele mai noi " + n  + " produse din fiecare categorie");
		Map<Integer, List<Integer>> prodsByCat= new HashMap<Integer, List<Integer>>();
		
		String distincCatQuery = "select distinct categorie.id from Produs";
		List<Integer> catIds = sessionFactory.getCurrentSession().createQuery(distincCatQuery).list();
		
		if (catIds != null){
			for (int catId : catIds){
				String firstNProdsQuery = "select id from Produs where categorie.id = :catId order by dataCreeare DESC";
				List<Integer> firstNProds = sessionFactory.getCurrentSession().createQuery(firstNProdsQuery).setInteger("catId", catId).setMaxResults(n).list();
				prodsByCat.put(catId, firstNProds);
			}
		}
		
		return prodsByCat;
	}

	@Override
	public List<Integer> getNewestProdsInCategories(List<Integer> idCategories, int n) {
		log.debug("Vreau primele " + n + " produse din categoriile: " + idCategories);
		
		StringBuilder query = new StringBuilder("select id from Produs where ");
		
		for (int i=0; i < idCategories.size(); i++){
			query.append("categorie.id=" + idCategories.get(i));
			if (i < idCategories.size() - 1){
				query.append(" OR ");
			}
		}
		query.append("order by dataCreeare DESC");
		
		log.debug("Query-ul este: " + query);
		return sessionFactory.getCurrentSession().createQuery(query.toString()).setMaxResults(n).list();
	}
	

	

}
