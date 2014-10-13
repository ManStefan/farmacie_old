<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:if test="${not empty model.filteredProductsList}">
	<div class="templatemo_right_section">
	
		<form name="filterForm" id="filterForm" method="get" action="produse.htm">
			<input type="hidden" name="action" value="filtrare" />
			<input type="hidden" name="pageNr" value="${model.pageNr}" />
			<input type="hidden" name="searchfield" value="${model.searchfield}" />
			<input type="hidden" name="sortField" value="${model.sortField}" />
	
		<table class="filter_style" cellpadding="0" cellspacing="0" style="padding-top: 27px;">
			<tr>
				<td><input type="radio" name="inStock" value="1" <c:if test="${model.showInStock == 1}">checked</c:if> onclick="document.filterForm.submit();"></td><td><spring:message code="farmacie.filter.afisatiProduseleInStoc"/></td>			
			</tr>
	
			<tr>
				<td><input type="radio" name="inStock" value="0" <c:if test="${model.showInStock == 0}">checked</c:if> onclick="document.filterForm.submit();"></td><td><spring:message code="farmacie.filter.afisatiToateProdusele"/></td>			
			</tr>
		
			<tr>
				<td></td><td style="padding-top: 10px;"><spring:message code="farmacie.filter.subcategorii"/></td>
			</tr>
			
			<c:forEach items="${model.categoriiMap}" var="entry">
				<tr>
					<td><input type="checkbox" name="id_categorie" value="${entry.categorie.id}" <c:if test="${entry.selected}">checked</c:if> onclick="document.filterForm.submit();"></td><td>${entry.categorie.nume}</td>
				</tr>
			</c:forEach>
		
		
		
			<c:forEach items="${model.atributeMap}" var="entry">		
				<tr>
					<td></td><td style="padding-top: 10px;"><b>${entry.key.nume}:</b></td>
				</tr>
				<c:forEach items="${entry.value}" var="value">
					<tr>
						<td><input type="checkbox" name="selectedAttrs" value="${entry.key.id}|${value.atribut.id}" <c:if test="${value.selected}">checked</c:if> onclick="document.filterForm.submit();"></td><td>${value.atribut.nume}</td>
					</tr>
				</c:forEach>
			</c:forEach>
	
			<c:if test="${not empty model.producatoriMap}">
				<tr>
					<td></td><td style="padding-top: 10px;"><b><spring:message code="farmacie.filter.producatori"/>:</b></td>
				</tr>
				<c:forEach items="${model.producatoriMap}" var="entry">
					<tr>
						<td><input type="checkbox" name="selectedProducers" value="${entry.producator.id}" <c:if test="${entry.selected}">checked</c:if> onclick="document.filterForm.submit();"></td><td>${entry.producator.nume}</td>
					</tr>
				</c:forEach>	
			</c:if>
			
			<!--  Am eliminat filtrul pentru etaloane, trebuie eliminat si din cod, dar poate am ce sa fac cu el???
			<c:if test="${not empty model.etaloaneMap}">
				<tr>
					<td></td><td style="padding-top: 10px;"><b><spring:message code="farmacie.filter.etaloane"/>:</b></td>
				</tr>
				<c:forEach items="${model.etaloaneMap}" var="entry">
					<tr>
						<td><input type="checkbox" name="selectedEtalon" value="${entry.etalon.id}" <c:if test="${entry.selected}">checked</c:if> onclick="document.filterForm.submit();"></td><td>${entry.etalon.nume}</td>
					</tr>
				</c:forEach>
			</c:if>
			-->	
		</table>
		</form>
	</div>
</c:if>	
