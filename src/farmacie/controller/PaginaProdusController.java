package farmacie.controller;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import farmacie.model.Categorie;
import farmacie.service.CategorieServiceI;
import farmacie.service.ProdusServiceI;

@Controller
@RequestMapping("/**/paginaProdus.htm")
public class PaginaProdusController extends FarmacieControl {

	@Autowired
	private Properties applicationProperties;	
	
	@Autowired
	private CategorieServiceI categorieService;	
	
	@Autowired
	private ProdusServiceI produsService;	
	
	@Override
	public void perform(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			Map<String, Object> modelMap) {
		Integer id_prod_selectat = 0;
		
		//setez meniul cu produse
/*		List<Categorie> pop_up_menu_categorii = (List<Categorie>) session.getAttribute(POP_UP_MENU_CATEGORII);
		if (pop_up_menu_categorii != null){
			modelMap.put(POP_UP_MENU_CATEGORII, pop_up_menu_categorii);
		} else {
			pop_up_menu_categorii = getXMLPopUpMenuCategorii(categorieService);
			modelMap.put(POP_UP_MENU_CATEGORII, pop_up_menu_categorii);
			session.setAttribute(POP_UP_MENU_CATEGORII, pop_up_menu_categorii);
		}*/	
		
		//iau id-ul produsului selectat
		id_prod_selectat = getInteger(request.getParameter(ID_PRODUS_SELECTAT));
		if (id_prod_selectat == null)
		{
			modelMap.put(ERROR, SYSTEM_ERROR);
			return;
		}
		
		//setez parametrul din view la valoarea produslui selectat
		modelMap.put(VIEW_PRODUS_SELECTAT, produsService.getProdusViewFullByIdProdus(id_prod_selectat, -1));
	}

	@Override
	public void setView() {
		setView("paginaProdus");

	}

}
