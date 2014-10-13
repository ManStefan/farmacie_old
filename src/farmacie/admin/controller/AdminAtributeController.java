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

import farmacie.admin.controller.statics.AdminAtributeControllerStatics;
import farmacie.json.JSONArray;
import farmacie.model.Atribut;
import farmacie.model.Atribut_produs;
import farmacie.model.CategorieAAtributelor;
import farmacie.service.AtributServiceI;
import farmacie.service.Atribut_produsServiceI;
import farmacie.service.CategorieAAtributelorServiceI;
import farmacie.statics.FarmacieConstants;

@Controller
public class AdminAtributeController implements AdminAtributeControllerStatics{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	CategorieAAtributelorServiceI categorieAAtributelorService;
	
	@Autowired
	AtributServiceI atributService;
	
	@Autowired
	Atribut_produsServiceI atribut_produsService;
	
	@RequestMapping("/**/adminAtribute.htm")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		return new ModelAndView(ADMIN_ATRIBUTE_VIEW, "model", modelMap);
	}
	
	@RequestMapping("/**/addNewAttr.htm")
	public ModelAndView addNewAttr(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		String newAttrName = request.getParameter("newAttrName");
		int selectedCat = Integer.valueOf(request.getParameter("selectedCat"));
		
		CategorieAAtributelor catAttr = categorieAAtributelorService.getCategorieAAtributelorById(selectedCat).get(0);
		
		log.debug("Adaug atributul: " + newAttrName + " la categoria: " + catAttr.getNume() + "(" + catAttr.getId() + ")");
		
		Atribut newAttr = new Atribut();
		newAttr.setNume(newAttrName);
		newAttr.setCategorieAAtributelor(catAttr);
		
		atributService.addAtribut(newAttr, errors);
		
		if (errors.size() > 0) {
			log.debug("Eroare in adaugare atribut :" + newAttrName + " - exista deja in BD!");
			if (errors.contains((String)FarmacieConstants.DUPLICATE_ENTRY_ERROR))
				modelMap.put(ADD_ATTRS_LABEL_ERROR, DUPLICATE_ATTRS_ERROR);
			else
			if (errors.contains((String)FarmacieConstants.SYSTEM_ERROR))
				modelMap.put(ADD_ATTRS_LABEL_ERROR, ADD_ATTRS_ERROR);
			
			return new ModelAndView(ADMIN_ADD_NEW_ATTR_VIEW, "model", modelMap);
		}
		
		log.debug("Succes in adaugare categorie!");
		modelMap.put(ADD_ATTRS_LABEL_ERROR, ADD_ATTRS_OK);
		
		return new ModelAndView(ADMIN_ADD_NEW_ATTR_VIEW, "model", modelMap);
	}	
	
	@RequestMapping("/**/addNewAttrCat.htm")
	public ModelAndView addNewAttrCat(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		String newAttrCatName = request.getParameter("newAttrCatName");
		
		log.debug("Adaug categoria a atributelor:" + newAttrCatName);
		
		CategorieAAtributelor catAAtribut = new CategorieAAtributelor();
		catAAtribut.setNume(newAttrCatName);
		
		categorieAAtributelorService.addCategorieAAtributelor(catAAtribut, errors);
		
		if (errors.size() > 0) {
			log.debug("Eroare in adaugare categorie atribute, categoria:" + newAttrCatName + " exista deja in BD!");
			if (errors.contains((String)FarmacieConstants.DUPLICATE_ENTRY_ERROR))
				modelMap.put(ADD_ATTRS_CATEGORIES_LABEL_ERROR, DUPLICATE_ATTRS_CAT_ERROR);
			else
			if (errors.contains((String)FarmacieConstants.SYSTEM_ERROR))
				modelMap.put(ADD_ATTRS_CATEGORIES_LABEL_ERROR, ADD_ATTRS_CAT_ERROR);
			
			return new ModelAndView(ADMIN_ADD_NEW_ATTR_CAT_VIEW, "model", modelMap);
		}
		
		log.debug("Categorie a atributelor adaugata cu succes in baza de date!");
		modelMap.put(ADD_ATTRS_CATEGORIES_LABEL_ERROR, ADD_ATTRS_CAT_OK);
		
		return new ModelAndView(ADMIN_ADD_NEW_ATTR_CAT_VIEW, "model", modelMap);
	}
	
	@RequestMapping("/**/searchAttr.htm")
	public ModelAndView searchAttr(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		String crumb = request.getParameter("term");
		if (crumb.equals("*"))
			crumb = "";
		int selectedAttrCatId = Integer.valueOf(request.getParameter("selectedAttrCatId"));
		
		CategorieAAtributelor attrCat = categorieAAtributelorService.getCategorieAAtributelorById(selectedAttrCatId).get(0);
		
		List<Atribut> attrsList = atributService.searchAtribut(crumb, attrCat, NR_OF_RECORDS_SHOWN);
		
		JSONArray json = new JSONArray();			
		
		for (int i=0; i < attrsList.size(); i++){
			
			Map<String, String> jsonNode = new HashMap<String, String>();
			
			jsonNode.put("id", new Integer(attrsList.get(i).getId()).toString());
			jsonNode.put("label", attrsList.get(i).getNume());
			jsonNode.put("value", attrsList.get(i).getNume());
//			jsonNode.put("searchedNrOfRecords", totalNrOfRecords);
//			jsonNode.put("totalNrOfRecords", searchedNrOfRecords);
			
			json.put(jsonNode);
		}
		
		modelMap.put(ATRIBUTE_SEARCH_RESULTS, json);
		
		return new ModelAndView(ADMIN_SEARCH_ATTRIBUTE, "model", modelMap);
	}	
	
	@RequestMapping("/**/searchAttrCat.htm")
	public ModelAndView searchAttrCat(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		String crumb = request.getParameter("term");
		if (crumb.equals("*"))
			crumb = "";
		List<CategorieAAtributelor> list = categorieAAtributelorService.searchCategorieAAtributelor(crumb, NR_OF_RECORDS_SHOWN);
		
//		String totalNrOfRecords = new Long(categorieAAtributelorService.numberOfSearchedResults(crumb)).toString();
//		String searchedNrOfRecords = new Integer(list.size()).toString();
		
		JSONArray json = new JSONArray();			
		
		for (CategorieAAtributelor cat : list){
			
			Map<String, String> jsonNode = new HashMap<String, String>();
			
			jsonNode.put("id", new Integer(cat.getId()).toString());
			jsonNode.put("label", cat.getNume());
			jsonNode.put("value", cat.getNume());
//			jsonNode.put("searchedNrOfRecords", totalNrOfRecords);
//			jsonNode.put("totalNrOfRecords", searchedNrOfRecords);
			
			json.put(jsonNode);
		}
		
		modelMap.put(CATEGORIE_A_ATRIBUTELOR_SEARCH_RESULTS, json);
		
		return new ModelAndView(ADMIN_SEARCH_ATTRIBUTE_CAT, "model", modelMap);
	}
	
	@RequestMapping("/**/renameAttr.htm")
	public ModelAndView renameAttr(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		String newAttrName = request.getParameter("newAttrName");
		int attrId = Integer.valueOf(request.getParameter("attrId"));
		
		log.debug("Redenumire atribut cu id-ul: " + attrId + " la numele: " + newAttrName);
		
		Atribut attr = atributService.getAtributById(attrId).get(0);
		
		attr.setNume(newAttrName);
		
		atributService.updateAtribut(attr, errors);
		
		if (errors.size() > 0) {
			log.debug("Eroare in modificare nume atribut: " + newAttrName + " exista deja in BD!");
			if (errors.contains((String)FarmacieConstants.DUPLICATE_ENTRY_ERROR))
				modelMap.put(RENAME_ATTRS_LABEL_ERROR, DUPLICATE_ATTRS_ERROR);
			else
			if (errors.contains((String)FarmacieConstants.SYSTEM_ERROR))
				modelMap.put(RENAME_ATTRS_LABEL_ERROR, RENAME_ATTRS_ERROR);
			
			return new ModelAndView(ADMIN_RENAME_ATTRIBUTE, "model", modelMap);
		}
		
		log.debug("Atribut redenumit cu succes !");
		modelMap.put(RENAME_ATTRS_LABEL_ERROR, RENAME_ATTRS_OK);

		return new ModelAndView(ADMIN_RENAME_ATTRIBUTE, "model", modelMap);
	}	
	
	
	@RequestMapping("/**/renameAttrCat.htm")
	public ModelAndView renameAttrCat(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		String newAttrCatName = request.getParameter("newAttrCatName");
		int attrCatId = Integer.valueOf(request.getParameter("attrCatId"));
		
		log.debug("Redenumire categorie cu id-ul:" + attrCatId + " la numele:" + newAttrCatName);
		
		CategorieAAtributelor catAttr = categorieAAtributelorService.getCategorieAAtributelorById(attrCatId).get(0);
		
		CategorieAAtributelor newCatAttr = new CategorieAAtributelor();
		newCatAttr.setId(catAttr.getId());
		newCatAttr.setNume(newAttrCatName);
//		newCatAttr.setAtribut(catAttr.getAtribut());
		
		//catAttr.setNume(newAttrCatName);
		
		categorieAAtributelorService.updateCategorieAAtributelor(newCatAttr, errors);

		if (errors.size() > 0) {
			log.debug("Eroare in modificare nume categorie atribute, categoria:" + newAttrCatName + " exista deja in BD!");
			if (errors.contains((String)FarmacieConstants.DUPLICATE_ENTRY_ERROR))
				modelMap.put(RENAME_ATTRS_CATEGORIES_LABEL_ERROR, DUPLICATE_ATTRS_CAT_ERROR);
			else
			if (errors.contains((String)FarmacieConstants.SYSTEM_ERROR))
				modelMap.put(RENAME_ATTRS_CATEGORIES_LABEL_ERROR, RENAME_ATTRS_CAT_ERROR);
			
			return new ModelAndView(ADMIN_RENAME_ATTRIBUTE_CAT, "model", modelMap);
		}
		
		log.debug("Categorie a atributelor redenumita cu succes !");
		modelMap.put(RENAME_ATTRS_CATEGORIES_LABEL_ERROR, RENAME_ATTRS_CAT_OK);

		
		return new ModelAndView(ADMIN_RENAME_ATTRIBUTE_CAT, "model", modelMap);
	}
	
	@RequestMapping("/**/finishDeleteAttr.htm")
	public ModelAndView finishDeleteAttr(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		int id_attr = Integer.valueOf(request.getParameter("attrId"));
		Atribut attr = atributService.getAtributById(id_attr).get(0);
		
		log.debug("Sterg atributul cu id-ul:" + id_attr);
		atributService.deleteAtribut(attr, errors);
		
		if (errors.size() > 0 && errors.contains((String)FarmacieConstants.SYSTEM_ERROR)){
			modelMap.put(FINISH_DELETE_ATTRS_LABEL_ERROR, FINISH_DELETE_ATTRS_ERROR);
			return new ModelAndView(ADMIN_FINISH_DELETE_ATTRIBUTE, "model", modelMap);
		}
		
		log.debug("Stergerea atributului a avut loc cu succes!");
		modelMap.put(FINISH_DELETE_ATTRS_LABEL_ERROR, FINISH_DELETE_ATTRS_OK);
		
		return new ModelAndView(ADMIN_FINISH_DELETE_ATTRIBUTE, "model", modelMap);
	}
	
	
	@RequestMapping("/**/deleteAttr.htm")
	public ModelAndView deleteAttr(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		int id_attr = Integer.valueOf(request.getParameter("attrId"));
		
		List<Atribut_produs> attr_prod = atributService.getAtributProdusByIdAtribut(id_attr);
		
		if (attr_prod != null && !attr_prod.isEmpty())
			modelMap.put(DELETE_ATTRS_LABEL_MESSAGE, DELETE_ATTRIBUTES_EXISTS_ASSOCIATED_PRODS);
		else
			modelMap.put(DELETE_ATTRS_LABEL_MESSAGE, DELETE_ATTRIBUTES_NO_EXISTS_ASSOCIATED_PRODS);

		return new ModelAndView(ADMIN_DELETE_ATTRIBUTE, "model", modelMap);
	}	
	
	@RequestMapping("/**/deleteAttrCat.htm")
	public ModelAndView deleteAttrCat(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		int attrCatId = Integer.valueOf(request.getParameter("attrCatId"));
		CategorieAAtributelor attrCat = categorieAAtributelorService.getCategorieAAtributelorById(attrCatId).get(0);
		
		List<Atribut> attrList = categorieAAtributelorService.getAtributForCategory(attrCatId);
		
		log.debug("Stergere categorie ci id-ul" + attrCatId);
		
		if (attrList != null && !attrList.isEmpty()){
			modelMap.put(DELETE_ATTRS_CATEGORIES_LABEL_ERROR, DELETE_ATTR_CAT_ERROR_NOT_EMPTY_CAT);
			log.debug("Categorie cu id-ul:" + attrCatId + " are atribute atasatate, nu va fi stearsa!");
			return new ModelAndView(ADMIN_DELETE_ATTRIBUTE_CAT, "model", modelMap);		
		}
		
		categorieAAtributelorService.deleteCategorieAAtributelor(attrCat, errors);
		
		if (errors.size() > 0 && errors.contains((String)FarmacieConstants.SYSTEM_ERROR)){
			modelMap.put(DELETE_ATTRS_CATEGORIES_LABEL_ERROR, DELETE_ATTRS_CAT_ERROR);
			return new ModelAndView(ADMIN_DELETE_ATTRIBUTE_CAT, "model", modelMap);
		}
		
		log.debug("Stergere facuta cu succes!");
		modelMap.put(DELETE_ATTRS_CATEGORIES_LABEL_ERROR, DELETE_ATTRS_CAT_OK);
		
		return new ModelAndView(ADMIN_DELETE_ATTRIBUTE_CAT, "model", modelMap);
	}
	
}
