package farmacie.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import farmacie.model.Atribut;
import farmacie.model.Categorie;
import farmacie.model.Etalon;
import farmacie.model.Poza;
import farmacie.model.Producator;
import farmacie.model.Produs;
import farmacie.model.view.ProdusViewFull;
import farmacie.model.view.ProdusViewLite;

public interface ProdusServiceI {
	public void addProdus(Produs produs, List<String> errors);
	public void deleteProdus(Produs produs, List<String> errors);
	public void updateProdus(Produs produs, List<String> errors);
	public List<Produs> getProduseById(int id);
	public List<Produs> getProduseByNume(String nume);
	public List<Produs> getProduseByPret(double pret_min, double pret_max);
	public List<Produs> getProduseByCantitate(int cant_min, int cant_max);
	public List<Produs> getProduseByData(Date data_min, Date data_max);
	public List<Produs> getProduseOrderedByDateAsc();
	public List<Atribut> getAtributeForProdus(Produs produs);
	public Categorie getCategorieForProdus(Produs produs);
	public Producator getProducatorForProdus(Produs produs);
	public Etalon getEtalonForProdus(Produs produs);
	public void mergeProdus(Produs produs, List<String> errors);
	public int deleteProdus(int id, List<String> errors);
	
	public List<Atribut> getAtributByIdProdus(int id_prod);
	public Categorie getCategorieByIdProdus(int id_prod);
	public Etalon getEtalonByIdProdus(int id_prod);
	public List<Poza> getPozaByIdProdus(int id_prod);
	public Producator getProducatorByIdProdus(int idProd);
	
	public ProdusViewFull getProdusViewFullByIdProdus(int id_prod, int nrCarDescriere);
	public ProdusViewLite getProdusViewLiteByIdProdus(int id_prod, int nrCarDescriere);

	public List<Atribut> getAtributByProdus(Produs produs);
	public Categorie getCategorieByProdus(Produs produs);
	public Etalon getEtalonByProdus(Produs produs);
	public List<Poza> getPozaByProdus(Produs produs);
	
	public ProdusViewFull getProdusViewFullByProdus(Produs produs, int nrCarDescriere);
	public ProdusViewLite getProdusViewLiteByProdus(Produs produs, int nrCarDescriere);
	
	public List<Produs> searchProduseSimplu(String crumb, Integer idCategorie, int nrOfRecords);
	public boolean existaProduseCuEtalon(int idEtalon);
	public boolean existaProduseCuProducator(int idProducator);
	public boolean existaProduseCuCategorie(int idCategorie);	
	
	public int stergeProduseleDinCategoria(int idCat, List<String> errors);
	public Map<Integer, List<Integer>> getNewestNProdsByCat(int n);
	
	public List<Integer> getNewestProdsInCategories(List<Integer> idCategories, int n);
}
