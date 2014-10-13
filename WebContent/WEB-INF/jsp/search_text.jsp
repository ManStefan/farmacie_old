<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:if test="${not empty model.searchfield}">
	<div id="sort_text_div">
		<spring:message code="farmacie.search.text.result"/> <b>"${model.searchfield}"</b>
	</div>
</c:if>