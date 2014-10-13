package farmacie.service;

import java.util.List;

import farmacie.model.Producator;
import farmacie.model.Produs;

public interface ProducatorServiceI {
	
	public void addProducator(Producator producator, List<String> errors);
	public void deleteProducator(Producator producator, List<String> errors);
	public void updateProducator(Producator producator, List<String> errors);
	public List<Producator> searchProducatorByName(String crumb, int nrOfRec);
	public List<Producator> getProducatorById(int id);
	public List<Producator> getProducatorByNume(String nume);	
	public List<Produs> getProdusByIdProducator(int id_etalon);
}
