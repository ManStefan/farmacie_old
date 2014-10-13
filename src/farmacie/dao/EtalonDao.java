package farmacie.dao;

import java.util.List;

import farmacie.model.Etalon;

public interface EtalonDao {

	public void addEtalon(Etalon etalon, List<String> errors);
	public void deleteEtalon(Etalon etalon, List<String> errors);
	public void updateEtalon(Etalon etalon, List<String> errors);
	public List<Etalon> getEtalonById(int id);
	public List<Etalon> getEtalonByNume(String nume);
	public List<Etalon> searchEtalonByName(String crumb, int nrOfRec);
}
