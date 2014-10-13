<%@ include file="/WEB-INF/jsp/include.jsp" %>

<script type="text/javascript">

	var nrOfCurrentAttribute = 0;
	var nrOfAttribute = 0;
	var nrOfCurrentImage = 0;

	function valideazaProdus(){
		var errorsMsg = "";
		
		var priceRege = /^\+?[0-9]*\.?[0-9]+$/;
		var cantitateRege = /^\+?[0-9]+$/;

		var numeProdus = jQuery.trim(jQuery('#newProdusName').val());
		var pretProdus = jQuery.trim(jQuery('#newProdusPret').val());
		var cantitateProdus = jQuery.trim(jQuery('#newProdusCantitate').val());
		var categorieProdus = jQuery.trim(jQuery('#selectedCategorieId').val());
		var producatorProdus = jQuery.trim(jQuery('#selectedProducatorId').val());
		var etalonProdus = jQuery.trim(jQuery('#selectedEtaloaneId').val());

		if (numeProdus == ""){
			errorsMsg += "Numele produsului nu trebuie sa fie gol! \n";
		}
		
		if (!priceRege.test(pretProdus)){
			errorsMsg += "Pretul trebuie sa fie un numar pozitiv! \n";
		}	
		
		if (!cantitateRege.test(cantitateProdus)){
			errorsMsg += "Cantitatea trebuie sa fie un numar intreg pozitiv! \n";
		}	

		if (categorieProdus == ""){
			errorsMsg += "Trebuie sa selectati o categorie in care sa se afle produsul adaugat! \n";
		}

		if (producatorProdus == ""){
			errorsMsg += "Trebuie sa selectati un producator pentru produsul adaugat! \n"
		}

		if (etalonProdus == ""){
			errorsMsg += "Trebuie sa selectati un etalon pentru produsul adaugat! \n";
		}

		if (errorsMsg != ""){
			alert(errorsMsg);
			return;
		}

		var attributes = "";
		for (i=1; i <= nrOfAttribute; i++){
			categorie = jQuery('#selectedAttrCat' + i).val();
			atribut = jQuery('#selectedAttr' + i).val();	
			if (categorie != "" && atribut != "" && categorie && atribut){
				attributes += categorie + ";" + atribut;
				if (i < nrOfAttribute){
					attributes += "||";
				}
			}
		}
		
		jQuery.ajax({
            url: "valideazaProdus.htm",
        	dataType: "html",
        	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
        	data: {
				numeProdus : numeProdus,
				pretProdus : pretProdus, 
				cantitateProdus : cantitateProdus, 
				categorieProdus : categorieProdus, 	
				producatorProdus : producatorProdus,
				etalonProdus : etalonProdus,
				atribute : attributes
    		},
			success : function( data ) { 
    						var response = jQuery.trim(data);
							if (response == "OK"){
								jQuery('#newProductForm').submit();
							} else {
								alert(response);
							}
    					}
		});
		
		
	}
	
	function adaugaAtribut(){
		jQuery.ajax({
            url: "atributeForProduct.htm",
        	dataType: "html",
        	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
        	data: {
        		
    		},
			success : function( data ) { 
    						jQuery('#attributes_div').append(data); 
    						nrOfAttribute++;
    					}
		});
	}

	function adaugaImagine(){
		jQuery.ajax({
            url: "imageForProduct.htm",
        	dataType: "html",
        	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
        	data: {
        		
    		},
			success : function( data ) { 
    						jQuery('#images_div').append(data); 
    					}
		});
	}	

	jQuery(function(){
		$( "#producator" ).autocomplete({
			source: "searchProducatori.htm",
			minLength: 1,
			delay : 500,
			select: function( event, ui ) {
				if (ui.item){
					jQuery('#selectedProducatorId').val(ui.item.id); 
				}
			}
		});
	});

	jQuery(function(){
		$( "#etaloane1" ).autocomplete({
			source: "searchEtaloane.htm",
			minLength: 1,
			delay : 500,
			select: function( event, ui ) {
				if (ui.item){
					jQuery('#selectedEtaloaneId').val(ui.item.id); 
				}
			}
		});
	});
	
	jQuery(function(){
		$( "#categorie" ).autocomplete({
			source: function(request, response) {
				$.ajax({

	                url: "searchCategorie.htm",
                	dataType: "json",
                	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                	data: {
                			term : request.term,
                			nivel : "*",
                			frunza : 1
            		},
            		success: function( data ) {
                        response( $.map( data, function( item ) {
                        							return {
									                        label: item.label,
									                        value: item.value,
									                        id: item.id
									                        }
								}));
            		}
				});
			},
			delay : 500,
			minLength: 1,
			select: function( event, ui ) {
				if (ui.item)
					jQuery('#selectedCategorieId').val(ui.item.id); 
			}
		});
	});	


</script>

<form method="post" action="adaugaProdusNou.htm" enctype="multipart/form-data" id="newProductForm">
	<table>
		<tr>
			<td>
				<table>
					<tr>
						<td style="width: 10px;">
							Numele: 
						</td>
						<td><input type="text" name="newProdusName" id="newProdusName"  size="25"/></td>	
					</tr>
					<tr>
						<td style="width: 10px;">
							Descriere: 
						</td>
						<td><textarea cols="75" rows="6" name="newProdusDescriere" id="newProdusDescriere"></textarea></td>	
					</tr>					
					<tr>
						<td>
							Pretul: 
						</td>
						<td><input type="text" name="newProdusPret" id="newProdusPret"  size="25"/></td>
					</tr>
					<tr>
						<td>
							Cantitate: 
						</td>
						<td><input type="text" name="newProdusCantitate" id="newProdusCantitate"  size="5"/></td>
					</tr>				
					<tr>
						<td><label for="categorie">Categoria: </label></td>
						<td style="padding-top: 10px;">
							<div class="ui-autocomplete-farmacieAdmin">
								<input id="categorie" onkeydown="jQuery('#selectedCategorieId').val('');" style="background-color: #E0E0E0;">
							</div>
							<input type="hidden" name="selectedCategorieId" id ="selectedCategorieId" />		
						</td>
					</tr>	
					<tr>
						<td><label for="producator">Producatorul: </label></td>
						<td style="padding-top: 10px;">
							<div class="ui-autocomplete-farmacieAdmin">
								<input id="producator" onkeydown="jQuery('#selectedProducatorId').val('');" style="background-color: #E0E0E0;">
							</div>
							<input type="hidden" name="selectedProducatorId" id ="selectedProducatorId" />		
						</td>	
					</tr>
					<tr>
						<td><label for="etaloane1">Etalonul: </label></td>
						<td style="padding-top: 10px;">
							<div class="ui-autocomplete-farmacieAdmin">
								<input id="etaloane1" onkeydown="jQuery('#selectedEtaloaneId').val('');" style="background-color: #E0E0E0;">
							</div>
							<input type="hidden" name="selectedEtaloaneId" id ="selectedEtaloaneId" />		
						</td>	
					</tr>					
					<tr>
						<td colspan="2">
							<div id = "attributes_div">
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="button" value="Adaugati un nou atribut!" onclick="adaugaAtribut()"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div id = "images_div">
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="button" value="Adaugati o noua imagine!" onclick="adaugaImagine()"/>
						</td>
					</tr>								
													
				</table>
			</td>
		</tr>
	</table>
</form>
	<table>
		<tr>
			<td>
				Adaugati un produs nou:  <input type="submit" value="Adauga!" onmousedown="valideazaProdus()">
			</td>
		</tr>
	</table>	