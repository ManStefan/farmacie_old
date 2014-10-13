package farmacie.admin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import farmacie.admin.controller.statics.AdminCategoriiControllerStatics;
import farmacie.statics.FarmacieConstants;

@Controller
public class AdminControllersUtils implements FarmacieConstants, AdminCategoriiControllerStatics{

	private Logger log = Logger.getLogger(this.getClass());	
	
    @Autowired
    @Qualifier("applicationProperties")
    protected Properties applicationProperties;		
	
	public static void addAdminErrorsToModelMap(List<String> errors, Map<String, Object> modelMap){
		List<String> translatedErrors = new ArrayList<String>();
		
		if (errors.contains(DUPLICATE_ENTRY_ERROR))
			translatedErrors.add(DUPLICATE_CATEGORIES_ERROR);
		if (errors.contains(SYSTEM_ERROR))
			translatedErrors.add(ADD_CATEGORIES_ERROR);
		
		modelMap.put(ADD_CATEGORIES_LABEL_ERROR, translatedErrors);
	}
	
	@RequestMapping("/**/incarcaImagineServlet.htm")
	public void incarcaImagine(HttpServletRequest request, HttpServletResponse response, HttpSession session){
	    //String filename = URLDecoder.decode(request.getPathInfo(), "UTF-8");
		
		String filename = request.getParameter("imageName");
	    File file = new File(applicationProperties.getProperty("cale_stocare_imagini_prod"), filename);

	    response.setContentType(/*fileNameMap.getContentTypeFor(filename)*/"image/jpg");
	    response.setContentLength((int)file.length());
	    response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

	    BufferedInputStream input = null;
	    BufferedOutputStream output = null;
	    
	    try {
	        input = new BufferedInputStream(new FileInputStream(file));
	        output = new BufferedOutputStream(response.getOutputStream());

	        byte[] buffer = new byte[8192];
	        int length;
	        while ((length = input.read(buffer)) > 0) {
	            output.write(buffer, 0, length);
	        }
	    } catch (IOException e){
	        log.error("Eroare la incarcare imagine din BD:" + getStackTrace(e));
	    } finally {
	        if (output != null) try { output.close(); } catch (IOException ignore) {}
	        if (input != null) try { input.close(); } catch (IOException ignore) {}
	    }
		
	}
	
    public static String getStackTrace(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }	
	
}
