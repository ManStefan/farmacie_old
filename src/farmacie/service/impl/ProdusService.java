package farmacie.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import farmacie.dao.ProdusDao;

import farmacie.model.Atribut;
import farmacie.model.Atribut_produs;
import farmacie.model.Categorie;
import farmacie.model.Etalon;
import farmacie.model.Poza;
import farmacie.model.Producator;
import farmacie.model.Produs;
import farmacie.model.view.ProdusViewFull;
import farmacie.model.view.ProdusViewLite;
import farmacie.service.ProdusServiceI;
import farmacie.statics.FarmacieConstants;

@Service
public class ProdusService extends ServiceHelper implements FarmacieConstants, ProdusServiceI {

	@Autowired
	ProdusDao produsDao;
	
	@Override
	@Transactional
	public void addProdus(Produs produs, List<String> errors) {
		produsDao.addProdus(produs, errors);

	}

	@Override
	@Transactional
	public void deleteProdus(Produs produs, List<String> errors) {
		produsDao.deleteProdus(produs, errors);
	}

	@Override
	@Transactional
	public List<Produs> getProduseByCantitate(int cantMin, int cantMax) {
		return produsDao.getProduseByCantitate(cantMin, cantMax);
	}

	@Override
	@Transactional
	public List<Produs> getProduseByData(Date dataMin, Date dataMax) {
		return produsDao.getProduseByData(dataMin, dataMax);
	}

	@Override
	@Transactional
	public List<Produs> getProduseById(int id) {
		return produsDao.getProduseById(id);
	}

	@Override
	@Transactional
	public List<Produs> getProduseByNume(String nume) {
		return produsDao.getProduseByNume(nume);
	}

	@Override
	@Transactional
	public List<Produs> getProduseByPret(double pretMin, double pretMax) {
		return produsDao.getProduseByPret(pretMin, pretMax);
	}

	@Override
	@Transactional
	public void updateProdus(Produs produs, List<String> errors) {
		produsDao.updateProdus(produs, errors);
	}

	/**
	 * Metoda care returneaza lista cu atribute a produsului cu id-ul idProd
	 */
	@Override
	@Transactional
	public List<Atribut> getAtributByIdProdus(int idProd) {
		List<Atribut> list_a = new ArrayList<Atribut>();
		
		List<Atribut_produs> list_a_p = produsDao.getProduseById(idProd).get(0).getAtributeProdus();
		Iterator<Atribut_produs> it = list_a_p.iterator();
		while (it.hasNext())
			list_a.add(it.next().getAtribut());
		
		return list_a;
	}

	@Override
	@Transactional
	public Categorie getCategorieByIdProdus(int idProd) {
		
		return produsDao.getProduseById(idProd).get(0).getCategorie();
	}

	@Override
	@Transactional
	public Etalon getEtalonByIdProdus(int idProd) {
		
		return produsDao.getProduseById(idProd).get(0).getEtalon();
	}

	@Override
	@Transactional
	public Producator getProducatorByIdProdus(int idProd) {
		return produsDao.getProduseById(idProd).get(0).getProducator();
	}
	
	
	@Override
	@Transactional
	public List<Poza> getPozaByIdProdus(int idProd) {
		return produsDao.getProduseById(idProd).get(0).getPoza();
	}

	@Override
	@Transactional
	public List<Produs> getProduseOrderedByDateAsc() {
		return produsDao.getProduseOrderedByDateAsc();
	}

