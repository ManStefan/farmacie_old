package farmacie.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import farmacie.dao.AtributDao;

import farmacie.model.Atribut;
import farmacie.model.Atribut_produs;
import farmacie.model.CategorieAAtributelor;
import farmacie.model.Produs;
import farmacie.service.AtributServiceI;
import farmacie.statics.FarmacieConstants;

@Service
public class AtributService extends ServiceHelper implements FarmacieConstants, AtributServiceI {

	@Autowired
	AtributDao atributDao;
	
	@Override
	@Transactional
	public void addAtribut(Atribut atribut, List<String> errors) {
		atributDao.addAtribut(atribut, errors);
	}

	@Override
	@Transactional
	public void deleteAtribut(Atribut atribut, List<String> errors) {
		atributDao.deleteAtribut(atribut, errors);
	}

	@Override
	@Transactional
	public List<Atribut> getAtributById(int id) {
		return atributDao.getAtributById(id);
	}

	@Override
	@Transactional
	public List<Atribut> getAtributByNume(String nume) {
		return atributDao.getAtributByNume(nume);
	}

	@Override
	@Transactional
	public List<Produs> getProdusByIdAtribut(int idAtribut) {
		List<Produs> list_p = new ArrayList<Produs>();
		
		List<Atribut_produs> list_a_p = atributDao.getAtributById(idAtribut).get(0).getAtribute_produse();
		Iterator<Atribut_produs> it = list_a_p.iterator();
		while (it.hasNext())
			list_p.add(it.next().getProdus());
		
		return list_p;
	}

	@Override
	@Transactional
	public long numberOfSearchedResults(String crumb, CategorieAAtributelor catAttr) {
		return atributDao.numberOfSearchedResults(crumb, catAttr);
	}

	@Override
	@Transactional
	public List<Atribut> searchAtribut(String crumb, CategorieAAtributelor catAttr, int nrOfRec) {
		return atributDao.searchAtribut(crumb, catAttr ,nrOfRec);
	}

	@Override
	@Transactional
	public void updateAtribut(Atribut atribut, List<String> errors) {
		atributDao.updateAtribut(atribut, errors);
		
	}

	@Override
	@Transactional
	public List<Atribut_produs> getAtributProdusByIdAtribut(int id) {
		return atributDao.getAtributProdusByIdAtribut(id);
	}

	@Override
	@Transactional
	public CategorieAAtributelor getCategorieAAtributului(Atribut atribut){
		return unproxy(atributDao.getAtributById(atribut.getId()).get(0).getCategorieAAtributelor());
	}
	
	
}
