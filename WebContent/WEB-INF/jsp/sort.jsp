<%@ include file="/WEB-INF/jsp/include.jsp" %>

<div id="sort_div">
	<spring:message code="farmacie.sort.ordoneaza"/>
	
	<select id="sortSelectField" name="sortSelectField" onchange="document.filterForm.sortField.value=this.options[this.selectedIndex].value; document.filterForm.submit();">
		<c:choose>
		    <c:when test="${model.sortField eq 'price_desc'}">
		    	<option value ="noSort">----</option>
				<option value ="price_asc"><spring:message code="farmacie.sort.pretCrescator"/></option>
				<option value ="price_desc" selected="selected"><spring:message code="farmacie.sort.pretDescrescator"/></option>
			</c:when>
			<c:when test="${model.sortField eq 'price_asc'}">
				<option value ="noSort">----</option>
				<option value ="price_asc" selected="selected"><spring:message code="farmacie.sort.pretCrescator"/></option>
				<option value ="price_desc"><spring:message code="farmacie.sort.pretDescrescator"/></option>
			</c:when>
			<c:otherwise>
				<option value ="noSort" selected="selected">----</option>
				<option value ="price_asc"><spring:message code="farmacie.sort.pretCrescator"/></option>
				<option value ="price_desc"><spring:message code="farmacie.sort.pretDescrescator"/></option>		
			</c:otherwise>
		</c:choose>
	</select>
</div>