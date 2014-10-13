<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:if test="${not empty model.add_category_error}">
	<c:out value="${model.add_category_error}"/> 
</c:if>

<c:if test="${empty model.add_category_error}">
	<c:out value="OK||${model.nrOfDropDowns}||${model.old_name_category}||${model.modified_category.id}||${model.modified_category.nume}||${model.modified_category.nivel}||${model.modified_category.frunza}" />
</c:if>