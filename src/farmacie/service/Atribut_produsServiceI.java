package farmacie.service;

import java.util.List;

import farmacie.model.Atribut_produs;
import farmacie.model.Produs;

public interface Atribut_produsServiceI {
	public void addAtribut_produs(Atribut_produs atribut_produs, List<String> errors);
	public void deleteAtribut_produs(Atribut_produs atribut_produs, List<String> errors);
	public List<Atribut_produs> getAtribut_produsById_prod(int id_prod);
	public List<Atribut_produs> getAtribut_produsById_atribut(int id_atribut);
	public int deleteAllAtribut_produsByProdus(Produs produs, List<String> errors);
}
