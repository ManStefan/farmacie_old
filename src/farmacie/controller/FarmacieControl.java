package farmacie.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import farmacie.filters.impl.FilterProdus;
import farmacie.model.Categorie;
import farmacie.page.Page;
import farmacie.service.CategorieServiceI;
import farmacie.sort.Sort;
import farmacie.statics.FarmacieConstants;


@Controller
public abstract class FarmacieControl implements FarmacieConstants{

//http://okkk.3x.ro/Tabel%20diacritice%20HTML.htm	
	
	protected Logger log = Logger.getLogger(this.getClass());
	
    @Autowired
    @Qualifier("applicationProperties")
    protected Properties applicationProperties;
	
	@Autowired
	private CategorieServiceI categorieService;
	
	String view;
	
	Page page;
	
	Sort sort;
	
	FilterProdus filter;
	
	@RequestMapping
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
	       
		response.setCharacterEncoding("utf-8");
		
        Map<String, Object> modelMap = new HashMap<String, Object>();
        setView();
        
        page = new Page();
        
        sort = new Sort();
        
        filter = new FilterProdus();
       
        //face procesarea
        try {
        	
    		//setez meniul cu produse
    		List<Categorie> pop_up_menu_categorii = (List<Categorie>) session.getAttribute(POP_UP_MENU_CATEGORII);
    		if (pop_up_menu_categorii != null){
    			modelMap.put(POP_UP_MENU_CATEGORII, pop_up_menu_categorii);
    		} else {
    			pop_up_menu_categorii = getXMLPopUpMenuCategorii(categorieService);
    			modelMap.put(POP_UP_MENU_CATEGORII, pop_up_menu_categorii);
    			session.setAttribute(POP_UP_MENU_CATEGORII, pop_up_menu_categorii);
    		}        	
        	
    		modelMap.put(LISTA_CATEGORII_CAUTARE, categorieService.getCategorieByNivel(1));
        	perform(request, response, session, modelMap);
        } catch (Exception e){
        	modelMap.put(ERROR, SYSTEM_ERROR);
        	e.printStackTrace();
        }
        
        return new ModelAndView(view, "model", modelMap);
	}
	
	//metoda care realizeaza practic business-logic, se va suprascrie
	public abstract void perform(HttpServletRequest request, HttpServletResponse response, HttpSession session, Map<String, Object> modelMap);
	
	public String getView() {
		return view;
	}
	
	public void setView(String view){
		this.view = view;
	}
	
    public abstract void setView();
	
	public List<Categorie> getXMLPopUpMenuCategorii(CategorieServiceI categorieService){
		List<Categorie> XMLPopupMenuCategorii = new ArrayList<Categorie>();
		
		List<Categorie> EncodedArbore_categorie = categorieService.getEncodedArbore_categorie();
		
		Categorie inc_li = new Categorie();
		inc_li.setNivel(0); inc_li.setNume("<li>");
		
		Categorie sf_li = new Categorie();
		sf_li.setNivel(0); sf_li.setNume("</li>");
		
		Categorie inc_ul = new Categorie();
		inc_ul.setNivel(0); inc_ul.setNume("<ul>");
		
		Categorie sf_ul = new Categorie();
		sf_ul.setNivel(0); sf_ul.setNume("</ul>");
		
		Categorie element_prec = new Categorie();
		element_prec.setNivel(0);
		element_prec.setNume("dummy");		
		
		for (Categorie element_act : EncodedArbore_categorie)
		{
			if (element_act.getNivel() > 0){
				if (element_prec.getNivel() == 0){
					XMLPopupMenuCategorii.add(inc_li);
					XMLPopupMenuCategorii.add(element_act);
					element_prec = element_act;
				} else {
					XMLPopupMenuCategorii.add(inc_ul);
					XMLPopupMenuCategorii.add(inc_li);
					XMLPopupMenuCategorii.add(element_act);
					element_prec = element_act;
				}
			} else{
				if  (element_prec.getNivel() > 0){
					XMLPopupMenuCategorii.add(sf_li);
					element_prec = element_act;
				} else {
					XMLPopupMenuCategorii.add(sf_ul);
					XMLPopupMenuCategorii.add(sf_li);
					element_prec = element_act;
				}
			}
		}
		return XMLPopupMenuCategorii;
	}	
	
	public Integer getInteger(String numar){
		int nr = 0;
		
		try {
			nr = new Integer(numar);
		} catch (Exception e){
			log.error(SYSTEM_ERROR + e.getMessage(),e);
			return null;
		}
		
		return nr;
	}
	
	protected void redirect(HttpServletRequest request, HttpServletResponse response, String url){
		RequestDispatcher rd = request.getRequestDispatcher(url);
		
		try {
			rd.forward(request, response);
		} catch (Exception e){
			log.debug("Can't redirect to " + url + ": " + e.getMessage());			
		}
	}
	
}
