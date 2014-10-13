package farmacie.filters.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import farmacie.filters.Filter;

public class FilterProdus extends Filter{
	
	//id-urile pentru optiunile selectate
	private List<Integer> id_categorie = new ArrayList<Integer>();
	private Map<Integer, List<Integer>> id_atribut = new HashMap<Integer, List<Integer>>();
	private List<Integer> id_producator = new ArrayList<Integer>();
	private List<Integer> id_etalon = new ArrayList<Integer>();
	private List<Double> pret_produs = new ArrayList<Double>();
	private List<Integer> cantitate_produs = new ArrayList<Integer>();
	private String crumb;

	//aici sunt id-urile pentru toate optiunile care se pot selectat
	private List<Integer> id_categorieAll = new ArrayList<Integer>();
	private Map<Integer, List<Integer>> id_atributAll = new HashMap<Integer, List<Integer>>();
	private List<Integer> id_producatorAll = new ArrayList<Integer>();
	private List<Integer> id_etalonAll = new ArrayList<Integer>();
	private List<Double> pret_produsAll = new ArrayList<Double>();
	private List<Integer> cantitate_produsAll = new ArrayList<Integer>();
		
	private boolean inStock = false;	
	
	public String getCrumb() {
		return crumb;
	}


	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}


	public List<Integer> getId_categorieAll() {
		return id_categorieAll;
	}


	public void setId_categorieAll(List<Integer> idCategorieAll) {
		id_categorieAll = idCategorieAll;
	}


	public Map<Integer, List<Integer>> getId_atributAll() {
		return id_atributAll;
	}


	public void setId_atributAll(Map<Integer, List<Integer>> idAtributAll) {
		id_atributAll = idAtributAll;
	}


	public List<Integer> getId_producatorAll() {
		return id_producatorAll;
	}


	public void setId_producatorAll(List<Integer> idProducatorAll) {
		id_producatorAll = idProducatorAll;
	}


	public List<Integer> getId_etalonAll() {
		return id_etalonAll;
	}


	public void setId_etalonAll(List<Integer> idEtalonAll) {
		id_etalonAll = idEtalonAll;
	}


	public List<Double> getPret_produsAll() {
		return pret_produsAll;
	}


	public void setPret_produsAll(List<Double> pretProdusAll) {
		pret_produsAll = pretProdusAll;
	}


	public List<Integer> getCantitate_produsAll() {
		return cantitate_produsAll;
	}


	public void setCantitate_produsAll(List<Integer> cantitateProdusAll) {
		cantitate_produsAll = cantitateProdusAll;
	}


	public List<Double> getPret_produs() {
		return pret_produs;
	}


	public void setPret_produs(List<Double> pretProdus) {
		pret_produs = pretProdus;
	}


	public List<Integer> getCantitate_produs() {
		return cantitate_produs;
	}


	public void setCantitate_produs(List<Integer> cantitateProdus) {
		cantitate_produs = cantitateProdus;
	}


	public List<Integer> getId_categorie() {
		return id_categorie;
	}


	public void setId_categorie(List<Integer> idCategorie) {
		id_categorie = idCategorie;
	}


	public Map<Integer, List<Integer>> getId_atribut() {
		return id_atribut;
	}


	public void setId_atribut(Map<Integer, List<Integer>> idAtribut) {
		id_atribut = idAtribut;
	}


	public List<Integer> getId_producator() {
		return id_producator;
	}


	public void setId_producator(List<Integer> idProducator) {
		id_producator = idProducator;
	}


	public List<Integer> getId_etalon() {
		return id_etalon;
	}


	public void setId_etalon(List<Integer> idEtalon) {
		id_etalon = idEtalon;
	}

	public boolean isInStock() {
		return inStock;
	}


	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}


	@Override
	public String getSqlFromFilter() {
		Integer idProducator = 0;
		Integer idCategorie = 0;
		Integer idEtalon = 0;
		boolean prev = false;
		
		String query = "FROM Produs as produs "; 
		
		Iterator it = id_atribut.keySet().iterator();
		if (it.hasNext()){
			query += "WHERE ";
			prev = true;
		}
		while (it.hasNext()){
			log.debug("Adaugare filtru pentru atribute");
			
			Iterator it1 = id_atribut.get(it.next()).iterator();
			String inCondition = "in (";
			while (it1.hasNext()){
				inCondition += it1.next();
				if (it1.hasNext()){
					inCondition += ",";
				} else{
					inCondition += ")";
				}
			}
			String attrCatCondition = "select count(*) from Produs AS produs1 " +
																"INNER JOIN produs1.atributeProdus AS atribut_produs1 " +
																"INNER JOIN atribut_produs1.atribut AS atribut " +
																"WHERE atribut.id " + inCondition + " " +
																	   "AND produs1.id = produs.id";
			query += "("+ attrCatCondition +") > 0";
			if (it.hasNext()){
				query += " AND ";
			}
		}
		
		if (!id_categorie.isEmpty()){
			log.debug("Adaugare filtru pentru categorii!");
			if (prev){
				query += " AND ";
			} else{
				query += " WHERE ";
				prev = true;
			}
			
			Iterator itCat = id_categorie.iterator();
			query += "(";
			while (itCat.hasNext()){
				query += "produs.categorie.id = " + itCat.next();
				if (itCat.hasNext()){
					query += " OR ";
				}
			} 
			query += ")";
		}
				
		if (!id_producator.isEmpty()){
			log.debug("Adaugare filtru pentru producator!");
			idProducator = id_producator.get(0);
			if (prev){
				query += " AND ";
			} else{
				query += " WHERE ";
				prev = true;
			}			
			query += "produs.producator.id = " + idProducator;
		}
	
		if (!id_etalon.isEmpty()){
			log.debug("Adaugare filtru pentru etalon!");
			idEtalon = id_etalon.get(0);
			if (prev){
				query += " AND ";
			} else{
				query += " WHERE ";
				prev = true;
			}			
			query += "produs.etalon.id = " + idEtalon + " ";
		}
		
		if (inStock){
			log.debug("Adaugare filtru pentru produsele in stoc!");
			if (prev){
				query += " AND ";
			} else{
				query += " WHERE ";
				prev = true;
			}			
			query += "produs.cantitate > 0";
		}
		
		if (crumb != null && !crumb.isEmpty()){
			log.debug("Adaugare filtru pentru cautare produse dupa textul introdus: " + crumb);
			if (prev){
				query += " AND ";
			} else{
				query += " WHERE ";
				prev = true;
			}	
			
			crumb = crumb.toLowerCase();
			query += "(lower(produs.nume) like '%" + crumb + "%' OR lower(produs.descriere) like '%" + crumb + "%')";
		}
		
		return query;
	}


	@Override
	public Criterion getCriterionFromFilter() {
		Criterion criterion = null;
		Criterion tmp_criterion = null;
		Iterator<Integer> it;
		Iterator<Integer> it1;
		
		log.debug("Calculare criterion pentru FilterProdus");
		
		if (!id_categorie.isEmpty()){
			log.debug("Adaugare filtru pentru categorii");
			it = id_categorie.iterator();
			
			tmp_criterion = Restrictions.eq(ID_CATEGORIE, it.next());
			
			while (it.hasNext())
			{
				tmp_criterion = Restrictions.or(tmp_criterion, Restrictions.eq(ID_CATEGORIE, it.next()));
			}
			
			criterion = tmp_criterion;
		}
		
		it = id_atribut.keySet().iterator();
		while (it.hasNext()){
			log.debug("Adaugare filtru pentru atribute");
			it1 = id_atribut.get(it.next()).iterator();
			
			if (it1.hasNext()){
				tmp_criterion = Restrictions.eq(ID_ATRIBUT, it1.next());
				while (it1.hasNext()){
					tmp_criterion = Restrictions.or(tmp_criterion, Restrictions.eq(ID_ATRIBUT, it1.next()));
				}
				
				if (criterion != null){
					criterion = Restrictions.and(criterion, tmp_criterion);
				} else {
					criterion = tmp_criterion;
				}
			}
		}
		

		if (!id_producator.isEmpty()){
			log.debug("Adaugare filtru pentru producatori");
			it = id_producator.iterator();
			tmp_criterion = Restrictions.eq(ID_PRODUCATOR, it.next());
			while (it.hasNext())
				tmp_criterion = Restrictions.or(tmp_criterion, Restrictions.eq(ID_PRODUCATOR, it.next()));
			
			if (criterion != null)
				criterion = Restrictions.and(criterion, tmp_criterion);
			else
				criterion = tmp_criterion;
		}
		
		if (!id_etalon.isEmpty()){
			log.debug("Adaugare filtru pentru etaloane");
			
			it = id_etalon.iterator();
			
			tmp_criterion = Restrictions.eq(ID_ETALON, it.next());
			while (it.hasNext())
				tmp_criterion = Restrictions.or(tmp_criterion, Restrictions.eq(ID_ETALON, it.next()));
			
			if (criterion != null)
				criterion = Restrictions.and(criterion, tmp_criterion);
			else
				criterion = tmp_criterion;
		}
		
		if (pret_produs.size() == 2){
			log.debug("Adaugare filtru pentru pret");
			
			tmp_criterion = Restrictions.between(PRET_PRODUS, pret_produs.get(0), pret_produs.get(1));
			
			if (criterion != null)
				criterion = Restrictions.and(criterion, tmp_criterion);
			else
				criterion = tmp_criterion;
		}
		

		if (cantitate_produs.size() == 2){
			log.debug("Adaugare filtru pentru cantitate");
			tmp_criterion = Restrictions.between(CANTITATE_PRODUS, cantitate_produs.get(0), cantitate_produs.get(1));
			
			if (criterion != null)
				criterion = Restrictions.and(criterion, tmp_criterion);
			else
				criterion = tmp_criterion;
		}			
		
		if (inStock){
			log.debug("Adaugare filtru pentru produsele in stoc!");
			tmp_criterion = Restrictions.gt(CANTITATE_PRODUS, new Integer(0));
			
			if (criterion != null)
				criterion = Restrictions.and(criterion, tmp_criterion);
			else
				criterion = tmp_criterion;
		}
		
		return criterion;
	}


	@Override
	public String toString() {
		return "FilterProdus [cantitate_produs=" + cantitate_produs
				+ ", id_atribut=" + id_atribut + ", id_categorie="
				+ id_categorie + ", id_etalon=" + id_etalon
				+ ", id_producator=" + id_producator + ", pret_produs="
				+ pret_produs + "]";
	}

}
