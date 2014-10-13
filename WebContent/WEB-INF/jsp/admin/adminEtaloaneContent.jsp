<%@ include file="/WEB-INF/jsp/include.jsp" %>

<script type="text/javascript">
function addNewEtalon(newEtalonName){
	newEtalonName = jQuery.trim(newEtalonName);
	if (newEtalonName == "")
		alert("Numele nu poate fi gol!");
	else {
		jQuery.ajax({
			   type: "POST",
			   url: "addNewEtalon.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: {newEtalonName: newEtalonName},
			   success: function(opt){
				   		opt = jQuery.trim(opt);
				   		if (opt == "OK"){
					   		alert("Adaugare facuta cu succes!");
				   		} else {
					   		alert("Eroare la adaugare Etalon : " + opt);
				   		}
						jQuery('#newEtalonName').val("");		
			   		  }
		});
	}
}


jQuery(function(){
	$( "#etaloane" ).autocomplete({
		source: "searchEtaloane.htm",
		minLength: 1,
		delay : 500,
		select: function( event, ui ) {
			if (ui.item){
				jQuery('#selectedEtaloane').val(ui.item.id); 
			}
		}
	});
});

function renameEtaloane(){
	
	var newEtalonNameR = jQuery.trim(jQuery('#newEtalonNameR').val());
	var etalonId = jQuery('#selectedEtaloane').val();

	if (etalonId == "" )
		alert("Nu ati selectat nici un etalon!");
	else
	if (newEtalonNameR == "")
		alert("Introduceti noul nume! ");
	else{
		jQuery.ajax({
			   type: "POST",
			   url: "renameEtalon.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: {newEtalonName: newEtalonNameR,
						etalonId : etalonId	},
			   success: function(opt){
				   		opt = jQuery.trim(opt);
				   		if (opt == "OK"){
					   		alert("Redenumire facuta cu succe!");
				   		} else {
					   		alert("Eroare la redenumire etalon: " + opt);
				   		}
				   		jQuery('#newEtalonNameR').val("");
				   		jQuery('#etaloane').val("");
				   		jQuery('#selectedEtaloane').val("");
				   		
			   		  }
		});	
	}
}

function deleteEtalon(){
	var etalonId = jQuery('#selectedEtaloane').val();

	if (etalonId == "" )
		alert("Nu ati selectat nici un etalon!");
	else {
		jQuery.ajax({
			   type: "POST",
			   url: "deleteEtalon.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: {etalonId : etalonId	},
			   success: function(opt){
				   		opt = jQuery.trim(opt);
				   		
				   		var msg = "Stergeti Etalonul selectat? ";
				   		if (opt != "OK"){
					   		msg += opt;		
				   			if(confirm(msg))
				   				finishDeleteEtalon(etalonId);						
				   		} else
				   			finishDeleteEtalon(etalonId);

				   		jQuery('#newEtalonNameR').val("");
				   		jQuery('#etaloane').val("");
				   		jQuery('#selectedEtaloane').val("");
			   		  }
		});			
	}		
}

function finishDeleteEtalon(etalonId){
	jQuery.ajax({
		   type: "POST",
		   url: "finishDeleteEtalon.htm",
		   dataType : "html",
		   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		   data: {etalonId : etalonId	},
		   success: function(opt){
			   		opt = jQuery.trim(opt);

			   		if (opt == "OK")
				   		alert("Stergere facuta cu succes!");
			   		else
				   		alert("Eroare la stergere etalon: " + opt);

			   		jQuery('#newEtalonNameR').val("");
			   		jQuery('#etaloane').val("");
			   		jQuery('#selectedEtaloane').val("");
		   }
	});
}
</script>

<table>
	<tr>
		<td colspan="3">
			Adaugati un Etalon nou: <input type="text" name="newEtalonName" id="newEtalonName"  size="25"/> <input type="submit" value="Adauga!" onclick="addNewEtalon(document.getElementById('newEtalonName').value)">
		</td>
	</tr>
	<tr>
		<td style="padding-top: 10px;">
			<div class="ui-autocomplete-farmacieAdmin">
				<label for="etaloane">Selectati un Etalon: </label>
				<input id="etaloane" onkeydown="jQuery('#selectedEtaloane').val('');" style="background-color: #E0E0E0;">
			</div>
			<input type="hidden" name="selectedEtaloane" id ="selectedEtaloane" />		
		</td>
		<td style="padding-left: 10px; padding-top: 10px;">
			Introduceti noul nume:
			<input type="text" size="30" name="newEtalonNameR" id="newEtalonNameR" >
			<input type="submit" value="Redenumeste!" name="renameEtaloaneBut" id="renameEtaloaneBut" onclick="renameEtaloane()" />
		</td>
		<td style="padding-left: 25px; padding-top: 10px;">
			<input type="submit" value="Sterge selectia!" style="padding-left: 25px;" onClick="if(confirm('Stergeti Etalonul selectat?')) deleteEtalon()"/>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<hr width="3px"/>
		</td>	
	</tr>
</table>