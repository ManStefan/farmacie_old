<%@ include file="/WEB-INF/jsp/include.jsp" %>

<div class="templatemo_post" >
    <div class="post_title">
    	<spring:message code="farmacie.produse.celeMaiNoiProduse"/>
    </div>
    
    <form name="viewProdusForm" action="paginaProdus.htm" method="get">
    	<input type="hidden" name="id_produs_selectat" />
    </form>
    
    <div class="post_body">
	    <table cellpadding="20">
	    	<c:set var="numar_produse_pe_linie" scope="session" value="0"/>
	    
	    	<c:forEach var="entry" items="${model.cele_mai_noi_produse_pe_categorii}">
	    		<tr>
	    			<td colspan="${model.numar_de_prod_pe_linie}">
	    				<div  class="cat_name_style"><spring:message code="farmacie.produse.categoria"/> <c:out value="${entry.key}" /> </div>
	    			</td>
	    		</tr>
	    	
				<c:forEach var="produs" items="${entry.value}">    	
		    		<c:set var="numar_produse_pe_linie" scope="session" value="${numar_produse_pe_linie+1}"/>
	
					<c:if test="${numar_produse_pe_linie == 1}">
						<tr>
					</c:if>
						<td valign="top" onclick="setViewProdusFormFieldValue('id_produs_selectat','${produs.id}');viewProdusFormSubmit('');">
							<a href="#">
 	  						<img alt="Fara Imagine" src="<c:if test="${!empty produs.numePoze}">  <c:out value="incarcaImagineServlet.htm?imageName=${produs.numePoze[0]}"/> </c:if>"
 	  													 <c:if test="${empty produs.numePoze}">  <c:out value="incarcaImagineServlet.htm?imageName=fara_imagine.bmp"/> </c:if>" width="140" height="140" />
							<div class="prod_name_style"><c:out value="${produs.nume}"/></div>
							<div class="atribute_produse_style">
									<c:out value="${produs.descriere}" /> 
							</div>
							<div class="prod_price_style"><c:out value="${produs.pret}"/> <spring:message code="farmacie.produse.lei"/></div>
							</a>
						</td>
					<c:if test="${numar_produse_pe_linie == model.numar_de_prod_pe_linie}">
						</tr>
						<c:set var="numar_produse_pe_linie" scope="session" value="0"/>
					</c:if>
				</c:forEach>
						
				<c:if test="${numar_produse_pe_linie < model.numar_de_prod_pe_linie}">
					</tr>
					<c:set var="numar_produse_pe_linie" scope="session" value="0"/>
				</c:if>
				    		
	    	</c:forEach>
	    </table>
    </div>
</div>