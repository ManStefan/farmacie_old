package farmacie.admin.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import farmacie.admin.controller.statics.AdminControllerUtilsStatics;
import farmacie.admin.controller.statics.AdminProduseControllerStatics;
import farmacie.controller.ControllerUtils;
import farmacie.json.JSONArray;
import farmacie.json.JSONException;
import farmacie.json.JSONObject;
import farmacie.model.Atribut;
import farmacie.model.Atribut_produs;
import farmacie.model.Categorie;
import farmacie.model.CategorieAAtributelor;
import farmacie.model.Etalon;
import farmacie.model.Poza;
import farmacie.model.Producator;
import farmacie.model.Produs;
import farmacie.service.AtributServiceI;
import farmacie.service.Atribut_produsServiceI;
import farmacie.service.PozaServiceI;
import farmacie.service.ProdusServiceI;
import farmacie.statics.FarmacieConstants;

@Controller
public class AdminProduseController implements AdminProduseControllerStatics, FarmacieConstants, AdminControllerUtilsStatics, HandlerExceptionResolver{
	private Logger log = Logger.getLogger(this.getClass());
	
	private static SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	@Autowired
	private ProdusServiceI produsService;
	
	@Autowired
	private AtributServiceI atributService;
		
	@Autowired
	private PozaServiceI pozaService;
	
	@Autowired
	private Atribut_produsServiceI atributProdusService;
	
    @Autowired
    @Qualifier("applicationProperties")
    protected Properties applicationProperties;	
	
	@RequestMapping("/**/adminProduse.htm")
	public ModelAndView handleAdaugareRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		session.removeAttribute(NR_OF_ATTRIBUTES_IN_PAGE);
		session.removeAttribute(NR_OF_IMAGES_IN_PAGE);
		
		Integer nrOfAttributesInPage = 0;
		session.setAttribute(NR_OF_ATTRIBUTES_IN_PAGE, nrOfAttributesInPage);
		
		Integer nrOfImagesInPage = 0;
		session.setAttribute(NR_OF_IMAGES_IN_PAGE, nrOfImagesInPage);

