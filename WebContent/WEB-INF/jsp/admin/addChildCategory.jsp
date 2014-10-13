<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:if test="${not empty model.add_category_error}">
	<c:out value="${model.add_category_error}"/> 
</c:if>

<c:if test="${empty model.add_category_error}">
	<c:out value="OK" />
</c:if>