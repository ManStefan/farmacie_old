package farmacie.service.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import farmacie.dao.Arbore_categoriiDao;
import farmacie.dao.CategorieDao;

import farmacie.model.Arbore_categorii;
import farmacie.model.Categorie;
import farmacie.model.Produs;
import farmacie.service.CategorieServiceI;
import farmacie.statics.FarmacieConstants;

@Service
public class CategorieService implements CategorieServiceI, FarmacieConstants {

	@Autowired
	CategorieDao categorieDao;
	
	@Autowired
	Arbore_categoriiDao arbore_categoriiDao;
	
	@Override
	@Transactional
	public void addCategorie(Categorie categorie, List<String> errors) {
		categorieDao.addCategorie(categorie, errors);
	}

	@Override
	@Transactional
	public void deleteCategorie(Categorie categorie, List<String> errors) {
		categorieDao.deleteCategorie(categorie, errors);
	}

	@Override
	@Transactional
	public List<Categorie> getCategorieById(int id) {
		return categorieDao.getCategorieById(id);
	}

	@Override
	@Transactional
	public List<Categorie> getCategorieByNume(String nume) {
		return categorieDao.getCategorieByNume(nume);
	}

	@Override
	@Transactional
	public List<Produs> getProdusByIdCategorie(int idCategorie) {
		return categorieDao.getCategorieById(idCategorie).get(0).getProduse();
	}

	@Override
	@Transactional
	public List<Categorie> getCategorieCopiiByIdCategorie(int idCatParinte) {
		List <Arbore_categorii> arbore_categorii = arbore_categoriiDao.getArbore_categoriiById_cat_parinte(idCatParinte);
		
		List<Categorie> categorie = new ArrayList<Categorie>();
		
		
		
		Iterator<Arbore_categorii> it= arbore_categorii.iterator();
		while (it.hasNext())
			categorie.add(categorieDao.getCategorieById(it.next().getId_cat_copil()).get(0));
		
		return categorie;
	}

	/**
	 * Metoda care returneaza intr-o lista arborele de categorii codat pentru a fi folosit in view
	 * Metoda de codare:
	 *       - daca nu am nici un dummy intre elemente consecutine, inseamna ca am tata->fiu
	 *       - daca am un dummy inseamna ca sunt elemente alaturate de pe acelsi nivel - frati ai aceluiasi tata
	 *       - daca am mai mult de un dummy, inseamna ca elementul care urmeaza este cu atatea niveluri in urma
	 *         cu cate dummy-uri exista
	 * Astfel ma intereseaza doar sa stiu cand inchid/deschid tag-urile in pagina, fara sa ma interezese
	 * relatia de tata-fiu: la fiecare doua categorii consecutive fara dummy deschid un tag, la fiecare dummy
	 * intalnit inchid un tag.
	 */
	@Override
	@Transactional
	public List<Categorie> getEncodedArbore_categorie() {

		Categorie tmp;
		Iterator<Categorie> it;
		
		//element care delimiteaza cand sa ma intorc in arbore
		Categorie dummy_cat = new Categorie();
		dummy_cat.setNivel(0);
		dummy_cat.setNume("dummy");
		
		//lista cu categoriile codate
		List<Categorie> arbore_codat = new ArrayList<Categorie>();

		//lista cu toate categoriile din magazin
		List<Categorie> lista_categorii = getToateCategoriile();
		
		
		//stiva cu care voi face parcurgerea in adancime si care o initializez cu radacinile categoriilor(nivel 1)
		Stack<Categorie> stiva = new Stack<Categorie>();
		
		//parcurg lista cu toate categoriile pentru a insera in stiva categoriile de pe primul nivel
		it = lista_categorii.iterator();
		while (it.hasNext()){
			tmp = it.next();
			if (tmp.getNivel() == 1){
				stiva.push(tmp);
			}
		}
		
		while (!stiva.isEmpty()){
			tmp = stiva.pop();
			
			List<Categorie> copii = getCategorieCopiiByIdCategorie(tmp.getId()); 
			//daca elementul scos nu are copii, inseamna ca trebuie sa ma intorc un nivel si inserez in 
			//lista intoarsa un element dummy
			if (!copii.isEmpty()){
				it = copii.iterator();
				while (it.hasNext())
					stiva.push(it.next());
			}	
			
			//ce am scos din stiva pun in lista codata
			arbore_codat.add(tmp);
			
			//vad daca mai am elemente in stiva - daca nu mai am pun in lista codata atatea dummy-uri cat este nr nivelului
			//                                  - daca am, pun diferenta dintre numarul de nivele + 1 
			if (stiva.isEmpty())
				put_lista_repeated(arbore_codat,tmp.getNivel(),dummy_cat);
			else
				put_lista_repeated(arbore_codat,tmp.getNivel() - stiva.peek().getNivel() + 1, dummy_cat);			
		}
		
		return arbore_codat;
	}

	@Override
	@Transactional
	public List<Categorie> getToateCategoriile() {
		return categorieDao.getToateCategoriile();
	}

	@Override
	@Transactional
	public List<Categorie> getCategorieByNivel(int nivel) {
		return categorieDao.getCategorieByNivel(nivel);
	}
	
	protected void put_lista_repeated(List<Categorie> list, int nr_dummy, Categorie element){
		for (int i=0; i < nr_dummy; i++)
			list.add(element);
	}

	@Override
	@Transactional
	public List<Produs> getProdusByCategorie(Categorie categorie) {
		return categorieDao.getCategorieById(categorie.getId()).get(0).getProduse();
	}

	@Override
	@Transactional
	public void updateCategorie(Categorie categorie, List<String> errors) {
		categorieDao.updateCategorie(categorie, errors);
		
	}

	@Override
	@Transactional
	public List<Categorie> searchCategorie(String crumb, String nivel, int frunza, int nrOfRecords) {
		return categorieDao.searchCategorie(crumb, nivel, frunza, nrOfRecords);
	}

	public List<Categorie> searchCategorie(String crumb, String nivel, int nrOfRecords){
		return categorieDao.searchCategorie(crumb, nivel, nrOfRecords);
	}

	@Override
	@Transactional
	public List<Integer> getIdCategorieCopiiByIdCategorie(int idCatParinte) {
		List <Arbore_categorii> arbore_categorii = arbore_categoriiDao.getArbore_categoriiById_cat_parinte(idCatParinte);
		
		List<Integer> categorie = new ArrayList<Integer>();
		
		Iterator<Arbore_categorii> it= arbore_categorii.iterator();
		while (it.hasNext())
			categorie.add(it.next().getId_cat_copil());
		
		return categorie;
	}

	@Override
	@Transactional
	public List<Integer> getIdToateSubcategoriile(int idCatParinte) {
		List<Integer> subCategorii = new ArrayList<Integer>();
		
		subCategorii.add(idCatParinte);
		
		int i=0;
		while (i < subCategorii.size()){
			subCategorii.addAll(getIdCategorieCopiiByIdCategorie(subCategorii.get(i)));
			i++;
		}
		
		return subCategorii;
	}

	@Override
	@Transactional
	public List<Integer> getIdCategorieByNivel(int nivel) {
		// TODO Auto-generated method stub
		return categorieDao.getIdCategorieByNivel(nivel);
	}
	
}
