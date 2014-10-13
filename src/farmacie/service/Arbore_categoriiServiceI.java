package farmacie.service;

import java.util.List;

import farmacie.Exceptii.FarmacieException;

import farmacie.model.Arbore_categorii;

public interface Arbore_categoriiServiceI {
	public void addArbore_categorii(Arbore_categorii arbore_categorii, List<String> errors);
	public void deleteArbore_categorii(Arbore_categorii arbore_Categorii, List<String> errors);
	public List<Arbore_categorii> getArbore_categoriiById_cat_parinte(int id_cat_parinte);
	public List<Arbore_categorii> getArbore_categoriiById_cat_copil(int id_cat_copil);
	public List<Integer> getCathegoriesUnderCat(int idCat);
}
