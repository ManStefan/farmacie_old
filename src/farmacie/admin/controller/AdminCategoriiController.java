package farmacie.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import farmacie.admin.controller.statics.AdminCategoriiControllerStatics;
import farmacie.json.JSONArray;
import farmacie.model.Arbore_categorii;
import farmacie.model.Categorie;
import farmacie.service.Arbore_categoriiServiceI;
import farmacie.service.CategorieServiceI;
import farmacie.service.ProdusServiceI;
import farmacie.statics.FarmacieConstants;

@Controller
public class AdminCategoriiController implements AdminCategoriiControllerStatics{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private CategorieServiceI categorieService;
	
	@Autowired
	private Arbore_categoriiServiceI arbore_categoriiService;
	
	@Autowired
	private ProdusServiceI produsService;
	
	@RequestMapping("/**/adminCategorii.htm")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		//completez primul combo box cu root-urile categoriilor
		
		List<Categorie> rootCategorii = categorieService.getCategorieByNivel(1);
		
		modelMap.put(DROPDOWN_CAT_LIST, rootCategorii);
		modelMap.put(NR_OF_DROPDOWNS, 1);
		
		session.setAttribute(NR_OF_DROPDOWNS, 1);
		
		return new ModelAndView(ADMIN_CATEGORII_VIEW, "model", modelMap);
	}
	
	@RequestMapping("/**/childrenCategories.htm")
	public ModelAndView getChildrenCategories(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if (request.getParameter("action").equals("exploreFW"))
		{	
			int idCat = Integer.valueOf(request.getParameter("idCat"));
			
			List<Categorie> listCategorii = categorieService.getCategorieCopiiByIdCategorie(idCat);
			if (listCategorii != null && !listCategorii.isEmpty())
			{
				int nrDropDowns = (Integer)session.getAttribute(NR_OF_DROPDOWNS);
				nrDropDowns++;
				session.setAttribute(NR_OF_DROPDOWNS, nrDropDowns);
				modelMap.put(NR_OF_DROPDOWNS, nrDropDowns);
			}
			modelMap.put(DROPDOWN_CAT_LIST, listCategorii);
		} else
		if (request.getParameter("action").equals("exploreBACK")) {
			int nrDropDowns = (Integer)session.getAttribute(NR_OF_DROPDOWNS);
			nrDropDowns--;
			session.setAttribute(NR_OF_DROPDOWNS, nrDropDowns);
			modelMap.put(NR_OF_DROPDOWNS, nrDropDowns);			
		}

		return new ModelAndView(CHILDREN_CATEGORIES, "model", modelMap);
	}

	@RequestMapping("/**/adBrotherCategory.htm")
	public ModelAndView addBrotherCategory(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		int idCatBrother = Integer.valueOf(request.getParameter("idCatBrother"));
		String newBrotherCatName = request.getParameter("newBrotherName");
		Categorie catParent = null;
		
		log.debug("Obtinere categorie parinte pentru categoria :" + idCatBrother + "(este categoria frate cu categoria care se vrea inserata: " + newBrotherCatName +")");
		List<Arbore_categorii> categoriiParinte = arbore_categoriiService.getArbore_categoriiById_cat_copil(idCatBrother);
		if (categoriiParinte != null && !categoriiParinte.isEmpty()){
			int idCatParent = categoriiParinte.get(0).getId_cat_parinte();
			log.debug("Categoria parinte este:" + idCatParent);
			catParent = categorieService.getCategorieById(idCatParent).get(0);
		} else
			log.debug("Categoria " + newBrotherCatName + " se vrea adaugata ca root!");
		
		log.debug("Adaugare categorie :" + newBrotherCatName + " ...");
		Categorie newBrotherCat = new Categorie();
		newBrotherCat.setFrunza(1);
		newBrotherCat.setNivel(catParent != null ?    catParent.getNivel() + 1 : 1);
		newBrotherCat.setNume(newBrotherCatName);
		categorieService.addCategorie(newBrotherCat, errors);
		if (errors.size() > 0) {
			log.debug("Eroare in adaugare categorie, categoria:" + newBrotherCatName + " exista deja in BD!");
			if (errors.contains((String)FarmacieConstants.DUPLICATE_ENTRY_ERROR))
				modelMap.put(ADD_CATEGORIES_LABEL_ERROR, DUPLICATE_CATEGORIES_ERROR);
			else
			if (errors.contains((String)FarmacieConstants.SYSTEM_ERROR))
				modelMap.put(ADD_CATEGORIES_LABEL_ERROR, ADD_CATEGORIES_ERROR);
			
			return new ModelAndView(ADD_CHILD_CATEGORY, "model", modelMap);
		}
		log.debug("Categoria: " + newBrotherCatName + " adaugata cu succes in BD!");
		
		if (catParent != null){
			log.debug("Legare categorie copil: " + newBrotherCat.getNume() + "(" + newBrotherCat.getId() + ") la categoria parinte: " + catParent.getNume() + "("+ catParent.getId() +") ...");
			Arbore_categorii arbore_categorii = new Arbore_categorii();
			arbore_categorii.setId_cat_copil(newBrotherCat.getId());
			arbore_categorii.setId_cat_parinte(catParent.getId());
			arbore_categoriiService.addArbore_categorii(arbore_categorii, errors);
			if (errors.size() > 0) {
				log.debug("Eroare la legare categorie copil: " + newBrotherCat.getNume() + "(" + newBrotherCat.getId() + ") la categoria parinte: " + catParent.getNume() + "("+ catParent.getId() +")");
				if (errors.contains(FarmacieConstants.DUPLICATE_ENTRY_ERROR))
					modelMap.put(ADD_CATEGORIES_LABEL_ERROR, DUPLICATE_CATEGORIES_MAPPING_ERROR);
				else
				if (errors.contains(FarmacieConstants.SYSTEM_ERROR))
					modelMap.put(ADD_CATEGORIES_LABEL_ERROR, ADD_CATEGORIES_ERROR);
				return new ModelAndView(ADD_CHILD_CATEGORY, "model", modelMap);
			}
			log.debug("Sucess la legare categorie copil: " + newBrotherCat.getNume() + "(" + newBrotherCat.getId() + ") la categoria parinte: " + catParent.getNume() + "("+ catParent.getId() +")");
			
		}
		
		modelMap.put(ADDED_BROTHER_CATEGORY, newBrotherCat);
		int nrDropDowns = (Integer)session.getAttribute(NR_OF_DROPDOWNS);
		modelMap.put(NR_OF_DROPDOWNS, nrDropDowns);	
		return new ModelAndView(ADD_BROTHER_CATEGORY, "model", modelMap);
	}	

	@RequestMapping("/**/updateNameCat.htm")
	public ModelAndView updateNameCat(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		String newCatName = request.getParameter("newCatName");
		int idCat = Integer.valueOf(request.getParameter("idCat"));
		
		Categorie cat = categorieService.getCategorieById(idCat).get(0);
		String oldCatName = cat.getNume();
		
		log.debug("Modific numele catgoriei: " + cat.getNume() + "(" + cat.getId() + ") in: " + newCatName);
		cat.setNume(newCatName);
		categorieService.updateCategorie(cat, errors);
		if (errors.size() > 0) {
			log.debug("Eroare la modificare numele catgoriei: " + cat.getNume() + "(" + cat.getId() + ") in: " + newCatName);
			if (errors.contains((String)FarmacieConstants.DUPLICATE_ENTRY_ERROR))
				modelMap.put(ADD_CATEGORIES_LABEL_ERROR, DUPLICATE_CATEGORIES_ERROR);
			else
			if (errors.contains((String)FarmacieConstants.SYSTEM_ERROR))
				modelMap.put(ADD_CATEGORIES_LABEL_ERROR, ADD_CATEGORIES_ERROR);
			
			return new ModelAndView(UPDATE_CATEGORY_NAME, "model", modelMap);
		}
		log.debug("Modificare facuta cu succes!");
		
		modelMap.put(MODIFIED_CATEGORY, cat);
		int nrDropDowns = (Integer)session.getAttribute(NR_OF_DROPDOWNS);
		modelMap.put(NR_OF_DROPDOWNS, nrDropDowns);		
		modelMap.put(OLD_NAME_CATEGORY, oldCatName);
		
		return new ModelAndView(UPDATE_CATEGORY_NAME, "model", modelMap);
		
		
	}	
	
	@RequestMapping("/**/deleteCategory.htm")
	public ModelAndView deleteCategory(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		boolean areProduseSubCategorie = false;
		
		
		List<Categorie> childCategories = new ArrayList<Categorie>();
		
		session.removeAttribute(ARE_CHILD_CATEGORIES);
		
		int idCat = Integer.valueOf(request.getParameter("idCat"));
		
		//verific daca categoria care vrea sa fie stearsa are sub-categorii si/sau produse
		List<Categorie> breathFirstList = new ArrayList<Categorie>();
		Categorie cat = categorieService.getCategorieById(idCat).get(0);
		childCategories.add(cat);
		breathFirstList.add(cat);
		
		while (!breathFirstList.isEmpty()){
			cat = breathFirstList.remove(0);
			if (produsService.existaProduseCuCategorie(cat.getId())){
				areProduseSubCategorie = true;
			}
			
			List<Arbore_categorii> arboreCatList = arbore_categoriiService.getArbore_categoriiById_cat_parinte(cat.getId());
			
			for (Arbore_categorii it : arboreCatList){
				Categorie childCat = categorieService.getCategorieById(it.getId_cat_copil()).get(0);
				breathFirstList.add(childCat);
				childCategories.add(childCat);
			}
		}
		
		if (!childCategories.isEmpty()){
			modelMap.put(ARE_CHILD_CATEGORIES, "1");
			session.setAttribute(ARE_CHILD_CATEGORIES, childCategories);
		} else {
			modelMap.put(ARE_CHILD_CATEGORIES, "0");
		}
		
		if (areProduseSubCategorie){
			modelMap.put(ARE_PRODUCTS_IN_CATEGORIES, "1");
		} else {
			modelMap.put(ARE_PRODUCTS_IN_CATEGORIES, "0");
		}
	
		return new ModelAndView(DELETE_CATEGORY, "model", modelMap);
	}

	@RequestMapping("/**/finishDeleteCategory.htm")
	public ModelAndView finishDeleteCategory(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();

		List<Categorie> childCategories = (List<Categorie>)session.getAttribute(ARE_CHILD_CATEGORIES);

		
		log.debug("Sterg categoria selectata si categoriile de sub ea ...");
		for (Categorie cat : childCategories){
			log.debug("Sterg produsele din categoria " + cat.toString());
			produsService.stergeProduseleDinCategoria(cat.getId(), errors);
			if (errors.size() > 0 && errors.contains((String)FarmacieConstants.SYSTEM_ERROR)){
				modelMap.put(DELETE_CATEGORIES_LABEL_ERROR, DELETE_CATEGORIES_ERROR_DELETE_PRODUCT + cat.toString());
				return new ModelAndView(FINISH_DELETE_CATEGORY, "model", modelMap);
			}			
			
			log.debug("Sterg categoria:" + cat.getNume() + "(" + cat.getId() + ")");
			categorieService.deleteCategorie(cat, errors);
			if (errors.size() > 0 && errors.contains((String)FarmacieConstants.SYSTEM_ERROR)){
				modelMap.put(DELETE_CATEGORIES_LABEL_ERROR, DELETE_CATEGORIES_ERROR_DELETE_CATEGORY + cat.toString());
				return new ModelAndView(FINISH_DELETE_CATEGORY, "model", modelMap);
			}
			
		}
		
		modelMap.put(DELETE_CATEGORIES_LABEL_ERROR, "OK");
		return new ModelAndView(FINISH_DELETE_CATEGORY, "model", modelMap);
	}	
	
	@RequestMapping("/**/adChildCategory.htm")
	public ModelAndView addChildCategory(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		response.setCharacterEncoding("utf-8");		
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		int idCatParent = Integer.valueOf(request.getParameter("idCatParent"));
		
		Categorie catParent = categorieService.getCategorieById(idCatParent).get(0);
		String newChildCatName = request.getParameter("newChildName");
		
		log.debug("Adaugare categorie copil: " + newChildCatName + " la categoria: " + catParent.getNume() + "(" + catParent.getId() + ")...");
		Categorie newChildCat = new Categorie();
		newChildCat.setFrunza(1);
		newChildCat.setNivel(catParent.getNivel() + 1);
		newChildCat.setNume(newChildCatName);
		categorieService.addCategorie(newChildCat, errors);
		if (errors.size() > 0) {
			log.debug("Eroare in adaugare categorie, categoria:" + newChildCatName + " exista deja in BD!");
			if (errors.contains((String)FarmacieConstants.DUPLICATE_ENTRY_ERROR))
				modelMap.put(ADD_CATEGORIES_LABEL_ERROR, DUPLICATE_CATEGORIES_ERROR);
			else
			if (errors.contains((String)FarmacieConstants.SYSTEM_ERROR))
				modelMap.put(ADD_CATEGORIES_LABEL_ERROR, ADD_CATEGORIES_ERROR);
			
			return new ModelAndView(ADD_CHILD_CATEGORY, "model", modelMap);
		}
		log.debug("Categoria: " + newChildCatName + " adaugata cu succes in BD!");
		
		log.debug("Legare categorie copil: " + newChildCat.getNume() + "(" + newChildCat.getId() + ") la categoria parinte: " + catParent.getNume() + "("+ catParent.getId() +") ...");
		Arbore_categorii arbore_categorii = new Arbore_categorii();
		arbore_categorii.setId_cat_copil(newChildCat.getId());
		arbore_categorii.setId_cat_parinte(catParent.getId());
		arbore_categoriiService.addArbore_categorii(arbore_categorii, errors);
		if (errors.size() > 0) {
			log.debug("Eroare la legare categorie copil: " + newChildCat.getNume() + "(" + newChildCat.getId() + ") la categoria parinte: " + catParent.getNume() + "("+ catParent.getId() +")");
			if (errors.contains(FarmacieConstants.DUPLICATE_ENTRY_ERROR))
				modelMap.put(ADD_CATEGORIES_LABEL_ERROR, DUPLICATE_CATEGORIES_MAPPING_ERROR);
			else
			if (errors.contains(FarmacieConstants.SYSTEM_ERROR))
				modelMap.put(ADD_CATEGORIES_LABEL_ERROR, ADD_CATEGORIES_ERROR);
			return new ModelAndView(ADD_CHILD_CATEGORY, "model", modelMap);
		}
		log.debug("Sucess la legare categorie copil: " + newChildCat.getNume() + "(" + newChildCat.getId() + ") la categoria parinte: " + catParent.getNume() + "("+ catParent.getId() +")");
		
		log.debug("Update categorie parinte de la nod frunza la nod care nu este frunza(daca este cazul)");
		if (catParent.getFrunza() == 1){
			catParent.setFrunza(0);
			categorieService.updateCategorie(catParent, errors);
			
			if (errors.size() > 0) {
				log.debug("Eraore la update nod parinte: " + catParent.getNume() + "("+ catParent.getId()+") de la nod frunza la ond normal!");
				if (errors.contains(FarmacieConstants.SYSTEM_ERROR))
					modelMap.put(ADD_CATEGORIES_LABEL_ERROR, ADD_CATEGORIES_ERROR);		
				return new ModelAndView(ADD_CHILD_CATEGORY, "model", modelMap);
			}
		}
		
		return new ModelAndView(ADD_CHILD_CATEGORY, "model", modelMap);
	}
	
	@RequestMapping("/**/searchCategorie.htm")
	public ModelAndView searchCategorie(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		String crumb = request.getParameter("term");
		Integer frunza = Integer.valueOf(request.getParameter("frunza"));
		String nivel = request.getParameter("nivel");
		
		if (crumb.equals("*"))
			crumb = "";

		List<Categorie> categorii = categorieService.searchCategorie(crumb, nivel, frunza, NR_OF_RECORDS_SHOWN);
		
		JSONArray json = new JSONArray();			
		
		for (Categorie categorie : categorii){
			
			Map<String, String> jsonNode = new HashMap<String, String>();
			
			jsonNode.put("id", new Integer(categorie.getId()).toString());
			jsonNode.put("label", categorie.getNume());
			jsonNode.put("value", categorie.getNume());
//			jsonNode.put("searchedNrOfRecords", totalNrOfRecords);
//			jsonNode.put("totalNrOfRecords", searchedNrOfRecords);
			
			json.put(jsonNode);
		}
		
		modelMap.put(CATEGORIE_SEARCH_RESULTS, json);
		
		return new ModelAndView(ADMIN_SEARCH_CATEGORIE_VIEW, "model", modelMap);
	}
	
	
}
