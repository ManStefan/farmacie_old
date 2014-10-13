package farmacie.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import farmacie.model.view.ProdusViewLite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



import farmacie.service.Arbore_categoriiServiceI;
import farmacie.service.CategorieServiceI;

import farmacie.service.ProdusServiceI;


@Controller
@RequestMapping("/**/index.htm")
public class HomePageController extends FarmacieControl{
	
	@Autowired
	private Properties applicationProperties;
	
	@Autowired
	private ProdusServiceI produsService;

	@Autowired
	private CategorieServiceI categorieService;
	
	@Autowired
	private Arbore_categoriiServiceI arboreCategoriiService;

	@Override
	public void perform(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Map<String, Object> modelMap) {
		
		//pun cele mai noi produse
		modelMap.put(CELE_MAI_NOI_PRODUSE_PE_CATEGORII, getCeleMaiNoiProdusePeCategorii(new Integer(applicationProperties.getProperty("cele_mai_noi_produse_pe_categorii"))));
		modelMap.put(NUMAR_DE_PROD_PE_LINIE, new Integer(applicationProperties.getProperty("numar_de_prod_pe_linie")));
	}
	


	//metoda care imi retunreaza cele mai noi nr_prod produse pe categorii
	protected Map<String,List<ProdusViewLite>> getCeleMaiNoiProdusePeCategorii(int nr_prod){
		List<Integer> mainCaths = categorieService.getIdCategorieByNivel(1);
		//Map<Integer,List<Integer>> subCategories = new HashMap<Integer, List<Integer>>();
		Map<Integer, List<Integer>> prod_pe_cat = new HashMap<Integer, List<Integer>>();
		for (int mainCat : mainCaths){
			List<Integer> subCaths = arboreCategoriiService.getCathegoriesUnderCat(mainCat);			
			if (subCaths != null && !subCaths.isEmpty()){
				List<Integer> prods = produsService.getNewestProdsInCategories(subCaths, nr_prod);
				if (prods != null && !prods.isEmpty()){
					prod_pe_cat.put(mainCat, prods);
				}
			}	
		}
		
		//Map<Integer, List<Integer>> prod_pe_cat = produsService.getNewestNProdsByCat(nr_prod);
		
		Map<String, List<ProdusViewLite>> nume_prod_pe_cat= new HashMap<String, List<ProdusViewLite>>();
		
		Iterator<Integer> it_keys = prod_pe_cat.keySet().iterator();
		while (it_keys.hasNext()){
			int key = it_keys.next();
	
			if (!prod_pe_cat.get(key).isEmpty()){
				String string_key = categorieService.getCategorieById(key).get(0).getNume();
				
				List<ProdusViewLite> produseViewLite = new ArrayList<ProdusViewLite>();
				
				Iterator<Integer> it_value = prod_pe_cat.get(key).iterator();
				while (it_value.hasNext()){
					produseViewLite.add(produsService.getProdusViewLiteByIdProdus(it_value.next(), Integer.valueOf(applicationProperties.getProperty(NR_MAX_CARACTERE_DESCRIERE))));
				}
				
				nume_prod_pe_cat.put(string_key, produseViewLite);
			}
			
		}
				
		return nume_prod_pe_cat;
	}
	
	@Override
	public void setView() {
		setView("homepage");
	}
}