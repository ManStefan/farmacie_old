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

import farmacie.admin.controller.statics.AdminProducatoriControllerStatics;
import farmacie.json.JSONArray;
import farmacie.model.Atribut;
import farmacie.model.Atribut_produs;
import farmacie.model.CategorieAAtributelor;
import farmacie.model.Producator;
import farmacie.service.ProducatorServiceI;
import farmacie.service.ProdusServiceI;
import farmacie.statics.FarmacieConstants;

@Controller
public class AdminProducatoriController implements AdminProducatoriControllerStatics{
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private ProducatorServiceI producatorService;
	
	@Autowired
	private ProdusServiceI produsService;
	
	@RequestMapping("/**/adminProducatori.htm")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		return new ModelAndView(ADMIN_PRODUCATORI_VIEW, "model", modelMap);
	}

	@RequestMapping("/**/addNewProducator.htm")
	public ModelAndView addNewAttrCat(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		String newProducatorName = request.getParameter("newProducatorName");
		
		log.debug("Adaug producatorul:" + newProducatorName);
		
		Producator newProducator = new Producator();
		newProducator.setNume(newProducatorName);
		
		producatorService.addProducator(newProducator, errors);
		
		if (errors.size() > 0) {
			if (errors.contains((String)FarmacieConstants.DUPLICATE_ENTRY_ERROR)){
				modelMap.put(ADD_PRODUCATOR_LABEL_ERROR, DUPLICATE_PRODUCATOR_ERROR);
				log.debug("Eroare in adaugare producator, producatorul cu numele:" + newProducatorName + " exista deja in BD!");
			} else
			if (errors.contains((String)FarmacieConstants.SYSTEM_ERROR)){
				modelMap.put(ADD_PRODUCATOR_LABEL_ERROR, ADD_PRODUCATOR_ERROR);
			}
			
			return new ModelAndView(ADMIN_ADD_PRODUCATOR_VIEW, "model", modelMap);
		}
		
		log.debug("Categorie a atributelor adaugata cu succes in baza de date!");
		modelMap.put(ADD_PRODUCATOR_LABEL_ERROR, ADD_PRODUCATOR_OK);
		
		return new ModelAndView(ADMIN_ADD_PRODUCATOR_VIEW, "model", modelMap);
	}
	
	
	
	@RequestMapping("/**/searchProducatori.htm")
	public ModelAndView searchProducatori(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		String crumb = request.getParameter("term");
		if (crumb.equals("*"))
			crumb = "";

		List<Producator> producatori = producatorService.searchProducatorByName(crumb, NR_OF_RECORDS_SHOWN);
		
		JSONArray json = new JSONArray();			
		
		for (Producator producator : producatori){
			
			Map<String, String> jsonNode = new HashMap<String, String>();
			
			jsonNode.put("id", new Integer(producator.getId()).toString());
			jsonNode.put("label", producator.getNume());
			jsonNode.put("value", producator.getNume());
//			jsonNode.put("searchedNrOfRecords", totalNrOfRecords);
//			jsonNode.put("totalNrOfRecords", searchedNrOfRecords);
			
			json.put(jsonNode);
		}
		
		modelMap.put(PRODUCATORI_SEARCH_RESULTS, json);
		
		return new ModelAndView(ADMIN_SEARCH_PRODUCATORI_VIEW, "model", modelMap);
	}
	
	@RequestMapping("/**/renameProducator.htm")
	public ModelAndView renameProducator(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		String newProducatorName = request.getParameter("newProducatorName");
		int producatorId = Integer.valueOf(request.getParameter("producatorId"));
		
		log.debug("Redenumire producator cu id-ul: " + producatorId + " la numele: " + newProducatorName);
		
		Producator producator = producatorService.getProducatorById(producatorId).get(0);
		
		producator.setNume(newProducatorName);
		
		producatorService.updateProducator(producator, errors);
		
		if (errors.size() > 0) {
			log.debug("Eroare in modificare nume producator: " + newProducatorName + " exista deja in BD!");
			if (errors.contains((String)FarmacieConstants.DUPLICATE_ENTRY_ERROR))
				modelMap.put(RENAME_PRODUCATOR_LABEL_ERROR, DUPLICATE_PRODUCATOR_ERROR);
			else
			if (errors.contains((String)FarmacieConstants.SYSTEM_ERROR))
				modelMap.put(RENAME_PRODUCATOR_LABEL_ERROR, RENAME_PRODUCATOR_ERROR);
			
			return new ModelAndView(ADMIN_RENAME_PRODUCATOR, "model", modelMap);
		}
		
		log.debug("Atribut redenumit cu succes !");
		modelMap.put(RENAME_PRODUCATOR_LABEL_ERROR, RENAME_PRODUCATOR_OK);

		return new ModelAndView(ADMIN_RENAME_PRODUCATOR, "model", modelMap);
	}	

	@RequestMapping("/**/deleteProducator.htm")
	public ModelAndView deleteProducator(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		int producatorId = Integer.valueOf(request.getParameter("producatorId"));
		
		boolean hasAttachedProducts = produsService.existaProduseCuProducator(producatorId);
		
		if (hasAttachedProducts)
			modelMap.put(DELETE_PRODUCT_LABEL_MESSAGE, DELETE_PRODUCT_EXISTS_ASSOCIATED_PRODS);
		else
			modelMap.put(DELETE_PRODUCT_LABEL_MESSAGE, DELETE_PRODUCT_NO_EXISTS_ASSOCIATED_PRODS);

		return new ModelAndView(ADMIN_DELETE_PRODUCT, "model", modelMap);
	}	
	
	@RequestMapping("/**/finishDeleteProducator.htm")
	public ModelAndView finishDeleteProducator(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		int producatorId = Integer.valueOf(request.getParameter("producatorId"));
		Producator producator = producatorService.getProducatorById(producatorId).get(0);
		
		log.debug("Sterg producatorul cu id-ul:" + producatorId);
		producatorService.deleteProducator(producator, errors);
		
		if (errors.size() > 0 && errors.contains((String)FarmacieConstants.SYSTEM_ERROR)){
			modelMap.put(FINISH_DELETE_PRODUCATOR_LABEL_ERROR, FINISH_DELETE_PRODUCATOR_ERROR);
			return new ModelAndView(ADMIN_FINISH_DELETE_PRODUCATOR, "model", modelMap);
		}
		
		log.debug("Stergerea producatorului a avut loc cu succes!");
		modelMap.put(FINISH_DELETE_PRODUCATOR_LABEL_ERROR, FINISH_DELETE_PRODUCATOR_OK);
		
		return new ModelAndView(ADMIN_FINISH_DELETE_PRODUCATOR, "model", modelMap);
	}
	
	
}
