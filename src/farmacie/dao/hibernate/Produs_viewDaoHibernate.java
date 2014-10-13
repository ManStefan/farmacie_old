package farmacie.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import farmacie.dao.Produs_viewDao;
import farmacie.filters.impl.FilterProdus;
import farmacie.page.Page;
import farmacie.sort.Sort;

@Repository
public class Produs_viewDaoHibernate extends FarmacieDaoHibernate implements
		Produs_viewDao {
	
	@Autowired
    private SessionFactory sessionFactory;	
	
	@Override
	public List<Integer> filterProdus_view(FilterProdus filter, Page page, Sort sort) {
		Session session = sessionFactory.getCurrentSession();
				
		//obtin query-ul din filtru
		log.debug("Obtinere sql-ul pentru filtrul selectat");
		String sqlQuery = filter.getSqlFromFilter();
		log.debug("SQL-ul obtinut pentru filtrul selectat este: " + sqlQuery);
		
		//filtrul trebuie refacut pentru optiunile care mai sunt valabile dupa filtrare, doar daca filtram in continuare,
		//daca vrem doar sa luam pagina urmatoare/precedenta, nu mai facem refresh la filtru
		//if (!page.wantNext_page() && !page.wantPrev_page())
			//refreshFilterProdus(criteria, filter);
		
		//trebuie resetata si paginarea daca se vrea lista de produse din orice alt motiv decat cel de a returna pagina urmatoare
		if (page.isApasatLinkPaginare() == false){
			refreshFilterProdus(sqlQuery, filter);
		}
		
		//fac paginarea
		log.debug("Fac paginarea");
		pageCriteria(sqlQuery, page);
		
		//fac sortarea
		log.debug("Fac sortarea");
		sqlQuery = sortCriteria(sqlQuery, sort);
		
		log.debug("SQL-ul obtinut in urma sortarii si paginarii(ultimate) este: " + sqlQuery);
		
		return session.createQuery("SELECT distinct(produs.id) " + sqlQuery).setFirstResult(page.getFirstResultPerPage()).setMaxResults(page.getResult_per_page()).list();
	}

}
