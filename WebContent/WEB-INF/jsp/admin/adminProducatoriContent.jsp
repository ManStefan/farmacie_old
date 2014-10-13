<%@ include file="/WEB-INF/jsp/include.jsp" %>

<script type="text/javascript">

function addNewProducator(newProducatorName){
	newProducatorName = jQuery.trim(newProducatorName);
	if (newProducatorName == "")
		alert("Numele nu poate fi gol!");
	else {
		jQuery.ajax({
			   type: "POST",
			   url: "addNewProducator.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: {newProducatorName: newProducatorName},
			   success: function(opt){
				   		opt = jQuery.trim(opt);
				   		if (opt == "OK"){
					   		alert("Adaugare facuta cu succes!");
				   		} else {
					   		alert("Eroare la adaugare producator : " + opt);
				   		}
						jQuery('#newProducatorName').val("");		
			   		  }
		});
	}
}


jQuery(function(){
	$( "#producatori" ).autocomplete({
		source: "searchProducatori.htm",
		minLength: 1,
		delay : 500,
		select: function( event, ui ) {
			if (ui.item){
				jQuery('#selectedProducatori').val(ui.item.id); 

/*				var attributesDiv = jQuery('#attributes_div');
				attributesDiv.css("visibility", "visible");	

				var selected_cat_label = jQuery('#selected_cat_label');
				selected_cat_label.text(ui.item.value);
				jQuery('#selected_cat_label_1').text(ui.item.value);*/
			}
		}
	});
});


function renameProducatori(){
	
	var newProducatorNameR = jQuery.trim(jQuery('#newProducatorNameR').val());
	var producatorId = jQuery('#selectedProducatori').val();

	if (producatorId == "" )
		alert("Nu ati selectat nici un producator!");
	else
	if (newProducatorNameR == "")
		alert("Introduceti noul nume! ");
	else{
		jQuery.ajax({
			   type: "POST",
			   url: "renameProducator.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: {newProducatorName: newProducatorNameR,
						producatorId : producatorId	},
			   success: function(opt){
				   		opt = jQuery.trim(opt);
				   		if (opt == "OK"){
					   		alert("Redenumire facuta cu succe!");
				   		} else {
					   		alert("Eroare la redenumire producator: " + opt);
				   		}
				   		jQuery('#newProducatorNameR').val("");
				   		jQuery('#producatori').val("");
				   		jQuery('#selectedProducatori').val("");

				   		//jQuery('#attributes_div').css('visibility', 'hidden');
				   		
			   		  }
		});	
	}
}

function deleteProducator(){
	var producatorId = jQuery('#selectedProducatori').val();

	if (producatorId == "" )
		alert("Nu ati selectat nici un producator!");
	else {
		jQuery.ajax({
			   type: "POST",
			   url: "deleteProducator.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: {producatorId : producatorId	},
			   success: function(opt){
				   		opt = jQuery.trim(opt);
				   		
				   		var msg = "Stergeti Producatorul selectat? ";
				   		if (opt != "OK"){
					   		msg += opt;		
				   			if(confirm(msg))
				   				finishDeleteProducator(producatorId);						
				   		} else
				   			finishDeleteProducator(producatorId);



				   		jQuery('#newProducatorNameR').val("");
				   		jQuery('#producatori').val("");
				   		jQuery('#selectedProducatori').val("");
			   		  }
		});			
	}		
}

function finishDeleteProducator(producatorId){
	jQuery.ajax({
		   type: "POST",
		   url: "finishDeleteProducator.htm",
		   dataType : "html",
		   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		   data: {producatorId : producatorId	},
		   success: function(opt){
			   		opt = jQuery.trim(opt);

			   		if (opt == "OK")
				   		alert("Stergere facuta cu succes!");
			   		else
				   		alert("Eroare la stergere producator: " + opt);

			   		jQuery('#newProducatorNameR').val("");
			   		jQuery('#producatori').val("");
			   		jQuery('#selectedProducatori').val("");
		   }
	});
	
}



</script>


<table>
	<tr>
		<td colspan="3">
			Adaugati un producator nou: <input type="text" name="newProducatorName" id="newProducatorName"  size="25"/> <input type="submit" value="Adauga!" onclick="addNewProducator(document.getElementById('newProducatorName').value)">
		</td>
	</tr>
	<tr>
		<td style="padding-top: 10px;">
			<div class="ui-autocomplete-farmacieAdmin">
				<label for="producatori">Selectati un producator: </label>
				<input id="producatori" onkeydown="jQuery('#selectedProducatori').val('');" style="background-color: #E0E0E0;">
			</div>
			<input type="hidden" name="selectedProducatori" id ="selectedProducatori" />		
		</td>
		<td style="padding-left: 10px; padding-top: 10px;">
			Introduceti noul nume:
			<input type="text" size="30" name="newProducatorNameR" id="newProducatorNameR" >
			<input type="submit" value="Redenumeste!" name="renameProducatoriBut" id="renameProducatoriBut" onclick="renameProducatori()" />
		</td>
		<td style="padding-left: 25px; padding-top: 10px;">
			<input type="submit" value="Sterge selectia!" style="padding-left: 25px;" onClick="if(confirm('Stergeti producatorul selectat?')) deleteProducator()"/>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<hr width="3px"/>
		</td>	
	</tr>
</table>