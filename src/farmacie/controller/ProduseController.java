package farmacie.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import farmacie.controller.statics.ProduseControllerStatics;
import farmacie.filters.impl.FilterProdus;
import farmacie.forms.AtributOption;
import farmacie.forms.CategorieOption;
import farmacie.forms.EtalonOption;
import farmacie.forms.ProducatorOption;
import farmacie.model.Atribut;
import farmacie.model.Categorie;
import farmacie.model.CategorieAAtributelor;
import farmacie.model.Etalon;
import farmacie.model.Producator;
import farmacie.model.view.ProdusViewFull;
import farmacie.page.Page;
import farmacie.service.AtributServiceI;
import farmacie.service.CategorieAAtributelorServiceI;
import farmacie.service.CategorieServiceI;
import farmacie.service.EtalonServiceI;
import farmacie.service.ProducatorServiceI;
import farmacie.service.ProdusServiceI;
import farmacie.service.Produs_viewServiceI;
import farmacie.sort.Sort;
import farmacie.statics.FarmacieConstants;

@Controller
@RequestMapping("/**/produse.htm")
public class ProduseController extends FarmacieControl implements ProduseControllerStatics, FarmacieConstants{

	@Autowired
	private CategorieAAtributelorServiceI categorieAAtributelorService;
	
	@Autowired
	private AtributServiceI atributService;
	
	@Autowired
	private ProducatorServiceI producatorService;		
	
	@Autowired 
	private EtalonServiceI etalonService;
	
	
	@Autowired
	private ProdusServiceI produsService;		
	
	@Autowired
	private Produs_viewServiceI produs_viewService;
	
	@Autowired
	private CategorieServiceI categorieService;
	
	@Override
	public void perform(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			Map<String, Object> modelMap) {
		
		//setez pe pagina numarul paginii intoarse
		getParametersForPage(request, page);
	
		//setez pe filtru optiunile selectate de utilizator
		getParametersForFilter(request, filter);		
		
		//setez pe sort optiunile selectate de utilizator
		getParametersForSort(request, sort);
		
			
		List<Integer> list = produs_viewService.filterProdus_view(filter, page, sort);
		if (page.isApasatLinkPaginare() == false){
			session.setAttribute(FILTER_PRODUSE, filter);
		} else {
			FilterProdus sessionFilter = (FilterProdus)session.getAttribute(FILTER_PRODUSE);
			if (sessionFilter != null){
				filter = sessionFilter;
			}
		}
		List<ProdusViewFull> listProduseViewFull = new ArrayList<ProdusViewFull>();
		for (Integer it : list) {
			listProduseViewFull.add(produsService.getProdusViewFullByIdProdus(it, Integer.valueOf(applicationProperties.getProperty(NR_MAX_CARACTERE_DESCRIERE))));
		}

		modelMap.put(FILTERED_PRODUCTS_LIST, listProduseViewFull);
		
		setParametersForPage(page, modelMap);
		setParametersForFilter(filter, modelMap);	
		setParametersForSort(sort, modelMap);
	}

	@Override
	public void setView() {
		setView("produse");
	}
	
	protected void setParametersForPage(Page page, Map<String, Object> modelMap){
		List<Integer> pagesList = new ArrayList<Integer>();
		
		int firstViewPage = (page.getCurrentPage() / 10) * 10;
		int lastViewPage = (page.getCurrentPage() / 10) * 10 + 9;
		
		if (firstViewPage == 0)
			firstViewPage = 1;
		else
			modelMap.put(IS_PREV_PAGES_CHUNK, 1);
		
		if (lastViewPage > page.getTotalPages())
			lastViewPage = page.getTotalPages();
		else
			modelMap.put(IS_NEXT_PAGES_CHUNK, "1");
		
		for (int i = firstViewPage; i <= lastViewPage; i++){
			pagesList.add(i);
		}
		
		modelMap.put(PAGES_LIST, pagesList);
		modelMap.put(PAGE_NR, page.getCurrentPage());
	}
	
