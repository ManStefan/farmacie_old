package farmacie.service;

import java.util.List;

import farmacie.model.Atribut;
import farmacie.model.Atribut_produs;
import farmacie.model.CategorieAAtributelor;
import farmacie.model.Produs;

public interface AtributServiceI {
	
	public void addAtribut(Atribut atribut, List<String> errors);
	public void deleteAtribut(Atribut atribut, List<String> errors);
	public List<Atribut> getAtributById(int id);
	public List<Atribut> getAtributByNume(String nume);	
	public List<Produs> getProdusByIdAtribut(int id_atribut);
	public List<Atribut> searchAtribut(String crumb, CategorieAAtributelor catAttr,int nrOfRec);
	public long numberOfSearchedResults(String crumb, CategorieAAtributelor catAttr);
	public void updateAtribut(Atribut atribut, List<String> errors);
	public List<Atribut_produs> getAtributProdusByIdAtribut(int id);
	public CategorieAAtributelor getCategorieAAtributului(Atribut atribut);
}
