package farmacie.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import farmacie.model.Produs;

public interface ProdusDao {

	public void addProdus(Produs produs, List<String> errors);
	public void updateProdus(Produs produs, List<String> errors);
	public void mergeProdus(Produs produs, List<String> errors);
	public List<Produs> getProduseById(int id);
	public List<Produs> getProduseByNume(String nume);
	public List<Produs> getProduseByPret(double pret_min, double pret_max);
	public List<Produs> getProduseByCantitate(int cant_min, int cant_max);
	public List<Produs> getProduseByData(Date data_min, Date data_max);
	public List<Produs> getProduseOrderedByDateAsc();
	public List<Integer> getNewestProdsInCategories(List<Integer> idCategories, int n);
	public void deleteProdus(Produs produs, List<String> errors);
	public List<Produs> searchProduseSimplu(String crumb, Integer idCategorie, int nrOfRecords);
	public int deleteProdus(int id, List<String> errors);
	
	public boolean existaProduseCuEtalon(int idEtalon);
	public boolean existaProduseCuProducator(int idProducator);
	public boolean existaProduseCuCategorie(int idCategorie);
	public int stergeProduseleDinCategoria(int idCat, List<String> errors);
	public Map<Integer, List<Integer>> getNewestNProdsByCat(int n);
}
