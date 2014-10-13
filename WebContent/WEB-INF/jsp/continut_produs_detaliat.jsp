<%@ include file="/WEB-INF/jsp/include.jsp" %>

<script type="text/javascript" src="js/jquery-1.6.2.js"></script>
<script type="text/javascript" src="js/fadeslideshow.js"></script>

<script type='text/javascript' src='js/jquery.mobile.customized.min.js'></script>
<script type='text/javascript' src='js/jquery.easing.1.3.js'></script> 
<script type='text/javascript' src='js/camera.min.js'></script>
   
<link rel='stylesheet' id='camera-css'  href='css/camera.css' type='text/css' media='all'>      
    <style>
		.fluid_container {
			margin: 0 auto;
			max-width: 1000px;
			width: 90%;
		}
	</style>

 <script>
jQuery(function(){
	
	jQuery('#camera_wrap_1').camera({
		loader: 'bar',
		pagination: true,
		thumbnails: false
	});
});
</script>


   
<!-- script type="text/javascript">

var slideArr = new Array();

<c:forEach items="${model.view_produs_selectat.numePoze}" varStatus="loop">
	var pictureArr = new Array();
	pictureArr.push("<c:out value="incarcaImagineServlet.htm?imageName=${model.view_produs_selectat.numePoze[loop.index]}" />");
	pictureArr.push("");
	pictureArr.push("");
	pictureArr.push("<c:out value="${model.view_produs_selectat.descrierePoze[loop.index]}" />");
	slideArr.push(pictureArr);
</c:forEach>

var mygallery2=new fadeSlideShow({
	wrapperid: "fadeshow2", //ID of blank DIV on page to house Slideshow
	dimensions: [350, 350], //width/height of gallery in pixels. Should reflect dimensions of largest image
	imagearray: slideArr,
	displaymode: {type:'manual', pause:2500, cycles:0, wraparound:false},
	persist: false, //remember last viewed slide and recall within same session?
	fadeduration: 500, //transition duration (milliseconds)
	descreveal: "always",
	togglerid: "fadeshow2toggler"
})
</script-->


<div class="templatemo_post" >
    <div class="post_title">
    	${model.view_produs_selectat.nume}
    </div>  
    
    <div class="post_body">
	    <table>
	    	<tr>
	    		<td></td>
	    		<td>
	    			<table style="width: 100%;">
	    				<tr>
	    					<td style="width: 70%">
								<div class="fluid_container">
								<div class="camera_wrap camera_azure_skin" id="camera_wrap_1">
									<c:forEach items="${model.view_produs_selectat.numePoze}" var="numePoze" varStatus="loop">
									    <div data-thumb="incarcaImagineServlet.htm?imageName=${numePoze}" data-src="incarcaImagineServlet.htm?imageName=${numePoze}">
									        <div class="camera_caption fadeFromBottom">
									            ${model.view_produs_selectat.descrierePoze[loop.index]}
									        </div>
									    </div>	
									</c:forEach>
								</div>			
								</div>					
							</td>
							<td class="prod_price_style_big">
								<table>
									<tr>
										<td>
											${model.view_produs_selectat.pret} <spring:message code="farmacie.produs.lei"/>
										</td>
									</tr>
									<tr>
										<td style="vertical-align: top; color: #009900; font-size: 17px;">
											<b>
											<spring:message code="farmacie.produs.stoc"/>: <c:if test="${model.view_produs_selectat.cantitate > 0}"> <spring:message code="farmacie.produs.stoc.da"/></c:if>
													 									  <c:if test="${model.view_produs_selectat.cantitate == 0}"> <spring:message code="farmacie.produs.stoc.nu"/></c:if>
											</b>
										</td>																
									</tr>
								</table>			
							</td>
						</tr> 
					</table>
	    		</td>
	    	</tr>
	    
	    <!-- Descriere produs selectat -->
	    	<c:if test="${model.view_produs_selectat.descriere != null}">
		    	<tr style="margin-top: 15px">
		    		<td>
		    			<div  class="cat_name_style">></div>
		    		</td>	    	
		    		<td>
		    			<div  class="cat_name_style"><spring:message code="farmacie.produs.descriere"/>: </div>
		    		</td>
		    	</tr>
		    	<tr valign="top">
		    		<td></td>
		    		<td class="prod_name_style">
		    			${model.view_produs_selectat.descriere}
		    		</td>
		    	</tr>
	    	</c:if>
	    	
	    	<!-- Numele Producatorului produsului selectat -->
	    	<c:if test="${model.view_produs_selectat.numeProducatori != null}">
		    	<tr style="margin-top: 15px">
		    		<td>
		    			<div  class="cat_name_style">></div>
		    		</td>	    	
		    		<td>
		    			<div  class="cat_name_style"><spring:message code="farmacie.produs.producator"/>: </div>
		    		</td>
		    	</tr>
		    	<c:forEach var="producator" items="${model.view_produs_selectat.numeProducatori}">
			    	<tr valign="top" >
			    		<td></td>
			    		<td class="prod_name_style">
			    			${producator}
			    		</td>
			    	</tr>
		    	</c:forEach>
	    	</c:if>	    	
	    	
	    	<!-- Atribute ale produslui selectat -->
	    	<c:if test="${model.view_produs_selectat.numeAtribute != null}">
		    	<tr style="margin-top: 15px">
		    		<td>
		    			<div  class="cat_name_style">></div>
		    		</td>	    	
		    		<td>
		    			<div  class="cat_name_style"><spring:message code="farmacie.produs.specificatii"/>: </div>
		    		</td>
		    	</tr>
		    	<c:forEach items="${model.view_produs_selectat.numeAtribute}" var="entry">
		    	<tr>
		    		<td></td>
		    		<td>
		    			<table cellpadding="0" cellspacing="0">
		    				<tr>
		    					<td class="prod_name_style">${entry.key}</td>
		    				</tr>
		    				<c:forEach items="${entry.value}" var="value">
		    					<tr>
		    						<td class="prod_name_style"><div style="margin-left: 20px">${value}</div></td>
		    					</tr>
		    				
		    				</c:forEach>
		    			</table>
		    		</td>
		    	</tr>
		    		
		    	</c:forEach>
		    </c:if>	    					    		
	    </table>
    </div>      
</div>