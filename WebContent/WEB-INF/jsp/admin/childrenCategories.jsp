<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:if test="${!empty model.dropdown_cat_list}">
	<div id="dropDownDiv${model.nrOfDropDowns}">
		<table>
			<tr>
				<td>
					<table>
						<c:if test="${model.nrOfDropDowns > 1}">
							<tr>
								<td>
									<img id="imgUp${model.nrOfDropDowns}" src="/farmacie/images/up_arrow.jpg" style="width: 30px; cursor: pointer;" onclick="exploreBack(${model.nrOfDropDowns})"/>
								</td>
							</tr>
						</c:if>
						<tr>
							<td>
								<img id="imgDown${model.nrOfDropDowns}" src="/farmacie/images/down_arrow.jpg" style="width: 30px; cursor: pointer;" onclick="getChildrenCategories(getValueOfSelectedItem('selCat${model.nrOfDropDowns}'))" />
							</td>
						</tr>
					</table>
				</td>
				<td>	
					<select id="selCat${model.nrOfDropDowns}" onchange="cancelDeleteCat(${model.nrOfDropDowns})" >
						<c:forEach var="it" items="${model.dropdown_cat_list}">
						    <option value="${it.id}||${it.nume}||${it.nivel}||${it.frunza}"> <c:out escapeXml="true" value="${it.nume}" /> </option>
						</c:forEach>
					</select>
				</td>
				<td>
					<div id="addChildCatDiv${model.nrOfDropDowns}">
						<table>
							<tr>
								<td>
									<input type="submit" value="Adauga categorie copil!" onclick="addChildCategory(document.getElementById('newChildName${model.nrOfDropDowns}').value, getValueOfSelectedItem('selCat${model.nrOfDropDowns}'))"/>
								</td>
							</tr>	
							<tr>	
								<td>
									<input type="text" name="newChildName${model.nrOfDropDowns}" id="newChildName${model.nrOfDropDowns}" size="30" />
								</td>
							</tr>
						</table>
					</div>
				</td>					
				<td>
					<div id="addBrotherCatDiv${model.nrOfDropDowns}">
						<table>
							<tr>
								<td>
									<input type="submit" value="Adauga categorie frate!" onclick="addBrotherCategory(document.getElementById('newBrotherName${model.nrOfDropDowns}').value, getValueOfSelectedItem('selCat${model.nrOfDropDowns}'))"/>
								</td>
							</tr>	
							<tr>	
								<td>
									<input type="text" size="30" name="newBrotherName${model.nrOfDropDowns}" id="newBrotherName${model.nrOfDropDowns}" />
								</td>
							</tr>
						</table>
					</div>
				</td>	
				<td>
					<div id="updateNameCat${model.nrOfDropDowns}">
						<table>
							<tr>
								<td>
									<input type="submit" value="Modifica numele!" onclick="updateNameCat(document.getElementById('newCatName${model.nrOfDropDowns}').value, getValueOfSelectedItem('selCat${model.nrOfDropDowns}'))"/>
								</td>
							</tr>	
							<tr>	
								<td>
									<input type="text" size="30" name="newCatName${model.nrOfDropDowns}" id="newCatName${model.nrOfDropDowns}" />
								</td>
							</tr>
						</table>
					</div>
				</td>	
				<td>
					<div id="deleteCat${model.nrOfDropDowns}">
						<table>
							<tr>
								<td>
									<input type="submit" id="deleteCatButton${model.nrOfDropDowns}" value="Sterge categoria!" onclick="deleteCat(getValueOfSelectedItem('selCat${model.nrOfDropDowns}'), ${model.nrOfDropDowns})"/>
								</td>
							</tr>
							<tr>
								<td>
									<div id="deleteCatOptions${model.nrOfDropDowns}" style="visibility: hidden;">
										<table>
											<tr>
												<td>
													<input type="checkbox" name="deleteProdCat${model.nrOfDropDowns}" id="deleteProdCat${model.nrOfDropDowns}" value="deleteProdCat">Exista produse si/sau categorii atasate!
												</td>
											</tr>
											<tr>
												<td><input type="submit" value="Finalizeaza !" onclick="finishDeleteCat(getValueOfSelectedItem('selCat${model.nrOfDropDowns}'), isCheckBoxChecked('deleteProdCat${model.nrOfDropDowns}'), ${model.nrOfDropDowns})"/>
													<input type="submit" value="Anuleaza !" onclick="cancelDeleteCat(${model.nrOfDropDowns})"/>												
												</td>
											</tr>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
					
				</td>								
			</tr>
		</table>		
	</div>	
</c:if>	