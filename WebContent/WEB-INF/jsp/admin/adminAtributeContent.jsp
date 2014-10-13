<%@ include file="/WEB-INF/jsp/include.jsp" %>

<script type="text/javascript">
	function addNewAttrCat(newAttrCatName){
		newAttrCatName = jQuery.trim(newAttrCatName);
		if (newAttrCatName == "")
			alert("Numele nu poate fi gol!");
		else {
			jQuery.ajax({
				   type: "POST",
				   url: "addNewAttrCat.htm",
				   dataType : "html",
				   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				   data: {newAttrCatName: newAttrCatName},
				   success: function(opt){
					   		opt = jQuery.trim(opt);
					   		if (opt == "OK"){
						   		alert("Adaugare facuta cu succe!");
					   		} else {
						   		alert("Eroare la adaugare categorie atribute: " + opt);
					   		}
							jQuery('#newAttrCatName').val("");
					   		
				   		  }
			});
		}
	}

	function addNewAttr(newAttrName, selectedCat){
		newAttrName = jQuery.trim(newAttrName);
		if (newAttrName == "")
			alert("Numele nu poate fi gol!");
		else
		if (selectedCat == "")
			alert("Trebuie sa selectati o categorie a atributelor!");
		else {
			jQuery.ajax({
				   type: "POST",
				   url: "addNewAttr.htm",
				   dataType : "html",
				   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				   data: {newAttrName: newAttrName,
						  selectedCat: selectedCat},
				   success: function(opt){
					   		opt = jQuery.trim(opt);
					   		if (opt == "OK"){
						   		alert("Adaugare facuta cu succes!");
					   		} else {
						   		alert("Eroare la adaugare atribut : " + opt);
					   		}
							jQuery('#newAttrName').val("");		
				   		  }
			});
		}
	}

	

	jQuery(function(){
		$( "#attrCat" ).autocomplete({
			source: "searchAttrCat.htm",
			minLength: 1,
			delay : 500,
			select: function( event, ui ) {
				if (ui.item){
					jQuery('#selectedAttrCat').val(ui.item.id); 

					var attributesDiv = jQuery('#attributes_div');
					attributesDiv.css("visibility", "visible");	

					var selected_cat_label = jQuery('#selected_cat_label');
					selected_cat_label.text(ui.item.value);
					jQuery('#selected_cat_label_1').text(ui.item.value);
				}
			}
		});
	});
	
	jQuery(function(){
		$( "#attrs" ).autocomplete({
			source: function(request, response) {
				$.ajax({

	                url: "searchAttr.htm",
                	dataType: "json",
                	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                	data: {
                			term : request.term,
                			selectedAttrCatId : jQuery('#selectedAttrCat').val()
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
					jQuery('#selectedAttr').val(ui.item.id); 
			}
		});
	});

	function renameAttrCat(){
		
		var newAttrCatName = jQuery.trim(jQuery('#newAttrCatNameR').val());
		var attrCatId = jQuery('#selectedAttrCat').val();

		if (attrCatId == "" )
			alert("Nu ati selectat nici o categorie!");
		else
		if (newAttrCatName == "")
			alert("Introduceti noul nume! ");
		else{
			jQuery.ajax({
				   type: "POST",
				   url: "renameAttrCat.htm",
				   dataType : "html",
				   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				   data: {newAttrCatName: newAttrCatName,
				   		  attrCatId : attrCatId	},
				   success: function(opt){
					   		opt = jQuery.trim(opt);
					   		if (opt == "OK"){
						   		alert("Redenumire facuta cu succe!");
					   		} else {
						   		alert("Eroare la redenumire categorie atribute: " + opt);
					   		}
					   		jQuery('#newAttrCatNameR').val("");
					   		jQuery('#attrCat').val("");
					   		jQuery('#selectedAttrCat').val("");

					   		jQuery('#attributes_div').css('visibility', 'hidden');
					   		
				   		  }
			});	
		}
	}

	function renameAttr(){
		
		var newAttrName = jQuery.trim(jQuery('#newAttrNameR').val());
		var attrId = jQuery('#selectedAttr').val();

		if (attrId == "" )
			alert("Nu ati selectat nici un atribut!");
		else
		if (newAttrName == "")
			alert("Introduceti noul nume! ");
		else{
			jQuery.ajax({
				   type: "POST",
				   url: "renameAttr.htm",
				   dataType : "html",
				   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				   data: {newAttrName: newAttrName,
				   		  attrId : attrId	},
				   success: function(opt){
					   		opt = jQuery.trim(opt);
					   		if (opt == "OK"){
						   		alert("Redenumire facuta cu succe!");
					   		} else {
						   		alert("Eroare la redenumire categorie atribute: " + opt);
					   		}
					   		jQuery('#newAttrNameR').val("");
					   		jQuery('#attrs').val("");
					   		jQuery('#selectedAttr').val("");
				   		  }
			});	
		}
	}

	
	function deleteAttrCat(){
		var attrCatId = jQuery('#selectedAttrCat').val();

		if (attrCatId == "" )
			alert("Nu ati selectat nici o categorie!");
		else {
			jQuery.ajax({
				   type: "POST",
				   url: "deleteAttrCat.htm",
				   dataType : "html",
				   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				   data: {attrCatId : attrCatId	},
				   success: function(opt){
					   		opt = jQuery.trim(opt);
					   		if (opt == "OK"){
						   		alert("Stergere facuta cu succe!");
					   		} else {
						   		alert("Eroare la stergere categorie atribute: " + opt);
					   		}
					   		jQuery('#newAttrCatNameR').val("");
					   		jQuery('#attrCat').val("");
					   		jQuery('#selectedAttrCat').val("");

					   		jQuery('#attributes_div').css('visibility', 'hidden');
					   		
				   		  }
			});			
		}		
	}

	function deleteAttr(){
		var attrId = jQuery('#selectedAttr').val();

		if (attrId == "" )
			alert("Nu ati selectat nici un atribut!");
		else {
			jQuery.ajax({
				   type: "POST",
				   url: "deleteAttr.htm",
				   dataType : "html",
				   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				   data: {attrId : attrId	},
				   success: function(opt){
					   		opt = jQuery.trim(opt);
					   		
					   		var msg = "Stergeti atributul selectat? ";
					   		if (opt != "OK"){
						   		msg += opt;								
					   		}

				   			if(confirm(msg))
				   				finishDeleteAttr(attrId);

					   		jQuery('#newAttrNameR').val("");
					   		jQuery('#attrs').val("");
					   		jQuery('#selectedAttr').val("");
				   		  }
			});			
		}		
	}

	function finishDeleteAttr(attrId){
		jQuery.ajax({
			   type: "POST",
			   url: "finishDeleteAttr.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: {attrId : attrId	},
			   success: function(opt){
				   		opt = jQuery.trim(opt);

				   		if (opt == "OK")
					   		alert("Stergere facuta cu succes!");
				   		else
					   		alert("Eroare la stergere atribut: " + opt);

				   		jQuery('#newAttrNameR').val("");
				   		jQuery('#attrs').val("");
				   		jQuery('#selectedAttr').val("");
			   }
		});
		
	}
	
</script>

<table>
	<tr>
		<td colspan="3">
			Adaugati o categorie noua: <input type="text" name="newAttrCatName" id="newAttrCatName"  size="25"/> <input type="submit" value="Adauga!" onclick="addNewAttrCat(document.getElementById('newAttrCatName').value)">
		</td>
	</tr>
	<tr>
		<td style="padding-top: 10px;">
			<div class="ui-autocomplete-farmacieAdmin">
				<label for="attrCat">Selectati categoria atributelor: </label>
				<input id="attrCat" onkeydown="jQuery('#selectedAttrCat').val(''); jQuery('#selectedAttr').val(''); jQuery('#attrs').val(''); jQuery('#attributes_div').css('visibility', 'hidden');" style="background-color: #E0E0E0;">
			</div>
			<input type="hidden" name="selectedAttrCat" id ="selectedAttrCat" />		
		</td>
		<td style="padding-left: 10px; padding-top: 10px;">
			Introduceti noul nume:
			<input type="text" size="30" name="newAttrCatNameR" id="newAttrCatNameR" >
			<input type="submit" value="Redenumeste!" name="renameAttrCatBut" id="renameAttrCatBut" onclick="renameAttrCat()" />
		</td>
		<td style="padding-left: 25px; padding-top: 10px;">
			<input type="submit" value="Sterge selectia!" style="padding-left: 25px;" onClick="if(confirm('Stergeti categoria selectata?')) deleteAttrCat()"/>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<hr width="3px"/>
		</td>	
	</tr>
</table>
<div id="attributes_div" style="visibility: hidden">
	<table>
		<tr>
			<td style="padding-top: 20px;">
				Adaugati un atribut nou pentru categoria <b><label for="newAttrName" id='selected_cat_label'></label></b>: <input type="text" name="newAttrName" id="newAttrName"  size="25"/> <input type="submit" value="Adauga!" onclick="addNewAttr(document.getElementById('newAttrName').value, document.getElementById('selectedAttrCat').value)">
			</td>	
		</tr>
		<tr>
			<td style="padding-top: 10px;">
				<div class="ui-autocomplete-farmacieAdmin">
					<label for="attrs">Selectati un atribut pentru categoria </label><b><label id='selected_cat_label_1'></label></b>
					<input id="attrs" onkeydown="jQuery('#selectedAttr').val('');" style="background-color: #E0E0E0;" style="background-color: #E0E0E0;">
				</div>
				<input type="hidden" name="selectedAttr" id ="selectedAttr" />		
			</td>
			<td style="padding-left: 10px; padding-top: 10px;">
				Introduceti noul nume:
				<input type="text" size="30" name="newAttrNameR" id="newAttrNameR" >
				<input type="submit" value="Redenumeste!" name="renameAttrBut" id="renameAttrBut" onclick="renameAttr()" />
			</td>
			<td style="padding-left: 25px; padding-top: 10px;">
				<input type="submit" value="Sterge selectia!" style="padding-left: 25px;" onClick="deleteAttr()"/>
			</td> 	
		</tr>
		
	</table>
</div>
