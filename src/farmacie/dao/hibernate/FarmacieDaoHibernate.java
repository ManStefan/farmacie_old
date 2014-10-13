package farmacie.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import farmacie.dao.AtributDao;
import farmacie.dao.FarmacieDao;
import farmacie.filters.impl.FilterProdus;
import farmacie.page.Page;
import farmacie.statics.FarmacieConstants;
import farmacie.sort.Sort;

@Repository
public class FarmacieDaoHibernate implements FarmacieDao, FarmacieConstants {

	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
	private AtributDao atributDao;
	
	@Override
	public void pageCriteria(Criteria criteria, Page page) {
		//calculez numarul total de pagini
		long rez_number;
		
		criteria.setProjection(Projections.countDistinct(ID_PRODUS));
		rez_number = (Long)criteria.list().get(0);
		criteria.setProjection(null);
		
		log.debug("Numarul de rezultate filtrate: " + rez_number);
		
		page.setTotal_result(rez_number);
		
		page.setTotalPages((int)rez_number / page.getResult_per_page());
		
		if ((page.getTotal_result() > 0) && (rez_number % page.getResult_per_page() != 0))
			page.setTotalPages(page.getTotalPages() + 1);
		
		if (page.getCurrentPage() == 999999999)
			page.setCurrentPage(page.getTotalPages());
		log.debug("Se vrea pagina: " + page.getCurrentPage());
		
		criteria.setFirstResult(page.getFirstResultPerPage());
		criteria.setMaxResults(page.getResult_per_page());
	}

	@Override
	public void pageCriteria(String sqlQuery, Page page){
		//calculez numarul total de pagini
		long rez_number;

		String sqlNumberQuery = "SELECT count(*) " + sqlQuery;
		rez_number = (Long)sessionFactory.getCurrentSession().createQuery(sqlNumberQuery).list().get(0);

		log.debug("Numarul de rezultate filtrate: " + rez_number);
		
		page.setTotal_result(rez_number);
		
		page.setTotalPages((int)rez_number / page.getResult_per_page());
		
		if ((page.getTotal_result() > 0) && (rez_number % page.getResult_per_page() != 0))
			page.setTotalPages(page.getTotalPages() + 1);
		
		if (page.getCurrentPage() == 999999999){
			page.setCurrentPage(page.getTotalPages());
		}
		
		if (page.getCurrentPage() > page.getTotalPages()){
			page.setCurrentPage(page.getTotalPages());
		}
		log.debug("Se vrea pagina: " + page.getCurrentPage());
	}
	
	@Override
	public void sortCriteria(Criteria criteria, Sort sort) {
		if (sort.getSortOrder().equals(Sort.SORT_ASC))
		{
			criteria.addOrder(Order.asc(sort.getSortField()));
			log.debug("Sortarea se face crescator");
		}
		else{
			criteria.addOrder(Order.desc(sort.getSortField()));
			log.debug("Sortarea se face descrescator");
		}
	}

