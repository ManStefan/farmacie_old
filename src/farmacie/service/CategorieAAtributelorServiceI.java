package farmacie.service;

import java.util.List;

import farmacie.model.Atribut;
import farmacie.model.CategorieAAtributelor;

public interface CategorieAAtributelorServiceI {
	
	public void addCategorieAAtributelor(CategorieAAtributelor categorieAAtributelor, List<String> errors);
	public void deleteCategorieAAtributelor(CategorieAAtributelor categorieAAtributelor, List<String> errors);
	public List<CategorieAAtributelor> searchCategorieAAtributelor(String crumb, int nrOfRec);
	public void updateCategorieAAtributelor(CategorieAAtributelor newCat, List<String> errors);
	public long numberOfSearchedResults(String crumb);
	public List<CategorieAAtributelor> getCategorieAAtributelorById(int id);
	public List<CategorieAAtributelor> getCategorieAAtributelorByNume(String nume);	
	public List<Atribut> getAtributForCategory(int attrCatId);
}