	@Override
	@Transactional
	public ProdusViewFull getProdusViewFullByIdProdus(int idProd, int nrCarDescriere) {
		ProdusViewFull prod_view = new ProdusViewFull(nrCarDescriere);
		
		prod_view.setId(idProd); 
		prod_view.setCantitate(produsDao.getProduseById(idProd).get(0).getCantitate());
		prod_view.setData_inreg(produsDao.getProduseById(idProd).get(0).getDataCreeare());
		prod_view.setNume(produsDao.getProduseById(idProd).get(0).getNume());
		prod_view.setPret(produsDao.getProduseById(idProd).get(0).getPret());
		prod_view.setDescriere(produsDao.getProduseById(idProd).get(0).getDescriere());
		
		for (Atribut atribut : getAtributByIdProdus(idProd)){
			if (atribut.getCategorieAAtributelor() != null)
				if (!prod_view.getNumeAtribute().containsKey(atribut.getCategorieAAtributelor().getNume()))
					prod_view.getNumeAtribute().put(atribut.getCategorieAAtributelor().getNume(), new ArrayList<String>());
				prod_view.getNumeAtribute().get(atribut.getCategorieAAtributelor().getNume()).add(atribut.getNume());
		}
		
		prod_view.getNumeCategorii().add(getCategorieByIdProdus(idProd).getNume());
		prod_view.getNumeEtaloane().add(getEtalonByIdProdus(idProd).getNume());
		
		for (Poza poza : getPozaByIdProdus(idProd))
		{
			prod_view.getNumePoze().add(poza.getPoza());
			prod_view.getDescrierePoze().add(poza.getDescriere());
		}
		
		prod_view.getNumeProducatori().add(getProducatorByIdProdus(idProd).getNume());		

		return prod_view;
	}



	/**
	 * Metoda care returneaza lista cu atribute a produsului cu id-ul idProd
	 */
	@Override
	@Transactional
	public List<Atribut> getAtributByProdus(Produs produs) {
		List<Atribut> list_a = new ArrayList<Atribut>();
		
		List<Atribut_produs> list_a_p = produs.getAtributeProdus();
		Iterator<Atribut_produs> it = list_a_p.iterator();
		while (it.hasNext())
			list_a.add(it.next().getAtribut());
		
		return list_a;
	}

	@Override
	@Transactional
	public Categorie getCategorieByProdus(Produs produs) {
		
		return produs.getCategorie();
	}

	@Override
	@Transactional
	public Etalon getEtalonByProdus(Produs produs) {
		return produs.getEtalon();
	}

	@Override
	@Transactional
	public List<Poza> getPozaByProdus(Produs produs) {
		List<Poza> poze = new ArrayList<Poza>();
		
		for (Poza poza : produsDao.getProduseById(produs.getId()).get(0).getPoza()){
			poze.add(poza);
		}
		return poze;
	}	
	
	@Override
	@Transactional
	public ProdusViewFull getProdusViewFullByProdus(Produs produs, int nrCarDescriere) {
		ProdusViewFull prod_view = new ProdusViewFull(nrCarDescriere);
		
		prod_view.setId(produs.getId()); 
		prod_view.setCantitate(produs.getCantitate());
		prod_view.setData_inreg(produs.getDataCreeare());
		prod_view.setNume(produs.getNume());
		prod_view.setPret(produs.getPret());
		prod_view.setDescriere(produs.getDescriere());
		
		for (Atribut atribut : getAtributByProdus(produs)){
			prod_view.getNumeAtribute().get(atribut.getCategorieAAtributelor().getNume()).add(atribut.getNume());
		}
		
		prod_view.getNumeCategorii().add(getCategorieByProdus(produs).getNume());
		prod_view.getNumeEtaloane().add(getEtalonByProdus(produs).getNume());
		
		for (Poza poza : getPozaByProdus(produs)){
			prod_view.getNumePoze().add(poza.getPoza());
			prod_view.getDescrierePoze().add(poza.getDescriere());
		}

		return prod_view;
	}

	@Override
	@Transactional
	public ProdusViewLite getProdusViewLiteByIdProdus(int idProd, int nrCarDescriere) {
		ProdusViewLite prod_view = new ProdusViewLite(nrCarDescriere);
		
		prod_view.setId(idProd); 
		prod_view.setCantitate(produsDao.getProduseById(idProd).get(0).getCantitate());
		prod_view.setNume(produsDao.getProduseById(idProd).get(0).getNume());
		prod_view.setPret(produsDao.getProduseById(idProd).get(0).getPret());
		prod_view.setDescriere(produsDao.getProduseById(idProd).get(0).getDescriere());
		
		for (Poza poza : getPozaByIdProdus(idProd))
		{
			prod_view.getNumePoze().add(poza.getPoza());
		}
		
		return prod_view;
	}

