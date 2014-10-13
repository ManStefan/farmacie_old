<%@ include file="/WEB-INF/jsp/include.jsp" %>

<script type="text/javascript">
	function submitSearchForm(){
		if ($.trim($("#searchfield").val()) != ""){
			$("#searchForm").submit();
		}
	}
</script>

<div id="templatemo_search_section">
    <form method="get" action="produse.htm" id="searchForm">
        <select id="searchSelectCategory" name="id_categorie">
        	<option value ="0">Toate categoriile</option>
			<c:forEach var="categorie" items="${model.lista_categorii_cautare}">
				<option value ="${categorie.id}"><c:out value="${categorie.nume}"/></option>
			</c:forEach>
        </select>
        <input type="text" name="searchfield" size="10" id="searchfield" title="searchfield" value="${model.searchfield}" />
        <a class="button-link-search" href="#" style="color: black;" onclick="submitSearchForm();"><spring:message code="farmacie.search.text.button"/></a>
    </form>
</div>