	//seteaza filtrul care va fi afisat in pagina
	protected void setParametersForFilter(FilterProdus filter, Map<String, Object> modelMap){
		
		//pun id-ul categoriilor 
		if (filter.getId_categorieAll() != null && !filter.getId_categorieAll().isEmpty()){
			List<Integer> categoriiAll = filter.getId_categorieAll();
			String ids = "";
			for (int idCat : categoriiAll){
				ids += idCat + ":";
			}
			ids = ids.substring(0, ids.length() - 1);
			modelMap.put(ID_CATEGORIE_PRODUS, ids);
		}
		
		
		//punem atributele
		if (filter.getId_atributAll() != null && !filter.getId_atributAll().isEmpty()){
			Map<CategorieAAtributelor,List<AtributOption>> atributeMap = new HashMap<CategorieAAtributelor,List<AtributOption>>();
			Iterator<Integer> it_id_cat_atribute = filter.getId_atributAll().keySet().iterator();
			while (it_id_cat_atribute.hasNext()){
				int id_cat_atribute = it_id_cat_atribute.next();
				
				CategorieAAtributelor catAtribute = categorieAAtributelorService.getCategorieAAtributelorById(id_cat_atribute).get(0);
				
				List<AtributOption> atribute = new ArrayList<AtributOption>();
				Iterator<Integer> it_id_atribute = filter.getId_atributAll().get(id_cat_atribute).iterator();
				while (it_id_atribute.hasNext()){
					Atribut atribut = atributService.getAtributById(it_id_atribute.next()).get(0);
					
					AtributOption attrOption = new AtributOption();
					attrOption.setAtribut(atribut);
					if (!filter.getId_atribut().isEmpty() && filter.getId_atribut().get(id_cat_atribute) != null && filter.getId_atribut().get(id_cat_atribute).contains(atribut.getId())){
						attrOption.setSelected(true);
					}
					atribute.add(attrOption);
				}
				atributeMap.put(catAtribute, atribute);			
			}
			modelMap.put(ATRIBUTE_MAP, atributeMap);
		}
		
		
		//pun producatorii
		
		if (filter.getId_producatorAll() != null && !filter.getId_producatorAll().isEmpty()){
			List<ProducatorOption> producatoriMap = new ArrayList<ProducatorOption>();
			Iterator<Integer> it_id_producatori = filter.getId_producatorAll().iterator();
			while (it_id_producatori.hasNext()){
				Producator producator = producatorService.getProducatorById(it_id_producatori.next()).get(0);
				
				ProducatorOption prodOption = new ProducatorOption();
				prodOption.setProducator(producator);
				if (!filter.getId_producator().isEmpty() && filter.getId_producator().contains(producator.getId()))
					prodOption.setSelected(true);
				producatoriMap.add(prodOption);
			}		
			modelMap.put(PRODUCATORI_MAP, producatoriMap);
		}
		
		//pun categoriile
		if (filter.getId_categorieAll() != null && !filter.getId_categorieAll().isEmpty()){
			List<CategorieOption> categoriiMap = new ArrayList<CategorieOption>();
			Iterator<Integer> itIdCategorie = filter.getId_categorieAll().iterator();
			while (itIdCategorie.hasNext()){
				Categorie categorie = categorieService.getCategorieById(itIdCategorie.next()).get(0);
				
				CategorieOption catOption = new CategorieOption();
				catOption.setCategorie(categorie);
				if (!filter.getId_categorie().isEmpty() && filter.getId_categorie().contains(categorie.getId())){
					catOption.setSelected(true);
				}
				categoriiMap.add(catOption);
			}
			modelMap.put(CATEGORII_MAP, categoriiMap);
		}		
		
		
		//pun etaloanele
		if (filter.getId_etalonAll()!= null && !filter.getId_etalonAll().isEmpty()){
			List<EtalonOption> etaloaneMap = new ArrayList<EtalonOption>();
			Iterator<Integer> it_id_etalon = filter.getId_etalonAll().iterator();
			while (it_id_etalon.hasNext()){
				Etalon etalon = etalonService.getEtalonById(it_id_etalon.next()).get(0);
				
				EtalonOption etOption = new EtalonOption();
				etOption.setEtalon(etalon);
				if (!filter.getId_etalon().isEmpty() && filter.getId_etalon().contains(etalon.getId())){
					etOption.setSelected(true);
				}
				
				etaloaneMap.add(etOption);
			}
			modelMap.put(ETALOANE_MAP, etaloaneMap);
		}
		
		//pun produsele in stock, sau toate
		if (filter.isInStock())
			modelMap.put(SHOW_IN_STOCK, "1");
		else
			modelMap.put(SHOW_IN_STOCK, "0");
		
		//pun textul pentru cautare
		if (filter.getCrumb() != null && !filter.getCrumb().isEmpty()){
			modelMap.put(SEARCH_FIELD, filter.getCrumb());
		}
		
		//pun preturile
/*		if (filter.getPret_produs() != null && !filter.getPret_produs().isEmpty()){
			List<Integer> preturiMap = new ArrayList<Integer>();
			double prag = Math.ceil(new Double(filter.getPret_produs().get(0)));
			for (double i = 0 ; i < filter.getPret_produs().get(1); i += prag){
				preturiMap.add(new Double(i).intValue());
			}
			preturiMap.add(filter.getPret_produs().get(1).intValue());
			modelMap.put(PRETURI_MAP, preturiMap);
		}*/
		
		//pun cantitatea
/*		if (filter.getCantitate_produs() != null && !filter.getCantitate_produs().isEmpty()){
			List<Integer> cantitateArray = new ArrayList<Integer>();
			int prag =  (filter.getCantitate_produs().get(1) - filter.getCantitate_produs().get(0)) / 5;
			for (int i=0; i < filter.getCantitate_produs().get(1); i+=prag){
				cantitateArray.add(i);
			}
			cantitateArray.add(filter.getCantitate_produs().get(1));
			List<List<Integer>> cantitateMap= new ArrayList<List<Integer>>();
			
			int j=0;
			while (j < cantitateArray.size() - 1){
				List<Integer> tmp = new ArrayList<Integer>();
				tmp.add(cantitateArray.get(j));tmp.add(cantitateArray.get(j+1));
				cantitateMap.add(tmp);
				
				j++;
			}
			
			modelMap.put(CANTITATE_MAP, cantitateMap);		
		} */
	}
	
