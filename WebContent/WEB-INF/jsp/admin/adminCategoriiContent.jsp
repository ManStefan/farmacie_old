<%@ include file="/WEB-INF/jsp/include.jsp" %>

<script type="text/javascript">
	function exploreBack(currDropDownNr){
		//must delete the current drop-down list and enable the one just above it
		
		//current drop-down
		var currSelectDiv = jQuery('#dropDownDiv' + currDropDownNr);		
			currSelectDiv.remove();	

		//above drop-down
		var aboveDropDownNr = --currDropDownNr;
		
		var aboveSelect = jQuery('#selCat' + aboveDropDownNr);
		aboveSelect.attr("disabled", false);
		
		var imgUp = jQuery('#imgUp' + aboveDropDownNr);
		if (imgUp.length > 0)
			imgUp.css("visibility", "visible");		
		
		var imgDown = jQuery('#imgDown' + aboveDropDownNr);
		imgDown.css("visibility", "visible");	

		var addChildCatDiv = jQuery('#addChildCatDiv' + aboveDropDownNr);
		addChildCatDiv.css("visibility", "visible");	
		
		var addBrotherCatDiv = jQuery('#addBrotherCatDiv' + aboveDropDownNr);
		addBrotherCatDiv.css("visibility", "visible");	

		var updateNameCat = jQuery('#updateNameCat' + aboveDropDownNr);
		updateNameCat.css("visibility", "visible");

		var deleteCatButton = jQuery('#deleteCatButton' + aboveDropDownNr);
		deleteCatButton.css("visibility", "visible");
		deleteCatButton.attr("disabled", false);
		

//		var deleteCatOptions = jQuery('#deleteCatOptions' + aboveDropDownNr);
//		deleteCatOptions.css("visibility", "visible");							


		//call the server to substract the sequence number	
		jQuery.ajax({
			   type: "POST",
			   url: "childrenCategories.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: { action: "exploreBACK" },
			   success: function(opt){
			   }
		});		 
	}

	function getChildrenCategories(catDescr){
		jQuery.ajax({
			   type: "POST",
			   url: "childrenCategories.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: {idCat: catDescr.split("||")[0],
			   		  action: "exploreFW"	 },
			   success: function(opt){
					if (jQuery.trim(opt) != ''){
						//disable other drop-down lists
						var i = 1;
						while (jQuery('#selCat' + i).length > 0){
							jQuery('#selCat' + i).attr("disabled", true);

							var imgUp = jQuery('#imgUp' + i);
							var imgDown = jQuery('#imgDown' + i);
							if (imgUp.length > 0)
								imgUp.css("visibility", "hidden");
							if (imgDown.length > 0)
								imgDown.css("visibility", "hidden");
							
							var addChildCatDiv = jQuery('#addChildCatDiv' + i);
							addChildCatDiv.css("visibility", "hidden");	
							
							var addBrotherCatDiv = jQuery('#addBrotherCatDiv' + i);
							addBrotherCatDiv.css("visibility", "hidden");	

							var updateNameCat = jQuery('#updateNameCat' + i);
							updateNameCat.css("visibility", "hidden");

							var deleteCatButton = jQuery('#deleteCatButton' + i);
							deleteCatButton.css("visibility", "hidden");
												
							var deleteCatOptions = jQuery('#deleteCatOptions' + i);
							deleteCatOptions.css("visibility", "hidden");							
							
							i++;
						}							   
			   			jQuery('#categoriesDiv').append(opt);
					}
			   }
		});
	}

	function cancelDeleteCat(nrOfDropDowns){
		var deleteButton = jQuery('#deleteCatButton' + nrOfDropDowns);	
		deleteButton.attr("disabled", false);

		var finishDeleteCatButton = jQuery('#deleteCatOptions' + nrOfDropDowns);
		finishDeleteCatButton.css("visibility", "hidden");
		
	}
	
	function deleteCat(catDescr, nrOfDropDowns) {
		jQuery.ajax({
			   type: "POST",
			   url: "deleteCategory.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: {idCat: catDescr.split("||")[0]
					 },
			   success: function(opt){
						   	var optTrim = jQuery.trim(opt);
						   	var areProductsInCategories = optTrim.substr(optTrim.indexOf("are_products_in_categories=") + 27,1);

						   	if (areProductsInCategories == 1){
								var deleteButton = jQuery('#deleteCatButton' + nrOfDropDowns);
								//deleteButton.css("visibility", "hidden");	
								deleteButton.attr("disabled", true);
								  
								var finishDeleteCatButton = jQuery('#deleteCatOptions' + nrOfDropDowns);
								finishDeleteCatButton.css("visibility", "visible");
						   	} else
							   	finishDeleteCat(catDescr, 0, nrOfDropDowns);
			   			}
		});
	}

	function finishDeleteCat(catDescr, deleteProdCat, nrOfDropDowns) {
		jQuery.ajax({
			   type: "POST",
			   url: "finishDeleteCategory.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: {idCat: catDescr.split("||")[0],
			   		  deleteAssociatedProds: deleteProdCat 
					 },
			   success: function(opt){
						   var optTrim = jQuery.trim(opt);

							if (optTrim == "OK"){
								jQuery("#selCat" + nrOfDropDowns + " option[value='" + catDescr + "']").remove();
								
								if (!jQuery("#selCat" + nrOfDropDowns).children(":selected").val())
									exploreBack(nrOfDropDowns);
								
								alert("Stergere facuta cu succes!");
							} else {
								alert("Eroare la stergere! " + optTrim);
							}
							var finishDeleteCatButton = jQuery('#deleteCatOptions' + nrOfDropDowns);
							finishDeleteCatButton.css("visibility", "hidden");	
							var deleteButton = jQuery('#deleteCatButton' + nrOfDropDowns);	
							deleteButton.attr("disabled", false);													
			   			}
		});
	}
	
	function addChildCategory(newChildName, parentCatDescr){
		if (jQuery.trim(newChildName) == "")
			alert("Numele categoriei nu poate sa fie gol!");
		else
		jQuery.ajax({
			   type: "POST",
			   url: "adChildCategory.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: {idCatParent: parentCatDescr.split("||")[0],
					  newChildName: jQuery.trim(newChildName)
					 },
			   success: function(opt){
						   var optTrim = jQuery.trim(opt);
						   if (optTrim == "OK"){
							   alert("Inserare facuta cu success!");
						   } else {
							   alert("Adaugare esuata:" + optTrim);
						   }
							   
			   			}
		});
	}	


	function updateNameCat(newCatName, catDescr){
		if (jQuery.trim(newCatName) == "")
			alert("Noul nume nu poate sa fie gol!");
		else {
			jQuery.ajax({
				   type: "POST",
				   url: "updateNameCat.htm",
				   dataType : "html",
				   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				   data: {idCat: catDescr.split("||")[0],
						  newCatName: jQuery.trim(newCatName)
						 },
				   success: function(opt){
							   var optTrim = jQuery.trim(opt);
							   var optDesc = optTrim.split("||");
							   if (optDesc[0] == "OK"){
									var oldSelectValue = optDesc[3] + "||" + optDesc[2] + "||" + optDesc[5] + "||" + optDesc[6];
									jQuery("#selCat" + optDesc[1] + " option[value='" + oldSelectValue + "']").remove(); 

									var newModifiedValue = optDesc[3] + "||" + optDesc[4] + "||" + optDesc[5] + "||" + optDesc[6];
									jQuery("#selCat" + optDesc[1]).append('<option value="' + newModifiedValue + '" selected="selected">' + optDesc[4] + '</option>');

									   alert("Modificare facuta cu success!");
							   } else {
								   alert("Modificare esuata:" + optTrim);
							   }
								   
				   			}
			});			
		}
	}
	
	function addBrotherCategory(newBrotherName, parentCatDescr){
		if (jQuery.trim(newBrotherName) == "")
			alert("Numele categoriei nu poate sa fie gol!");
		else		
		jQuery.ajax({
			   type: "POST",
			   url: "adBrotherCategory.htm",
			   dataType : "html",
			   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			   data: {idCatBrother: parentCatDescr.split("||")[0],
					  newBrotherName: jQuery.trim(newBrotherName)
					 },
			   success: function(opt){
						   var optTrim = jQuery.trim(opt);
						   if (optTrim.split("||")[0] == "OK"){
							   alert("Inserare facuta cu success!");
							   var brotherDescr = optTrim.split("||");
							   jQuery("#selCat" + brotherDescr[1]).append('<option value=\"'+ brotherDescr[2] + '||' + brotherDescr[3] + '||' + brotherDescr[4] + '||' + brotherDescr[5] + '\">' + brotherDescr[3] + '</option>');
						   } else {
							   alert("Adaugare esuata:" + optTrim);
						   }							   
			   			}
		});
	}
</script>

<div id="categoriesDiv">
	<jsp:include page="childrenCategories.jsp" />
</div>
