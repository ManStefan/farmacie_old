package farmacie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import farmacie.Exceptii.FarmacieException;
import farmacie.dao.Arbore_categoriiDao;

import farmacie.model.Arbore_categorii;
import farmacie.service.Arbore_categoriiServiceI;
import farmacie.statics.FarmacieConstants;

@Service
public class Arbore_categoriiService implements Arbore_categoriiServiceI,
		FarmacieConstants {

	@Autowired
	Arbore_categoriiDao arbore_categoriiDao;
	
	@Override
	@Transactional
	public void addArbore_categorii(Arbore_categorii arboreCategorii, List<String> errors) {
		arbore_categoriiDao.addArbore_categorii(arboreCategorii, errors);
	}

	@Override
	@Transactional
	public void deleteArbore_categorii(Arbore_categorii arboreCategorii, List<String> errors) {
		arbore_categoriiDao.deleteArbore_categorii(arboreCategorii, errors);
	}

	@Override
	@Transactional
	public List<Arbore_categorii> getArbore_categoriiById_cat_copil(int idCatCopil) {
		return arbore_categoriiDao.getArbore_categoriiById_cat_copil(idCatCopil);
	}

	@Override
	@Transactional
	public List<Arbore_categorii> getArbore_categoriiById_cat_parinte(
			int idCatParinte) {
		return arbore_categoriiDao.getArbore_categoriiById_cat_parinte(idCatParinte);
		
	}

	@Override
	@Transactional
	public List<Integer> getCathegoriesUnderCat(int idCat) {
		return arbore_categoriiDao.getCathegoriesUnderCat(idCat);
	}
}
