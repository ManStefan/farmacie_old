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

import farmacie.admin.controller.statics.AdminEtaloaneControllerStatics;
import farmacie.json.JSONArray;
import farmacie.model.Etalon;
import farmacie.model.Producator;
import farmacie.service.EtalonServiceI;
import farmacie.service.ProdusServiceI;
import farmacie.statics.FarmacieConstants;

@Controller
public class AdminEtaloaneController implements AdminEtaloaneControllerStatics, FarmacieConstants{
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private EtalonServiceI etalonService;
	
	@Autowired
	private ProdusServiceI produsService;
	
	@RequestMapping("/**/adminEtaloane.htm")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		return new ModelAndView(ADMIN_ETALOANE_VIEW, "model", modelMap);
	}	
	
	@RequestMapping("/**/addNewEtalon.htm")
	public ModelAndView addNewEtalon(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		String newEtalonName = request.getParameter("newEtalonName");
		
		log.debug("Adaug etalonul:" + newEtalonName);
		
		Etalon etalon = new Etalon();
		etalon.setNume(newEtalonName);
		
		etalonService.addEtalon(etalon, errors);
		
		if (errors.size() > 0){
			if (errors.contains(DUPLICATE_ENTRY_ERROR)){
				log.debug("Eroare la adaugarea etalonului: " + newEtalonName + " - acest etalon exista deja in BD!");
				modelMap.put(ADD_ETALON_LABEL_ERROR, ADD_ETALON_DUPLICATE_ENTRY_ERROR);
			} else {
				log.debug("Eroare la adaugarea etalonului: " + newEtalonName);
				modelMap.put(ADD_ETALON_LABEL_ERROR, ADD_ETALON_SYSTEM_ERROR);
			}
			return new ModelAndView(ADMIN_ADD_ETALON_VIEW, "model", modelMap);
		}
		
		log.debug("Etalon adaugat cu succes in baza de date!");
		modelMap.put(ADD_ETALON_LABEL_ERROR, ADD_ETALON_OK);
		return new ModelAndView(ADMIN_ADD_ETALON_VIEW, "model", modelMap);
	}	
	
	@RequestMapping("/**/searchEtaloane.htm")
	public ModelAndView searchEtaloane(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		String crumb = request.getParameter("term");
		if (crumb.equals("*"))
			crumb = "";

		List<Etalon> etaloane = etalonService.searchEtalonByName(crumb, NR_OF_RECORDS_SHOWN);
		
		JSONArray json = new JSONArray();			
		
		for (Etalon etalon : etaloane){
			
			Map<String, String> jsonNode = new HashMap<String, String>();
			
			jsonNode.put("id", new Integer(etalon.getId()).toString());
			jsonNode.put("label", etalon.getNume());
			jsonNode.put("value", etalon.getNume());
//			jsonNode.put("searchedNrOfRecords", totalNrOfRecords);
//			jsonNode.put("totalNrOfRecords", searchedNrOfRecords);
			
			json.put(jsonNode);
		}
		
		modelMap.put(ETALOANE_SEARCH_RESULTS, json);
		
		return new ModelAndView(ADMIN_SEARCH_ETALOANE_VIEW, "model", modelMap);
	}	
	
	@RequestMapping("/**/renameEtalon.htm")
	public ModelAndView renameProducator(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		String newEtalonName = request.getParameter("newEtalonName");
		int etalonId = Integer.valueOf(request.getParameter("etalonId"));
		
		log.debug("Redenumire etalon cu id-ul: " + etalonId + " la numele: " + newEtalonName);
		
		Etalon etalon = etalonService.getEtalonById(etalonId).get(0);
		
		etalon.setNume(newEtalonName);
		
		etalonService.updateEtalon(etalon, errors);
		
		if (errors.size() > 0) {
			log.debug("Eroare in modificare nume etalon: " + newEtalonName + " exista deja in BD!");
			if (errors.contains((String)FarmacieConstants.DUPLICATE_ENTRY_ERROR))
				modelMap.put(RENAME_ETALON_LABEL_ERROR, DUPLICATE_PRODUCATOR_ERROR);
			else
			if (errors.contains((String)FarmacieConstants.SYSTEM_ERROR))
				modelMap.put(RENAME_ETALON_LABEL_ERROR, RENAME_PRODUCATOR_ERROR);
			
			return new ModelAndView(ADMIN_RENAME_ETALON_VIEW, "model", modelMap);
		}
		
		log.debug("Atribut redenumit cu succes !");
		modelMap.put(RENAME_ETALON_LABEL_ERROR, RENAME_ETALON_OK);

		return new ModelAndView(ADMIN_RENAME_ETALON_VIEW, "model", modelMap);
	}	
	
	@RequestMapping("/**/deleteEtalon.htm")
	public ModelAndView deleteEtalon(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		int etalonId = Integer.valueOf(request.getParameter("etalonId"));
		
		boolean hasAttachedProducts = produsService.existaProduseCuEtalon(etalonId);
		
		if (hasAttachedProducts)
			modelMap.put(DELETE_ETALON_LABEL_MESSAGE, DELETE_ETALON_EXISTS_ASSOCIATED_PRODS);
		else
			modelMap.put(DELETE_ETALON_LABEL_MESSAGE, DELETE_ETALON_NO_EXISTS_ASSOCIATED_PRODS);

		return new ModelAndView(ADMIN_DELETE_ETALON_VIEW, "model", modelMap);
	}
	
	@RequestMapping("/**/finishDeleteEtalon.htm")
	public ModelAndView finishDeleteEtalon(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		int etalonId = Integer.valueOf(request.getParameter("etalonId"));
		Etalon etalon = etalonService.getEtalonById(etalonId).get(0);
		
		log.debug("Sterg etalonul cu id-ul:" + etalonId);
		etalonService.deleteEtalon(etalon, errors);
		
		if (errors.size() > 0 && errors.contains((String)FarmacieConstants.SYSTEM_ERROR)){
			modelMap.put(FINISH_DELETE_ETALON_LABEL_ERROR, FINISH_DELETE_ETALON_ERROR);
			return new ModelAndView(ADMIN_FINISH_DELETE_ETALON_VIEW, "model", modelMap);
		}
		
		log.debug("Stergerea producatorului a avut loc cu succes!");
		modelMap.put(FINISH_DELETE_ETALON_LABEL_ERROR, FINISH_DELETE_ETALON_OK);
		
		return new ModelAndView(ADMIN_FINISH_DELETE_ETALON_VIEW, "model", modelMap);
	}
	
}
