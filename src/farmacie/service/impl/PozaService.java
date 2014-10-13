package farmacie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import farmacie.dao.PozaDao;

import farmacie.model.Poza;
import farmacie.model.Produs;
import farmacie.service.PozaServiceI;
import farmacie.statics.FarmacieConstants;

@Service
public class PozaService implements PozaServiceI, FarmacieConstants {

	@Autowired
	PozaDao pozaDao;
	
	@Override
	@Transactional
	public void addPoza(Poza poza, List<String> errors) {
		pozaDao.addPoza(poza, errors);
	}

	@Override
	@Transactional
	public void deletePoza(Poza poza, List<String> errors) {
		pozaDao.deletePoza(poza, errors);
	}

	@Override
	@Transactional
	public List<Poza> getPozaByIdPoza(int id) {
		return pozaDao.getPozaByIdPoza(id);
	}

	@Override
	@Transactional
	public List<Poza> getPozaByIdProdus(int idProd) {
		return pozaDao.getPozaByIdProdus(idProd);
	}

	@Override
	@Transactional
	public Produs getProdusByIdPoza(int idPoza) {		
		return pozaDao.getPozaByIdPoza(idPoza).get(0).getProdus();
	}
	
	
}
