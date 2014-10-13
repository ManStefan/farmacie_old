package farmacie.dao;

import java.util.List;

import farmacie.model.Poza;

public interface PozaDao {

	public void addPoza(Poza poza, List<String> errors);
	public void deletePoza(Poza poza, List<String> errors);
	public List<Poza> getPozaByIdPoza(int id);
	public List<Poza> getPozaByIdProdus(int id_prod);
}
