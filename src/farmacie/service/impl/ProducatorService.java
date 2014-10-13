package farmacie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import farmacie.dao.ProducatorDao;

import farmacie.model.Producator;
import farmacie.model.Produs;
import farmacie.service.ProducatorServiceI;
import farmacie.statics.FarmacieConstants;

@Service
public class ProducatorService implements FarmacieConstants, ProducatorServiceI {

	@Autowired
	ProducatorDao producatorDao;
	
	@Override
	@Transactional
	public void addProducator(Producator producator, List<String> errors) {
		producatorDao.addProducator(producator, errors);
	}

	@Override
	@Transactional
	public void deleteProducator(Producator producator, List<String> errors) {
		producatorDao.deleteProducator(producator, errors);
	}

	@Override
	@Transactional
	public List<Producator> getProducatorById(int id) {
		return producatorDao.getProducatorById(id);
	}

	@Override
	@Transactional
	public List<Producator> getProducatorByNume(String nume) {
		return producatorDao.getProducatorByNume(nume);
	}

	@Override
	@Transactional
	public List<Produs> getProdusByIdProducator(int idProducator) {
		
		return producatorDao.getProducatorById(idProducator).get(0).getProduse();

	}

	@Override
	@Transactional
	public void updateProducator(Producator producator, List<String> errors) {
		producatorDao.updateProducator(producator, errors);
		
	}

	@Override
	@Transactional
	public List<Producator> searchProducatorByName(String crumb, int nrOfRec) {
		return producatorDao.searchProducatorByName(crumb, nrOfRec);
	}

}
