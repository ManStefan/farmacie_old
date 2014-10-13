<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:if test="${not empty model.filteredProductsList}">
	<div class="templatemo_post_page_nr" >
	
		<table width="100%" style="text-align: right;" cellpadding="5px;">
			<tr>
				<td style="width: 100%;"></td>
				<c:choose>
					<c:when test="${model.isPrevPagesChunk == 1}">
						<td><img src="images/arrow-first.gif" style="cursor: pointer;" onclick="document.filterForm.action.value='paginare_first'; document.filterForm.submit();"/></td>
						<td><img src="images/arrow-previous.gif" style="cursor: pointer;" onclick="document.filterForm.action.value='paginare_previous'; document.filterForm.submit();"/></td>
					</c:when>
					<c:otherwise>
						<td><img src="images/arrow-first-dis.gif" /></td>
						<td><img src="images/arrow-previous-dis.gif"/></td>				
					</c:otherwise>
				</c:choose>
				<c:forEach items="${model.pagesList}" var="it">
					<td>
						<c:if test="${model.pageNr == it}">
							<a href="#" style="decoration: none; text-align: right; color: 	#B45F04;" onclick="document.filterForm.action.value='paginare'; document.filterForm.pageNr.value='${it}'; document.filterForm.submit();">${it}</a>
						</c:if>
						
						<c:if test="${model.pageNr != it}">
							<a href="#" style="decoration: none; text-align: right;" onclick="document.filterForm.action.value='paginare'; document.filterForm.pageNr.value='${it}'; document.filterForm.submit();">${it}</a>
						</c:if>
					</td>
				</c:forEach>
				<c:choose>
					<c:when test="${model.isNextPagesChunk == 1}">
						<td><img src="images/arrow-next.gif" style="cursor: pointer;" onclick="document.filterForm.action.value='paginare_next'; document.filterForm.submit();"/></td>
						<td><img src="images/arrow-last.gif" style="cursor: pointer;" onclick="document.filterForm.action.value='paginare_last'; document.filterForm.submit();"/></td>
					</c:when>
					<c:otherwise>
						<td><img src="images/arrow-next-dis.gif"/></td>
						<td><img src="images/arrow-last-dis.gif" /></td>				
					</c:otherwise>
				</c:choose>			
				
			</tr>
		</table>
	
	</div>
</c:if>