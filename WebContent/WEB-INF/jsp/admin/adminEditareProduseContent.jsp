<%@ include file="/WEB-INF/jsp/include.jsp" %>

<script type="text/javascript">

var nrOfCurrentAttribute = 0;
var nrOfAttribute = 0;
var nrOfCurrentImage = 0;

jQuery(function(){
	$( "#categorie1" ).autocomplete({
		source: function(request, response) {
			jQuery.ajax({

                url: "searchCategorie.htm",
            	dataType: "json",
            	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
            	data: {
            			term : request.term,
            			nivel : "*",
            			frunza : -1
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
				jQuery('#selectedCategorieId1').val(ui.item.id); 
				jQuery('#din_categoria').html("din categoria <b>" + ui.item.label + "</b>");
		}
	});
});	

jQuery(function(){
	$( "#produse1" ).autocomplete({
		source: function(request, response) {
			$.ajax({

                url: "searchProduseDupaNumeCategorie.htm",
            	dataType: "json",
            	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
            	data: {
            			term : request.term,
            			id_categorie : jQuery('#selectedCategorieId1').val() 
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
				jQuery('#selectedProduseId1').val(ui.item.id);

				//aduc detaliille despre produs din baza de date
				jQuery.ajax({
	                url: "obtineDetaliiProdusSelectat.htm",
	            	dataType: "text",
	            	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
	            	data: {
	            			id_produs_selectat : ui.item.id
	        		},
	        		success: function( data ) {
		        		jQuery('#continut_produs_selectat').css('visibility', 'visible');
		        		jQuery('#actiuni_produs_selectat').css('visibility', 'visible');
		        		completeazaCuDatelePrimite(jQuery.trim(data));
	        		}
				});			
		}
	});
});

function completeazaCuDatelePrimite(data){
	var produs = jQuery.parseJSON(data);

	jQuery('#selProdusName').val(produs.nume);
	jQuery('#selProdusDescriere').val(produs.descriere);
	jQuery('#selProdusPret').val(produs.pret);
	jQuery('#selProdusCantitate').val(produs.cantitate);

	var categorie = produs.categorie;
	jQuery('#selectedCategorieId2').val(categorie.substring(0, categorie.indexOf(":")));
	jQuery('#selCategorie').val(categorie.substring(categorie.indexOf(":")+1, categorie.length));	
	
	var producator = produs.producator;
	jQuery('#selectedProducatorId1').val(producator.substring(0, producator.indexOf(":")));
	jQuery('#selProducator').val(producator.substring(producator.indexOf(":")+1, producator.length));

	var etalon = produs.etalon;
	jQuery('#selectedEtaloaneId1').val(etalon.substring(0, etalon.indexOf(":")));
	jQuery('#selEtaloane').val(etalon.substring(etalon.indexOf(":")+1, etalon.length));

	var atribute = produs.atribute;
	if (atribute !== undefined){
		for (i=0; i < atribute.length; i++){
			jQuery.ajax({
	            url: "atributeForProduct.htm",
	        	dataType: "html",
	        	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
	        	data: {
	        		id_atribut : atribute[i]
	    		},
				success : function( data ) { 
	    						jQuery('#attributes_div1').append(data); 
	    						nrOfAttribute++;
	    					}
			});		
		}
	}

//  <img src="incarcaImagineServlet.htm?imageName=poza1_2.jpg" />
	
	var poze = produs.poze;
	if (poze !== undefined){
		jQuery('#images_div1').append("Imaginile pentru produsul selectat:");
		for (i=0; i < poze.length; i++){
			
			var idPoza = poze[i].substring(0, poze[i].indexOf("|"));
			var descrierePoza = poze[i].substring(poze[i].indexOf("|") + 1 , poze[i].lastIndexOf("|"));
			var numePoza = poze[i].substring(poze[i].lastIndexOf("|") + 1, poze[i].length);

			var imgTag = "<div id=\"imagine_existenta_" + idPoza + "\"> <table> <tr> <td> <img src=\"incarcaImagineServlet.htm?imageName="+ numePoza +"\"> </td> <td> " + descrierePoza + " </td> <td> <img src=\"/farmacie/images/remove_attribute.jpg\" style=\"width: 15px; cursor: pointer;\" onclick=\"deleteExistingPicture(" + idPoza +")\" /> </td> </tr> </table> </div>";

			jQuery('#images_div1').append(imgTag);		
		}
	}
}

function deleteExistingPicture(idPoza){

	if (confirm("Sunteti sigur ca vreti sa stergeti imaginea?")){
		jQuery.ajax({
	        url: "deleteExistingPicture.htm",
	    	dataType: "html",
	    	data:{
		  		id_poza : idPoza
			},
			success : function( data ) { 
				data = jQuery.trim(data);
				if (data == "OK"){
					jQuery('#imagine_existenta_' + idPoza).remove();
					alert("Stergere facuta cu succes!");
				} else {
					alert("Eroare la stergere imagine! Eroare este: " + data);
				}	
			}
		});
	}
}

jQuery(function(){
	$( "#selProducator" ).autocomplete({
		source: "searchProducatori.htm",
		minLength: 1,
		delay : 500,
		select: function( event, ui ) {
			if (ui.item){
				jQuery('#selectedProducatorId1').val(ui.item.id); 
			}
		}
	});
});

jQuery(function(){
	$( "#selCategorie" ).autocomplete({
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
				jQuery('#selectedCategorieId2').val(ui.item.id); 
		}
	});
});	

jQuery(function(){
	$( "#selEtaloane" ).autocomplete({
		source: "searchEtaloane.htm",
		minLength: 1,
		delay : 500,
		select: function( event, ui ) {
			if (ui.item){
				jQuery('#selectedEtaloaneId1').val(ui.item.id); 
			}
		}
	});
});

function clearWhenDeselectProd(){
	jQuery('#selectedProduseId1').val(''); 
	jQuery('#continut_produs_selectat').css('visibility', 'hidden'); 
	jQuery('#actiuni_produs_selectat').css('visibility', 'hidden');
	nrOfCurrentAttribute = 0; nrOfAttribute = 0; nrOfCurrentImage = 0;	

	jQuery('#attributes_div1').html('');
	jQuery('#images_div1').html('');
}

function clearWhenDeselectCat(){
		jQuery('#selectedCategorieId1').val(''); 
		jQuery('#selectedProduseId1').val(''); 
		jQuery('#din_categoria').html(''); 
		jQuery('#produse1').val(''); 
		nrOfCurrentAttribute = 0; nrOfAttribute = 0; nrOfCurrentImage = 0;

		jQuery('#attributes_div1').html('');
		jQuery('#images_div1').html('');
}

function adaugaAtribut(){
	jQuery.ajax({
        url: "atributeForProduct.htm",
    	dataType: "html",
    	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
    	data: {
    		
		},
		success : function( data ) { 
						jQuery('#attributes_div1').append(data); 
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
						jQuery('#images_div1').append(data); 
					}
	});
}	

function valideazaModificariProdus(){
	var errorsMsg = "";
	
	var priceRege = /^\+?[0-9]*\.?[0-9]+$/;
	var cantitateRege = /^\+?[0-9]+$/;

	var idProdus = jQuery.trim(jQuery('#selectedProduseId1').val());
	var numeProdus = jQuery.trim(jQuery('#selProdusName').val());
	var pretProdus = jQuery.trim(jQuery('#selProdusPret').val());
	var cantitateProdus = jQuery.trim(jQuery('#selProdusCantitate').val());
	var categorieProdus = jQuery.trim(jQuery('#selectedCategorieId2').val());
	var producatorProdus = jQuery.trim(jQuery('#selectedProducatorId1').val());
	var etalonProdus = jQuery.trim(jQuery('#selectedEtaloaneId1').val());

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
    		idProdus : idProdus,
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
							jQuery('#modificaProductForm').submit();
						} else {
							alert(response);
						}
					}
	});
}

function stergeProdus(){
	if (confirm("Sunteti sigur ca vreti sa stergeti produsul?")){
		jQuery.ajax({
	        url: "stergeProdus.htm",
	    	dataType: "html",
	    	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
	    	data: {
	    		idProdus : jQuery('#selectedProduseId1').val()
			},
			success : function( data ) {
				var response = jQuery.trim(data);
				if (response == "OK"){
					alert("Stergere facuta cu success!");
					window.location.href = 'index.htm';
				} else {
					alert("Eroare la stergere produs !" + response);
				}	
			} 
		});
	}
}

</script>

<form method="post" action="modificaProdus.htm" enctype="multipart/form-data" id="modificaProductForm">
<table>
	<tr>
		<td><label for="categorie1">Selectati categoria: </label></td>
		<td style="padding-top: 10px;">
			<div class="ui-autocomplete-farmacieAdmin">
				<input id="categorie1" onkeydown="clearWhenDeselectCat()" style="background-color: #E0E0E0;">
			</div>
			<input type="hidden" name="selectedCategorieId1" id ="selectedCategorieId1" />		
		</td>
		<td></td>
	</tr>
	<tr>
		<td><label for="categorie1">Selectati produsul: </label></td>
		<td style="padding-top: 10px;">
			<div class="ui-autocomplete-farmacieAdmin">
				<input id="produse1" onkeydown="clearWhenDeselectProd()" style="background-color: #E0E0E0;">
			</div>
			<input type="hidden" name="selectedProduseId1" id ="selectedProduseId1" />		
		</td>
		<td><span id="din_categoria"></span></td>
	</tr>	
	<tr>
		<td colspan="3">
			<div id="continut_produs_selectat" style="visibility: hidden">
				<table>
					<tr>
						<td style="width: 10px;">
							Numele: 
						</td>
						<td><input type="text" name="selProdusName" id="selProdusName"  size="25"/></td>	
					</tr>
					<tr>
						<td style="width: 10px;">
							Descriere: 
						</td>
						<td><textarea cols="75" rows="6" name="selProdusDescriere" id="selProdusDescriere"></textarea></td>	
					</tr>					
					<tr>
						<td>
							Pretul: 
						</td>
						<td><input type="text" name="selProdusPret" id="selProdusPret"  size="25"/></td>
					</tr>
					<tr>
						<td>
							Cantitate: 
						</td>
						<td><input type="text" name="selProdusCantitate" id="selProdusCantitate"  size="5"/></td>
					</tr>
					<tr>
						<td><label for="selCategorie">Categoria: </label></td>
						<td style="padding-top: 10px;">
							<div class="ui-autocomplete-farmacieAdmin">
								<input id="selCategorie" onkeydown="jQuery('#selectedCategorieId2').val('');" style="background-color: #E0E0E0;">
							</div>
							<input type="hidden" name="selectedCategorieId2" id ="selectedCategorieId2" />		
						</td>
					</tr>						
					<tr>
						<td><label for="selProducator">Producatorul: </label></td>
						<td style="padding-top: 10px;">
							<div class="ui-autocomplete-farmacieAdmin">
								<input id="selProducator" onkeydown="jQuery('#selectedProducatorId1').val('');" style="background-color: #E0E0E0;">
							</div>
							<input type="hidden" name="selectedProducatorId1" id ="selectedProducatorId1" />		
						</td>	
					</tr>
					<tr>
						<td><label for="selEtaloane">Etalonul: </label></td>
						<td style="padding-top: 10px;">
							<div class="ui-autocomplete-farmacieAdmin">
								<input id="selEtaloane" onkeydown="jQuery('#selectedEtaloaneId1').val('');" style="background-color: #E0E0E0;">
							</div>
							<input type="hidden" name="selectedEtaloaneId1" id ="selectedEtaloaneId1" />		
						</td>	
					</tr>
					<tr>
						<td colspan="2">
							<div id = "attributes_div1">
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
							<div id = "images_div1">
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="button" value="Adaugati o noua imagine!" onclick="adaugaImagine()"/>
						</td>
					</tr>																						
				</table>						
			</div>
		</td>
	</tr>
</table>	
</form>		

<div id="actiuni_produs_selectat" style="visibility: hidden">
	<table>
		<tr>
			<td>
				Salveaza modificarile!  <input type="submit" value="Salveaza" onmousedown="valideazaModificariProdus()">
			</td>
			<td>
				Sterge produsul selectat!  <input type="submit" value="Sterge" onmousedown="stergeProdus()">
			</td>		
		</tr>
	</table>
</div>

			
