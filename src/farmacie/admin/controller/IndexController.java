package farmacie.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/**/admin/index.htm")
public class IndexController {

	@RequestMapping
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		return new ModelAndView("index", "model", modelMap);
	}	
}
