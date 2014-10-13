package farmacie.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import farmacie.dao.EtalonDao;

import farmacie.model.Etalon;
import farmacie.model.Produs;
import farmacie.service.EtalonServiceI;
import farmacie.statics.FarmacieConstants;

@Service
public class EtalonService implements FarmacieConstants, EtalonServiceI {

	@Autowired
	EtalonDao etalonDao;
	
	@Override
	@Transactional
	public void addEtalon(Etalon etalon, List<String> errors) {
		etalonDao.addEtalon(etalon, errors);
	}

	@Override
	@Transactional
	public void deleteEtalon(Etalon etalon, List<String> errors) {
		etalonDao.deleteEtalon(etalon, errors);
	}

	@Override
	@Transactional
	public List<Etalon> getEtalonById(int id) {
		return etalonDao.getEtalonById(id);
	}

	@Override
	@Transactional
	public List<Etalon> getEtalonByNume(String nume) {
		return etalonDao.getEtalonByNume(nume);
	}

	@Override
	@Transactional
	public List<Produs> getProdusByIdEtalon(int idEtalon) {		
		return etalonDao.getEtalonById(idEtalon).get(0).getProduse();
	}


	@Override
	@Transactional
	public void updateEtalon(Etalon etalon, List<String> errors) {
		etalonDao.updateEtalon(etalon, errors);
		
	}

	@Override
	@Transactional
	public List<Etalon> searchEtalonByName(String crumb, int nrOfRec) {
		return etalonDao.searchEtalonByName(crumb, nrOfRec);
	}

}
