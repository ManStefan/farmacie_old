package farmacie.service;

import java.util.List;

import farmacie.model.Etalon;
import farmacie.model.Produs;

public interface EtalonServiceI {
	
	public void addEtalon(Etalon etalon, List<String> errors);
	public void deleteEtalon(Etalon etalon, List<String> errors);
	public void updateEtalon(Etalon etalon, List<String> errors);
	public List<Etalon> getEtalonById(int id);
	public List<Etalon> getEtalonByNume(String nume);	
	public List<Produs> getProdusByIdEtalon(int id_etalon);
	public List<Etalon> searchEtalonByName(String crumb, int nrOfRec);
}
