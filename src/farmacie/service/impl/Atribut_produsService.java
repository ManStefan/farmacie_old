package farmacie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import farmacie.dao.Atribut_produsDao;

import farmacie.model.Atribut_produs;
import farmacie.model.Produs;
import farmacie.service.Atribut_produsServiceI;
import farmacie.statics.FarmacieConstants;

@Service
public class Atribut_produsService implements FarmacieConstants,
		Atribut_produsServiceI {

	@Autowired
	Atribut_produsDao atribut_produsDao;
	
	@Override
	@Transactional
	public void addAtribut_produs(Atribut_produs atributProdus, List<String> errors) {
		atribut_produsDao.addAtribut_produs(atributProdus, errors);
	}

	@Override
	@Transactional
	public void deleteAtribut_produs(Atribut_produs atributProdus, List<String> errors) {
		atribut_produsDao.deleteAtribut_produs(atributProdus, errors);

	}

	@Override
	@Transactional
	public List<Atribut_produs> getAtribut_produsById_atribut(int idAtribut) {
		return atribut_produsDao.getAtribut_produsById_atribut(idAtribut);
	}

	@Override
	@Transactional
	public List<Atribut_produs> getAtribut_produsById_prod(int idProd) {
		return atribut_produsDao.getAtribut_produsById_prod(idProd);
	}

	@Override
	@Transactional
	public int deleteAllAtribut_produsByProdus(Produs produs,
			List<String> errors) {
		return atribut_produsDao.deleteAllAtribut_produsByProdus(produs, errors);
	}

}
