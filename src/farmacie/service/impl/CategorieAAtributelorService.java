package farmacie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import farmacie.dao.CategorieAAtributelorDao;

import farmacie.model.Atribut;
import farmacie.model.CategorieAAtributelor;

import farmacie.service.CategorieAAtributelorServiceI;
import farmacie.statics.FarmacieConstants;

@Service
public class CategorieAAtributelorService implements FarmacieConstants, CategorieAAtributelorServiceI {

	@Autowired
	CategorieAAtributelorDao categorieAAtributelorDao;
	
	@Override
	@Transactional
	public void addCategorieAAtributelor(CategorieAAtributelor categorieAAtributelor, List<String> errors) {
		categorieAAtributelorDao.addCategorieAAtributelor(categorieAAtributelor, errors);
	}

	@Override
	@Transactional
	public void deleteCategorieAAtributelor(CategorieAAtributelor categorieAAtributelor, List<String> errors) {
		categorieAAtributelorDao.deleteCategorieAAtributelor(categorieAAtributelor, errors);
	}

	@Override
	@Transactional
	public List<CategorieAAtributelor> getCategorieAAtributelorById(int id) {
		return categorieAAtributelorDao.getCategorieAAtributelorById(id);
	}

	@Override
	@Transactional
	public List<CategorieAAtributelor> getCategorieAAtributelorByNume(
			String nume) {
		return categorieAAtributelorDao.getCategorieAAtributelorByNume(nume);
	}

	@Override
	@Transactional
	public long numberOfSearchedResults(String crumb) {
		return categorieAAtributelorDao.numberOfSearchedResults(crumb);
	}

	@Override
	@Transactional
	public List<CategorieAAtributelor> searchCategorieAAtributelor(
			String crumb, int nrOfRec) {
		return categorieAAtributelorDao.searchCategorieAAtributelor(crumb, nrOfRec);
	}

	@Override
	@Transactional
	public void updateCategorieAAtributelor(CategorieAAtributelor newCat,
			List<String> errors) {
		categorieAAtributelorDao.updateCategorieAAtributelor(newCat, errors);
		
	}

	@Override
	@Transactional
	public List<Atribut> getAtributForCategory(int attrCatId) {
		return categorieAAtributelorDao.getAtributForCategory(attrCatId);
	}

}
