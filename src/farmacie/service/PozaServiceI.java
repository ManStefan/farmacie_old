package farmacie.service;

import java.util.List;

import farmacie.model.Poza;
import farmacie.model.Produs;

public interface PozaServiceI {
	
	public void addPoza(Poza poza, List<String> errors);
	public void deletePoza(Poza poza, List<String> errors);
	public List<Poza> getPozaByIdPoza(int id);	
	public List<Poza> getPozaByIdProdus(int id_prod);
	public Produs getProdusByIdPoza(int id_poza);
}
