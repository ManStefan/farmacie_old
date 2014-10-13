<%@ include file="/WEB-INF/jsp/include.jsp" %>

<div class="templatemo_post" >
	<c:if test="${not empty model.filteredProductsList}">
		<jsp:include page="sort.jsp"></jsp:include>
		<jsp:include page="search_text.jsp"></jsp:include>
		
	    <form name="viewProdusForm" action="paginaProdus.htm" method="get">
	    	<input type="hidden" name="id_produs_selectat" />
	    </form>
	    
		<table width="100%" height="100%" cellpadding="0" cellspacing="25" style="padding-top: 80px;">
			<c:if test="${!empty model.filteredProductsList}">
				<c:forEach items="${model.filteredProductsList}" var="produs">
					<tr style="padding-top: 0px;">
						<td style="width: 1%;">
							<a href="#">
								<img onclick="setViewProdusFormFieldValue('id_produs_selectat','${produs.id}');viewProdusFormSubmit('');" alt="Fara Imagine" src="<c:if test="${!empty produs.numePoze}">  <c:out value="incarcaImagineServlet.htm?imageName=${produs.numePoze[0]}"/> </c:if>
															 <c:if test="${empty produs.numePoze}">  <c:out value="incarcaImagineServlet.htm?imageName=fara_imagine.bmp"/> </c:if>" width="170" height="170" />					
							</a>
						</td>
						<td style="width: 70%;">
							<table width="100%" height="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td style="vertical-align: top;" onclick="setViewProdusFormFieldValue('id_produs_selectat','${produs.id}');viewProdusFormSubmit('');"> <a href="#"> <c:out value="${produs.nume} ${produs.numeProducatori[0]}, ${produs.descriere}"/> </a></td>
								</tr>
								<tr>
									<td style="vertical-align: top;">
										<table>
									    	<c:forEach items="${produs.numeAtribute}" var="entry">
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
								    	</table>									
									</td>
								</tr>
							</table>
						</td>
						<td style="width: 30%;">
							<table width="100%" height="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td class="prod_price_style" style="vertical-align: top; padding-top: 10px; height: 1px;">
										${produs.pret} <spring:message code="farmacie.produse.lei"/>			
									</td>
								</tr>
								<tr>
									<td style="vertical-align: top; color: #009900;">
										<b>
										<spring:message code="farmacie.produs.stoc"/>: <c:if test="${produs.cantitate > 0}"> <spring:message code="farmacie.produs.stoc.da"/></c:if>
												 									   <c:if test="${produs.cantitate == 0}"> <spring:message code="farmacie.produs.stoc.nu"/></c:if>
										</b>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		
		
		</table>
	</c:if>
	<c:if test="${empty model.filteredProductsList}">
		<spring:message code="farmacie.produse.gol"/>	
	</c:if>
	
</div>