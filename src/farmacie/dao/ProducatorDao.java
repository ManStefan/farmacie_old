package farmacie.dao;

import java.util.List;

import farmacie.model.Producator;

public interface ProducatorDao {

	public void addProducator(Producator producator, List<String> errors);
	public void deleteProducator(Producator producator, List<String> errors);
	public void updateProducator(Producator producator, List<String> errors);
	public List<Producator> searchProducatorByName(String crumb, int nrOfRec);
	public List<Producator> getProducatorById(int id);
	public List<Producator> getProducatorByNume(String nume);
}
