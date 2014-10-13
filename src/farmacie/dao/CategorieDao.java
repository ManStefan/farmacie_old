package farmacie.dao;

import java.util.List;

import farmacie.model.Categorie;

public interface CategorieDao {

	public void addCategorie(Categorie categorie, List<String> errors);
	public void deleteCategorie(Categorie categorie, List<String> errors);
	public void updateCategorie(Categorie categorie, List<String> errors);
	public List<Categorie> searchCategorie(String crumb, String nivel, int frunza, int nrOfRecords);
	public List<Categorie> searchCategorie(String crumb, String nivel, int nrOfRecords);
	public List<Categorie> getCategorieById(int id);
	public List<Categorie> getCategorieByNume(String nume);
	public List<Categorie> getCategorieByNivel(int nivel);
	public List<Integer> getIdCategorieByNivel(int nivel);
	public List<Categorie> getToateCategoriile();
}