	@Override
	@Transactional
	public ProdusViewLite getProdusViewLiteByProdus(Produs produs, int nrCarDescriere) {
		ProdusViewLite prod_view = new ProdusViewLite(nrCarDescriere);
		
		prod_view.setId(produs.getId()); 
		prod_view.setCantitate(produs.getCantitate());
		prod_view.setNume(produs.getNume());
		prod_view.setPret(produs.getPret());
		prod_view.setDescriere(produs.getDescriere());
		
		for (Poza poza : getPozaByProdus(produs)){
			prod_view.getNumePoze().add(poza.getPoza());
		}

		return prod_view;
	}
	
	@Override
	@Transactional
	public List<Produs> searchProduseSimplu(String crumb, Integer idCategorie, int nrOfRecords){
		return produsDao.searchProduseSimplu(crumb, idCategorie, nrOfRecords);
	}
	
	@Override
	@Transactional
	public List<Atribut> getAtributeForProdus(Produs produs){
		List<Atribut> atribute = new ArrayList<Atribut>();
		
		for (Atribut_produs atribut_produs : produsDao.getProduseById(produs.getId()).get(0).getAtributeProdus()){
			atribute.add(atribut_produs.getAtribut());
		}
		
		return atribute;
	}
	
	@Override
	@Transactional
	public Categorie getCategorieForProdus(Produs produs){	
/*		Categorie proxyCat = produsDao.getProduseById(produs.getId()).get(0).getCategorie();
		Categorie categorie = new Categorie();
		categorie.setFrunza(proxyCat.getFrunza());
		categorie.setNivel(proxyCat.getNivel());
		categorie.setNume(proxyCat.getNume());
		
		return categorie;*/
		
		return unproxy(produsDao.getProduseById(produs.getId()).get(0).getCategorie());
	}
	
	@Override
	@Transactional
	public Producator getProducatorForProdus(Produs produs){
/*		Producator proxyProd = produsDao.getProduseById(produs.getId()).get(0).getProducator();
		Producator prod = new Producator();
		prod.setNume(proxyProd.getNume());
		
		return prod;*/
		
		return unproxy(produsDao.getProduseById(produs.getId()).get(0).getProducator());
	}
	
	@Override
	@Transactional
	public Etalon getEtalonForProdus(Produs produs){
/*		Etalon proxyEtalon = produsDao.getProduseById(produs.getId()).get(0).getEtalon();
		Etalon etalon = new Etalon();
		etalon.setNume(proxyEtalon.getNume());
		
		return etalon;*/
		return unproxy(produsDao.getProduseById(produs.getId()).get(0).getEtalon());
	}

	@Override
	@Transactional
	public boolean existaProduseCuCategorie(int idCategorie) {
		return produsDao.existaProduseCuCategorie(idCategorie);
	}

	@Override
	@Transactional
	public boolean existaProduseCuEtalon(int idEtalon) {
		return produsDao.existaProduseCuEtalon(idEtalon);
	}

	@Override
	@Transactional
	public boolean existaProduseCuProducator(int idProducator) {
		return produsDao.existaProduseCuProducator(idProducator);
	}

	@Override
	@Transactional
	public int stergeProduseleDinCategoria(int idCat, List<String> errors) {
		return produsDao.stergeProduseleDinCategoria(idCat, errors);
	}
	
	@Override
	@Transactional
	public void mergeProdus(Produs produs, List<String> errors){
		produsDao.mergeProdus(produs, errors);
	}

	@Override
	@Transactional
	public int deleteProdus(int id, List<String> errors) {
		return produsDao.deleteProdus(id, errors);
	}

	@Override
	@Transactional
	public Map<Integer, List<Integer>> getNewestNProdsByCat(int n) {
		return produsDao.getNewestNProdsByCat(n);
	}

	@Override
	@Transactional
	public List<Integer> getNewestProdsInCategories(List<Integer> idCategories,
			int n) {
		return produsDao.getNewestProdsInCategories(idCategories, n);
	}
}
