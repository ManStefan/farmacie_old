<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:if test="${not empty model.add_category_error}">
	<c:out value="${model.add_category_error}"/> 
</c:if>

<c:if test="${empty model.add_category_error}">
	<c:out value="OK||${model.nrOfDropDowns}||${model.added_brother_category.id}||${model.added_brother_category.nume}||${model.added_brother_category.nivel}||${model.added_brother_category.frunza}" />
</c:if>