	@Override
	public String sortCriteria(String sqlQuery, Sort sort){
		log.debug("SortOrder in FarmacieDaoHibernate: " + sort.getSortOrder());
		
		if (!sort.getSortOrder().equals(Sort.NO_SORT)){
				sqlQuery += " ORDER BY " + sort.getSortField() + " " + sort.getSortOrder(); 
		}
		
		return sqlQuery;
	}
	
/*	@Override
	public void refreshFilterProdus(Criteria criteria, FilterProdus filter) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> list_props;
		
		//vad daca mai am categorii valabile
		criteria.setProjection(Projections.distinct(Projections.property(ID_CATEGORIE)));
		filter.setId_categorieAll( criteria.list());
		if (filter.getId_categorieAll() != null && !filter.getId_categorieAll().isEmpty()){
			for (int i=0; i < filter.getId_categorieAll().size(); i++)
				if (filter.getId_categorieAll().get(i) == null)
					filter.getId_categorieAll().remove(i);
		}
		
		//vad daca mai am atribute valabile
		ProjectionList proListProds = Projections.projectionList();
		proListProds.add(Projections.distinct(Projections.property(ID_PRODUS)));
		criteria.setProjection(proListProds);
		List productsList = criteria.list();
		Criterion idProdsCriterion = null;
		if (productsList != null && !productsList.isEmpty()){
			idProdsCriterion = Restrictions.eq(ID_PRODUS, productsList.get(0));
			log.debug("%%%%%%%%%%%%%%%%%%%%%%"+productsList.get(0)+"%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			for (int i=1; i < productsList.size(); i++){
				log.debug("%%%%%%%%%%%%%%%%%%%%%%"+productsList.get(i)+"%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				idProdsCriterion = Restrictions.or(idProdsCriterion, Restrictions.eq(ID_PRODUS, productsList.get(i)));
			}
		}
		
		if (idProdsCriterion != null){
			Criteria criteriaProds = session.createCriteria(Produs_view.class).add(idProdsCriterion);
			
			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.distinct(Projections.property(ID_ATRIBUT)));
			proList.add(Projections.property(ID_CATEGORIE_ATRIBUT));
			criteriaProds.setProjection(proList);
			
			List list = criteriaProds.list();
			if (list != null && !list.isEmpty()){
				Iterator it = list.iterator();
				while (it.hasNext()){
					Object[] row = (Object[])it.next();
					if (row[0] != null && row[1] != null){
						if (filter.getId_atributAll().get((Integer)row[1]) != null){
							filter.getId_atributAll().get((Integer)row[1]).add((Integer)row[0]);
						} else {
							List<Integer> tmp_list = new ArrayList<Integer>();
							tmp_list.add((Integer)row[0]);
							filter.getId_atributAll().put((Integer)row[1], tmp_list);
						}
					}
				}
			}	
		}
		
		//vad daca mai am producatori
		criteria.setProjection(Projections.distinct(Projections.property(ID_PRODUCATOR)));
		filter.setId_producatorAll(criteria.list());
		if (filter.getId_producatorAll() != null && !filter.getId_producatorAll().isEmpty()){
			for (int i = 0; i < filter.getId_producatorAll().size(); i++)
				if (filter.getId_producatorAll().get(i) == null)
					filter.getId_producatorAll().remove(i);
		}
		
		//vad daca mai am etaloane
		criteria.setProjection(Projections.distinct(Projections.property(ID_ETALON)));
		filter.setId_etalonAll(criteria.list());
		if (filter.getId_etalonAll() != null && !filter.getId_etalonAll().isEmpty()){
			for (int i=0; i < filter.getId_etalonAll().size(); i++)
				if (filter.getId_etalonAll().get(i) == null)
					filter.getId_etalonAll().remove(i);
		}
		
		//vad care e maximul si minum-ul pentru pret
		criteria.setProjection(Projections.min(PRET_PRODUS));
		Object min = criteria.uniqueResult();
		if (min != null)
			filter.getPret_produsAll().add((Double)min);
		criteria.setProjection(Projections.max(PRET_PRODUS));
		Object max = criteria.uniqueResult();
		if (max != null)
			filter.getPret_produsAll().add((Double)max);
		
		//vad care e maximul si minim-ul pentru cantitate
		criteria.setProjection(Projections.min(CANTITATE_PRODUS));
		min = criteria.uniqueResult();
		if (min != null)
			filter.getCantitate_produsAll().add((Integer)min);	
		criteria.setProjection(Projections.max(CANTITATE_PRODUS));
		 max = criteria.uniqueResult();
		if (max != null)
			filter.getCantitate_produsAll().add((Integer)max);
		
		criteria.setProjection(null);
	}*/

	@Override
	public void refreshFilterProdus(String sqlQuery, FilterProdus filter) {
		Session session = sessionFactory.getCurrentSession();
		
		//selectez categoriile valabile
		String sqlQueryCategorii = "SELECT distinct(categorie.id) " + sqlQuery;
		filter.setId_categorieAll(session.createQuery(sqlQueryCategorii).list());
		
		//selectez producatorii valabili
		String sqlQueryProducatori = "SELECT distinct(producator.id) " + sqlQuery;
		filter.setId_producatorAll(session.createQuery(sqlQueryProducatori).list());
		
		//selectez etaloanele valabile
		String sqlQueryEtaloane = "SELECT distinct(etalon.id) " + sqlQuery;
		filter.setId_etalonAll(session.createQuery(sqlQueryEtaloane).list());
		
		//selectez atributele valabile		
		StringBuilder sqlQueryAtribute = new StringBuilder("SELECT distinct(atribut.id) ").append(new StringBuilder(sqlQuery).insert(22, "LEFT JOIN produs.atributeProdus AS atribut_produs INNER JOIN atribut_produs.atribut AS atribut "));
		List attrsId = session.createQuery(sqlQueryAtribute.toString()).list();
		if (attrsId != null && !attrsId.isEmpty()){
			Iterator it = attrsId.iterator();
			Map<Integer, List<Integer>> attrsMap = new HashMap<Integer, List<Integer>>();			
			while (it.hasNext()){
				int idAttr = (Integer)it.next();
				int idCatAttr = atributDao.getAtributById(idAttr).get(0).getCategorieAAtributelor().getId();
				if (attrsMap.containsKey(idCatAttr)){
					attrsMap.get(idCatAttr).add(idAttr);
				} else {
					List<Integer> tmp = new ArrayList<Integer>();
					tmp.add(idAttr);
					attrsMap.put(idCatAttr, tmp);
				}
			}
			filter.setId_atributAll(attrsMap);
		}
	}
}