	protected void setParametersForSort(Sort sort, Map<String, Object> modelMap){
		log.debug("Sunt in setParametersForSort si sortField este: " + sort.getSortField());
		
		if (sort.getSortField() != null && sort.getSortField().equals(Sort.PRICE_SORT_FIELD)){
			if (sort.getSortOrder().equals(Sort.SORT_ASC)){
				modelMap.put(SORT_FIELD, Sort.SORT_PRICE_ASC);
			} else {
				modelMap.put(SORT_FIELD, Sort.SORT_PRICE_DESC);
			}
		} else {
			modelMap.put(SORT_FIELD, "");
		}
	}
	
	protected void getParametersForSort(HttpServletRequest request, Sort sort){
		String sortField = request.getParameter(SORT_FIELD);
		
		log.debug("Sunt in getParametersForFilter si sortfield este: " + sortField);
		
		if (sortField != null){
			if (sortField.equals(Sort.SORT_PRICE_ASC)){
				sort.setSortField(Sort.PRICE_SORT_FIELD);
				sort.setSortOrder(Sort.SORT_ASC);
			} else 
			if (sortField.equals(Sort.SORT_PRICE_DESC)){
				sort.setSortField(Sort.PRICE_SORT_FIELD);
				sort.setSortOrder(Sort.SORT_DESC);
			} else {
				sort.setSortOrder(Sort.NO_SORT);				
			}
		} else {
			sort.setSortOrder(Sort.NO_SORT);				
		}
	}
	
