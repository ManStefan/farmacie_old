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
@RequestMapping("/**/contact.htm")
public class ContactController extends FarmacieControl{

	@Override
	public void perform(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			Map<String, Object> modelMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setView() {
		setView("contact");
	}
	
}