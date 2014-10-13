package farmacie.service;

import java.util.List;

import farmacie.model.Categorie;
import farmacie.model.Produs;

public interface CategorieServiceI {
	
	public void addCategorie(Categorie categorie, List<String> errors);
	public void deleteCategorie(Categorie categorie, List<String> errors);
	public void updateCategorie(Categorie categorie, List<String> errors);
	public List<Categorie> getCategorieById(int id);
	public List<Categorie> getCategorieByNume(String nume);	
	public List<Produs> getProdusByIdCategorie(int id_categorie);
	public List<Produs> getProdusByCategorie(Categorie categorie);
	public List<Categorie> getCategorieCopiiByIdCategorie(int id_cat_parinte);
	public List<Integer> getIdCategorieCopiiByIdCategorie(int id_cat_parinte);
	public List<Integer> getIdToateSubcategoriile(int idCatParinte);
	public List<Categorie> getEncodedArbore_categorie();
	public List<Categorie> getToateCategoriile();
	public List<Categorie> getCategorieByNivel(int nivel);
	public List<Integer> getIdCategorieByNivel(int nivel);
	public List<Categorie> searchCategorie(String crumb, String nivel, int frunza, int nrOfRecords);
	public List<Categorie> searchCategorie(String crumb, String nivel, int nrOfRecords);
}