	protected void getParametersForPage(HttpServletRequest request, Page page){
		Integer page_nr = null;
		
		if (request.getParameter(PAGE_NR) != null)
			page_nr = getInteger(request.getParameter(PAGE_NR));
		
		if (page_nr == null){
			page.setCurrentPage(1);
		} else {
			String action = request.getParameter("action");
			
			if (action != null && action.startsWith("paginare")){
				page.setApasatLinkPaginare(true);				
			} else {
				page.setApasatLinkPaginare(false);
			}
			
			if (action != null && action.startsWith("paginare_")){
				if (action.equals(PAGINARE_FIRST)){
					page.setCurrentPage(1);
				} else
				if (action.equals(PAGINARE_PREVIOUS)) {
					page.setCurrentPage((page_nr / 10) * 10 - 1);
				} else
				if (action.equals(PAGINARE_NEXT)) {
					page.setCurrentPage(((page_nr + 10) / 10) * 10);
				} else
				if (action.equals(PAGINARE_LAST)) {
					page.setCurrentPage(999999999);
				}
			} else {
				page.setCurrentPage(page_nr);
			}
		}
	}
	
	protected void getParametersForFilter(HttpServletRequest request, FilterProdus filter){
		
		//categoriile selectate
		String search = request.getParameter("Search");
		String [] idCategories = request.getParameterValues(SELECTED_CATEGORIES);
		if (idCategories != null && idCategories.length > 0 && !idCategories[0].equals("0")){
			for (String idCat : idCategories){
				Integer id_categorie = getInteger(idCat);
				if (id_categorie == null)
				{
					filter.setId_categorie(null);
					log.debug("Id-ul categoriei este invalid, iesim...");
					return;
				} else {
					if (search != null && !search.isEmpty()){
						filter.getId_categorie().addAll(categorieService.getIdToateSubcategoriile(id_categorie));
					}else {
						filter.getId_categorie().add(id_categorie);
					}
				}			
			}
		}
		
		
		String crumb = request.getParameter(SEARCH_FIELD);
		if (crumb != null && !crumb.isEmpty()){
			filter.setCrumb(crumb);
		}
		
		//produsele in stoc
		String stock = request.getParameter(IN_STOCK);
		if (stock != null && stock.equals("1"))
			filter.setInStock(true);
		else
			filter.setInStock(false);		
		
		//iau atributele selectate
		String[] attrsArray = request.getParameterValues(SELECTED_ATTRS);
		if (attrsArray != null){
			for (String attr : attrsArray){
				Integer id_cat_attr = getInteger(attr.substring(0, attr.indexOf("|")));
				Integer id_attr = getInteger(attr.substring(attr.indexOf("|") + 1, attr.length()));
				if (id_cat_attr == null || id_attr == null){
					log.debug("Unul din id-urile categorie atribut sau atribue este null, ies..." + id_cat_attr + " : " + id_attr);
					return;
				}
				log.debug("Am selectat categoria:" + id_cat_attr + " si atributul:" + id_attr);
				
				if (filter.getId_atribut().containsKey(id_cat_attr)){
					filter.getId_atribut().get(id_cat_attr).add(id_attr);
				} else {
					List<Integer> tmp = new ArrayList<Integer>();
					tmp.add(id_attr);
					filter.getId_atribut().put(id_cat_attr, tmp);
				}
			}
		}
		
		//iau producatorii selectati
		String[] producatoriArray = request.getParameterValues(SELECTED_PRODUCERS);
		if (producatoriArray != null){
			for (String prod : producatoriArray){
				Integer idProducator = getInteger(prod);
				if (idProducator == null) {
					log.debug("Id-ul producatorului este nul, iesim...");
					return;
				}
				log.debug("Am selectat producatorul cu id-ul:" + idProducator);
				filter.getId_producator().add(idProducator);
			}
		}
		
		//iau etaloanele selectate
		String[] etalonArray = request.getParameterValues(SELECTED_ETALON);
		if (etalonArray != null){
			for (String etalon : etalonArray){
				Integer idEtalon = getInteger(etalon);
				if (idEtalon == null) {
					log.debug("Id-ul etalonului este nul, iesim...");
					return;
				}
				log.debug("Am selectat producatorul cu id-ul:" + idEtalon);
				filter.getId_etalon().add(idEtalon);
			}
		}
		
		
		
		
	}

	public Map<String, Object> getObjectsFromFilter(FilterProdus filter) {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