		return new ModelAndView(ADMIN_PRODUSE_VIEW, "model", modelMap);
	}
	
	@RequestMapping("/**/stergeProdus.htm")
	public ModelAndView stergeProdus(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		int idProdus = Integer.valueOf(request.getParameter("idProdus"));
		produsService.deleteProdus(idProdus, errors);
		
		if (errors.size() > 0){
			modelMap.put(STERGE_PRODUS_ERROR, STERGE_PRODUS_SYSTEM_ERROR_LABEL);
			return new ModelAndView(ADMIN_STERGE_PRODUS_VIEW, "model", modelMap);
		}
		
		modelMap.put(STERGE_PRODUS_ERROR, STERGE_NEW_PRODUCT_OK_LABEL);
		return new ModelAndView(ADMIN_STERGE_PRODUS_VIEW, "model", modelMap);
	}	
	
	@RequestMapping("/**/adminEditareProduse.htm")
	public ModelAndView handleEditareRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();

		session.removeAttribute(NR_OF_ATTRIBUTES_IN_PAGE);
		session.removeAttribute(NR_OF_IMAGES_IN_PAGE);
		
		Integer nrOfAttributesInPage = 0;
		session.setAttribute(NR_OF_ATTRIBUTES_IN_PAGE, nrOfAttributesInPage);
		
		Integer nrOfImagesInPage = 0;
		session.setAttribute(NR_OF_IMAGES_IN_PAGE, nrOfImagesInPage);		
		
		return new ModelAndView(ADMIN_EDITARE_PRODUSE_VIEW, "model", modelMap);
	}
	
	
	@RequestMapping("/**/atributeForProduct.htm")
	public ModelAndView addAttributeForProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		Integer nrOfAttributesInPage = (Integer)session.getAttribute(NR_OF_ATTRIBUTES_IN_PAGE);
		nrOfAttributesInPage++;
		session.setAttribute(NR_OF_ATTRIBUTES_IN_PAGE, nrOfAttributesInPage);
		
		String idAtributStr = request.getParameter(ID_ATRIBUT_EXISTENT);
		if (idAtributStr != null && !idAtributStr.isEmpty()){
			int idAtribut = Integer.valueOf(idAtributStr);
			Atribut atributExistent = atributService.getAtributById(idAtribut).get(0);
			CategorieAAtributelor catAAtributExistent = atributService.getCategorieAAtributului(atributExistent);
			
			modelMap.put(ID_ATRIBUT_EXISTENT, idAtribut);
			modelMap.put(NUME_ATRIBUT_EXISTENT, atributExistent.getNume());
			modelMap.put(ID_CATEGORIE_ATRIBUT_EXISTENT, catAAtributExistent.getId());
			modelMap.put(NUME_CAT_ATRIBUT_EXISTENT, catAAtributExistent.getNume());
			
		}
		
		modelMap.put(NR_OF_ATTRIBUTES_IN_PAGE, nrOfAttributesInPage);

		return new ModelAndView(ATRIBUTE_FOR_PRODUCT_VIEW, "model", modelMap);
	}
	
	@RequestMapping("/**/imageForProduct.htm")
	public ModelAndView addImageForProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		Integer nrOfImagesInPage = (Integer)session.getAttribute(NR_OF_IMAGES_IN_PAGE);
		nrOfImagesInPage++;
		session.setAttribute(NR_OF_IMAGES_IN_PAGE, nrOfImagesInPage);
		
		modelMap.put(NR_OF_IMAGES_IN_PAGE, nrOfImagesInPage);
		
		return new ModelAndView(IMAGE_FOR_PRODUCT_VIEW, "model", modelMap);
	}	
	
	@RequestMapping("/**/valideazaProdus.htm")
	public ModelAndView valideazaProdus(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		String idProdusSelectatStr = request.getParameter("idProdus");
		int idProdusSelectat = 0;
		if (idProdusSelectatStr != null && !idProdusSelectatStr.isEmpty()){
			idProdusSelectat = Integer.valueOf(idProdusSelectatStr);
		}
		
		Produs produsSelectat = null;
		if (idProdusSelectat > 0){
			produsSelectat = produsService.getProduseById(idProdusSelectat).get(0);
		}
		
		String numeProdus = request.getParameter("numeProdus");
		List<Produs> produseByNume = produsService.getProduseByNume(numeProdus); 
		if (!produseByNume.isEmpty()){
			if (produsSelectat != null){
				if (!produseByNume.contains(produsSelectat)){
					modelMap.put(VALIDATE_PRODUCT_ERROR, DUPLICATE_PRODUCT_MESSAGE);
					return new ModelAndView(VALIDEAZA_PRODUS_VIEW, "model", modelMap);					
				}
			} else {
				modelMap.put(VALIDATE_PRODUCT_ERROR, DUPLICATE_PRODUCT_MESSAGE);
				return new ModelAndView(VALIDEAZA_PRODUS_VIEW, "model", modelMap);
			}
		}
		
		String atribute = request.getParameter("atribute");
		String[] catAtrs = atribute.split("\\|\\|");
		Set<String> dupl = new HashSet<String>();
		for (String catAtr : catAtrs){
			dupl.add(catAtr);
		}
		if (dupl.size() < catAtrs.length){
			modelMap.put(VALIDATE_PRODUCT_ERROR, DUPLICATE_ATTRS_MESSAGE);
			return new ModelAndView(VALIDEAZA_PRODUS_VIEW, "model", modelMap);
		}
		
		modelMap.put(VALIDATE_PRODUCT_ERROR, VALIDATE_PRODUCT_OK);
		return new ModelAndView(VALIDEAZA_PRODUS_VIEW, "model", modelMap);
	}	
	

	@RequestMapping("/**/modificaProdus.htm")
	public ModelAndView modificaProdus(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<String> errors = new ArrayList<String>();
		
		int produsId = Integer.valueOf(request.getParameter("selectedProduseId1"));
		
		Produs produsModificat = new Produs();
		produsModificat.setId(produsId);
		
		atributProdusService.deleteAllAtribut_produsByProdus(produsModificat, errors);
		
		try {
			adaugaNumeProdus(multipartRequest, produsModificat, MOD_PRODUS_NAME, MOD_PRODUS_DESCRIERE, MOD_PRODUS_PRET, MOD_PRODUS_CANTITATE);
			adaugaCategorieLaProdus(multipartRequest, produsModificat, MOD_PRODUS_CATEGORIE);
			adaugaProducatorLaProdus(multipartRequest, produsModificat, MOD_PRODUS_PRODUCATOR);
			adaugaEtalonLaProdus(multipartRequest, produsModificat, MOD_PRODUS_ETALON);
			adaugaAtributeLaProdus(multipartRequest, session, produsModificat);
			adaugaPozeLaProdus(multipartRequest, session, produsModificat);
			
			produsService.updateProdus(produsModificat, errors);
			if (errors.size() > 0){
				if (errors.contains(DUPLICATE_ENTRY_ERROR)){
					modelMap.put(UPDATE_PRODUCT_ERROR, UPDATE_PRODUCT_DUPLICATE_ERROR_LABEL);
					log.error("Eroare la adaugare produs nou: produsul era deja in baza de date!");					
				} else {
					modelMap.put(UPDATE_PRODUCT_ERROR, UPDATE_PRODUCT_SYSTEM_ERROR_LABEL);
					log.error("Eroare de sistem la adaugare produs nou!");
				}
				
				return new ModelAndView(UPDATE_PRODUS_NOU_VIEW, "model", modelMap);
			}			
			
		} catch (Exception e) {
			modelMap.put(UPDATE_PRODUCT_ERROR, UPDATE_PRODUCT_SYSTEM_ERROR_LABEL);
			log.error("Eroare in " + this.getClass() + " :" + ControllerUtils.getStackTrace(e));
			return new ModelAndView(UPDATE_PRODUS_NOU_VIEW, "model", modelMap);
		}
		
		
		modelMap.put(UPDATE_PRODUCT_ERROR, ADD_NEW_PRODUCT_OK);
		return new ModelAndView(UPDATE_PRODUS_NOU_VIEW, "model", modelMap);

	}	
	
	
	@RequestMapping("/**/adaugaProdusNou.htm")
	public ModelAndView adaugaProdusNou(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<String> errors = new ArrayList<String>();
		Produs newProdus = new Produs();
		
		log.debug("Multipart encoding !!!!!!!!!" + multipartRequest.getCharacterEncoding());
		
		try {
			adaugaNumeProdus(multipartRequest, newProdus, NEW_PRODUS_NAME, NEW_PRODUS_DESCRIERE, NEW_PRODUS_PRET, NEW_PRODUS_CANTITATE);		
			adaugaCategorieLaProdus(multipartRequest, newProdus, NEW_PRODUS_CATEGORIE);
			adaugaProducatorLaProdus(multipartRequest, newProdus, NEW_PRODUS_PRODUCATOR);
			adaugaEtalonLaProdus(multipartRequest, newProdus, NEW_PRODUS_ETALON);
			adaugaAtributeLaProdus(multipartRequest, session, newProdus);
			adaugaPozeLaProdus(multipartRequest, session, newProdus);
			
			produsService.addProdus(newProdus, errors);
			if (errors.size() > 0){
				if (errors.contains(DUPLICATE_ENTRY_ERROR)){
					modelMap.put(ADD_NEW_PRODUCT_ERROR, ADD_NEW_PRODUCT_DUPLICATE_ERROR_LABEL);
					log.error("Eroare la adaugare produs nou: produsul era deja in baza de date!");					
				} else {
					modelMap.put(ADD_NEW_PRODUCT_ERROR, ADD_NEW_PRODUCT_SYSTEM_ERROR_LABEL);
					log.error("Eroare de sistem la adaugare produs nou!");
				}
				
				return new ModelAndView(ADAUGA_PRODUS_NOU_VIEW, "model", modelMap);
			}			
			
		} catch (Exception e) {
			modelMap.put(ADD_NEW_PRODUCT_ERROR, ADD_NEW_PRODUCT_SYSTEM_ERROR_LABEL);
			log.error("Eroare in " + this.getClass() + " :" + ControllerUtils.getStackTrace(e));
			return new ModelAndView(ADAUGA_PRODUS_NOU_VIEW, "model", modelMap);
		}
		
		
		modelMap.put(ADD_NEW_PRODUCT_ERROR, ADD_NEW_PRODUCT_OK);
		return new ModelAndView(ADAUGA_PRODUS_NOU_VIEW, "model", modelMap);
	}	
	
	private void adaugaAtributeLaProdus(MultipartHttpServletRequest request, HttpSession session, Produs produs) throws ServletRequestBindingException{
		Integer nrOfAttributesInPage = (Integer)session.getAttribute(NR_OF_ATTRIBUTES_IN_PAGE);
		
		List<Atribut_produs> atributProdusList = new ArrayList<Atribut_produs>();
		
		for (int i = 1; i <= nrOfAttributesInPage; i++){
			Integer attrId = null;
			try {
				String tmpAttrId = ServletRequestUtils.getStringParameter(request, "selectedAttr" + i);
				if (tmpAttrId != null){
					attrId = Integer.valueOf(tmpAttrId);
				}
			} catch (NumberFormatException e) {
				log.error("Eroare la adaugare atribut pentru produs :" + e.getMessage());
			}
			
			if (attrId != null){
				//Atribut attr = atributService.getAtributById(attrId).get(0);
				Atribut attr = new Atribut();
				attr.setId(attrId.intValue());
				
				Atribut_produs attrProd = new Atribut_produs();
				attrProd.setProdus(produs);
				attrProd.setAtribut(attr);
				
				atributProdusList.add(attrProd);
				log.debug("Adaug atributul:" + attrProd);
			}
		}
		produs.setAtributeProdus(atributProdusList);
	}
	
	private void adaugaPozeLaProdus(MultipartHttpServletRequest request, HttpSession session, Produs produs) throws IOException, ServletRequestBindingException{
		Integer nrOfImagesInPage = (Integer)session.getAttribute(NR_OF_IMAGES_IN_PAGE);
		
		List<Poza> poze = new ArrayList<Poza>();
		
		for (int i = 1; i <= nrOfImagesInPage; i++){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("imagineProdus" + i);
			
			if (multipartFile != null){
				String numePoza = multipartFile.getOriginalFilename();
				String extensiePoza = numePoza.substring(numePoza.lastIndexOf(".") + 1, numePoza.length());
				log.debug("Incarc poza cu numele: " + numePoza);
				
				String path = "img_p_" + "_" + timeStampFormat.format(new Date()) + "." + extensiePoza;
				FileOutputStream newFile = new FileOutputStream(applicationProperties.getProperty("cale_stocare_imagini_prod") + path);
				newFile.write(multipartFile.getBytes());
				newFile.close();				
				
				String descriere = ServletRequestUtils.getStringParameter(request, "descrierePozaProdus" + i);
				
				Poza poza = new Poza();
				poza.setPoza(path);
				poza.setProdus(produs);
				if (descriere != null){
					poza.setDescriere(descriere);
				}
				poze.add(poza);
				//pozaService.addPoza(poza);
			}
		}
		produs.setPoza(poze);
	}
	
	private void adaugaNumeProdus(MultipartHttpServletRequest request, Produs produs, String produsName, String produsDescriere,
			String produsPret, String produsCantitate) throws ServletRequestBindingException{
		
		String newProdusName = ServletRequestUtils.getStringParameter(request, produsName);
		String newProdusDescriere = ServletRequestUtils.getStringParameter(request, produsDescriere);
		Double newProdusPret = new Double(ServletRequestUtils.getStringParameter(request, produsPret));
		Integer newProdusCantitate = new Integer(ServletRequestUtils.getStringParameter(request, produsCantitate));
		
		produs.setNume(newProdusName);
		produs.setDescriere(newProdusDescriere);
		produs.setPret(newProdusPret);
		produs.setCantitate(newProdusCantitate);
		produs.setDataCreeare(new Date()); 
		
	}
	
	private void adaugaCategorieLaProdus(MultipartHttpServletRequest request, Produs produs, String produsCategorie) throws NumberFormatException, ServletRequestBindingException{
		Integer newProdusCategorieId = new Integer(ServletRequestUtils.getStringParameter(request, produsCategorie));
		
		Categorie categorie = new Categorie();
		categorie.setId(newProdusCategorieId);
		produs.setCategorie(categorie);
	}
	
	public void adaugaProducatorLaProdus(MultipartHttpServletRequest request, Produs produs, String producatorCategorie) throws NumberFormatException, ServletRequestBindingException{
		Integer newProducatorProdusId = new Integer(ServletRequestUtils.getStringParameter(request, producatorCategorie));
		
		Producator producator = new Producator();
		producator.setId(newProducatorProdusId);
		produs.setProducator(producator);
	}
	
	public void adaugaEtalonLaProdus(MultipartHttpServletRequest request, Produs produs, String producatorEtalon) throws NumberFormatException, ServletRequestBindingException{
		Integer newEtalonProdusId = new Integer(ServletRequestUtils.getStringParameter(request, producatorEtalon));
		
		Etalon etalon = new Etalon();
		etalon.setId(newEtalonProdusId);
		produs.setEtalon(etalon);
	}
	
	@RequestMapping("/**/searchProduseDupaNumeCategorie.htm")
	public ModelAndView searchProduseDupaNumeCategorie(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		session.removeAttribute(NR_OF_ATTRIBUTES_IN_PAGE);
		session.removeAttribute(NR_OF_IMAGES_IN_PAGE);
		
		Integer nrOfAttributesInPage = 0;
		session.setAttribute(NR_OF_ATTRIBUTES_IN_PAGE, nrOfAttributesInPage);
		
		Integer nrOfImagesInPage = 0;
		session.setAttribute(NR_OF_IMAGES_IN_PAGE, nrOfImagesInPage);				
		
		String crumb = request.getParameter("term");
		Integer categorie = null; 
		
		String idCatStr = request.getParameter("id_categorie").trim();
		if (idCatStr == null || idCatStr.isEmpty()){
			categorie = null;
		} else {
			categorie = Integer.valueOf(idCatStr);
		}
		
		if (crumb.equals("*"))
			crumb = "";

		List<Produs> produse = produsService.searchProduseSimplu(crumb, categorie, NR_OF_RECORDS_SHOWN);
		
		JSONArray json = new JSONArray();			
		
		for (Produs produs : produse){
			
			Map<String, String> jsonNode = new HashMap<String, String>();
			
			jsonNode.put("id", new Integer(produs.getId()).toString());
			jsonNode.put("label", produs.getNume());
			jsonNode.put("value", produs.getNume());
//			jsonNode.put("searchedNrOfRecords", totalNrOfRecords);
//			jsonNode.put("totalNrOfRecords", searchedNrOfRecords);
			
			json.put(jsonNode);
		}
		
		modelMap.put(PRODUSE_SEARCH_DUPA_NUME_RESULTS, json);
		
		return new ModelAndView(PRODUSE_SEARCH_DUPA_NUME_VIEW, "model", modelMap);		
	}
	
	@RequestMapping("/**/obtineDetaliiProdusSelectat.htm")
	public ModelAndView obtineDetaliiProdusSelectat(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
	
		int idProdSel = Integer.valueOf(request.getParameter("id_produs_selectat"));

		Produs produsSel = produsService.getProduseById(idProdSel).get(0);
		
		
		
		JSONObject jSonProdus = new JSONObject();
		try {
			jSonProdus.put("nume", produsSel.getNume());
			jSonProdus.put("descriere", produsSel.getDescriere());
			jSonProdus.put("pret", produsSel.getPret());
			jSonProdus.put("cantitate", produsSel.getCantitate());
			Categorie categorie = produsService.getCategorieForProdus(produsSel);
			jSonProdus.put("categorie", categorie.getId() + ":" + categorie.getNume());
			Producator producator = produsService.getProducatorForProdus(produsSel);
			jSonProdus.put("producator", producator.getId() + ":" + producator.getNume());
			Etalon etalon = produsService.getEtalonForProdus(produsSel);
			jSonProdus.put("etalon", etalon.getId() + ":" + etalon.getNume());
			
			List<Atribut> atribute = produsService.getAtributeForProdus(produsSel);
			if (atribute != null && !atribute.isEmpty()){
				JSONArray jsonAtribute = new JSONArray();
				jSonProdus.put("atribute", jsonAtribute);
				for (Atribut atribut : atribute){
					jsonAtribute.put(atribut.getId());
				}
			}
			
			List<Poza> poze = produsService.getPozaByProdus(produsSel);
			if (poze != null && !poze.isEmpty()){
				JSONArray jsonPoze = new JSONArray();
				jSonProdus.put("poze", jsonPoze);
				for (Poza poza : poze){
					jsonPoze.put(poza.getId() + "|" + poza.getDescriere()  + "|" + poza.getPoza());
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		modelMap.put(DETALII_PRODUS_SELECTAT, jSonProdus);
		
		
		return new ModelAndView(DETALII_PRODUS_SELECTAT_VIEW, "model", modelMap);
	}
	
	
	@RequestMapping("/**/deleteExistingPicture.htm")
	public ModelAndView deleteExistingPicture(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		
		String idPozaStr = request.getParameter("id_poza");
		int idPoza = 0;
		if (idPozaStr != null && !idPozaStr.isEmpty()){
			idPoza = Integer.valueOf(idPozaStr);
		}
		
		Poza poza = new Poza(); poza.setId(idPoza);
		pozaService.deletePoza(poza, errors);
		
		if (errors.size() > 0){
			modelMap.put(DELETE_EXISTING_PICTURE_RESULT, DELETE_EXISTING_PICTURE_ERROR_LABEL);
		} else {
			modelMap.put(DELETE_EXISTING_PICTURE_RESULT, DELETE_EXISTING_PICTURE_OK_LABEL);
		}
		
		
		
		return new ModelAndView(DELETE_EXISTING_PICTURE_VIEW, "model", modelMap);
		
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3) {
	    if (arg3 instanceof MaxUploadSizeExceededException) {
	        ModelAndView modelAndView = new ModelAndView("inline-error");
	        modelAndView.addObject("error", "Eroare:  Una dintre poze are marimea mai mare de 1MB!");
	        return modelAndView;
	    }
	    arg3.printStackTrace();
	    return new ModelAndView("500");
	}	
	
}
