package farmacie.dao;

import java.util.List;

import farmacie.model.Atribut;
import farmacie.model.CategorieAAtributelor;

public interface CategorieAAtributelorDao {

	public void addCategorieAAtributelor(CategorieAAtributelor categorieAAtributelor, List<String> errors);
	public void deleteCategorieAAtributelor(CategorieAAtributelor categorieAAtributelor, List<String> errors);
	public List<CategorieAAtributelor> searchCategorieAAtributelor(String crumb, int nrOfRec);
	public long numberOfSearchedResults(String crumb);
	public List<CategorieAAtributelor> getCategorieAAtributelorById(int id);
	public List<CategorieAAtributelor> getCategorieAAtributelorByNume(String nume);
	public void updateCategorieAAtributelor(CategorieAAtributelor newCat, List<String> errors);
	public List<Atribut> getAtributForCategory(int attrCatId);
}
