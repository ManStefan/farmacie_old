package farmacie.dao;

import org.hibernate.Criteria;

import farmacie.filters.impl.FilterProdus;
import farmacie.page.Page;
import farmacie.sort.Sort;
import farmacie.statics.FarmacieConstants;

public interface FarmacieDao extends FarmacieConstants {
	
	public void pageCriteria(Criteria criteria, Page page);
	
	public void pageCriteria(String sqlQuery, Page page);
	
	public void sortCriteria(Criteria criteria, Sort sort);
	
	public String sortCriteria(String sqlQuery, Sort sort);
	
	/**
	 * Metoda care revizuieste filtrul pentru a contine numai optiunile care sunt valabile dupa ce se aplica acest filtru
	 * @param criteria
	 * @param filter
	 */
//	public void refreshFilterProdus(Criteria criteria, FilterProdus filter);
	
	public void refreshFilterProdus(String sqlQuery, FilterProdus filter);
	
}
