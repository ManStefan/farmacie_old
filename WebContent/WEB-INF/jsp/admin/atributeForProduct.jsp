<%@ include file="/WEB-INF/jsp/include.jsp" %>

<div id="attribute_div${model.nr_of_attributes_in_page}" onmousedown="nrOfCurrentAttribute = jQuery('#' + jQuery(this).attr('id') + ' input:first').val();">
	<input type="hidden" name="nrOfAttribute" id="nrOfAttribute" value = "${model.nr_of_attributes_in_page}"/>
	<script>
		jQuery(function(){	
			$( "#attrCat${model.nr_of_attributes_in_page}").autocomplete({
				source: "searchAttrCat.htm",
				minLength: 1,
				delay : 500,
				select: function( event, ui ) {
					if (ui.item){
						jQuery('#selectedAttrCat' + nrOfCurrentAttribute).val(ui.item.id); 
		
						var atributDiv = jQuery('#atributDiv' + nrOfCurrentAttribute);
						jQuery('#atributDiv' + nrOfCurrentAttribute).css("visibility", "visible");	
		
						jQuery('#selected_cat_label_1' + nrOfCurrentAttribute).text(ui.item.value);					
					}
				}
			});
		});	
		
		jQuery(function(){
			$( "#attrs${model.nr_of_attributes_in_page}").autocomplete({
				source: function(request, response) {
					$.ajax({
		
		                url: "searchAttr.htm",
		            	dataType: "json",
		            	contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		            	data: {
		            			term : request.term,
		            			selectedAttrCatId : jQuery('#selectedAttrCat' + nrOfCurrentAttribute).val()
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
						jQuery('#selectedAttr' + nrOfCurrentAttribute).val(ui.item.id); 
				}
			});
		});	

		jQuery(function(){
			$("#deleteAttribute${model.nr_of_attributes_in_page}").click(function() {
					jQuery('#attribute_div' + nrOfCurrentAttribute).remove();				  
				});			
		});
		
	</script>

	<table>
		<tr>
			<td>
				<div id = "catAtributuluiDiv${model.nr_of_attributes_in_page}">
					<table>
						<tr>
							<td><label for="attrCat${model.nr_of_attributes_in_page}">Categoria atributului: </label></td>
							<td style="padding-top: 10px;">
								<div class="ui-autocomplete-farmacieAdmin">
									<input id="attrCat${model.nr_of_attributes_in_page}" onkeydown="jQuery('#selectedAttrCat' + nrOfCurrentAttribute).val(''); jQuery('#selectedAttr'+ nrOfCurrentAttribute).val(''); jQuery('#attrs'+ nrOfCurrentAttribute).val(''); jQuery('#atributDiv'+ nrOfCurrentAttribute).css('visibility', 'hidden');" style="background-color: #E0E0E0;" value="${model.nume_cat_atr}"/>
								</div>
								<input type="hidden" name="selectedAttrCat${model.nr_of_attributes_in_page}" id ="selectedAttrCat${model.nr_of_attributes_in_page}" value="${model.id_cat_atr}"/>	
							</td>
						</tr>
					</table>
				</div>
			</td>				
			<td>
				<div id = "atributDiv${model.nr_of_attributes_in_page}" <c:if test="${model.id_atribut != null}"> style="visibility: visible" </c:if> <c:if test="${model.id_atribut == null}">style="visibility: hidden" </c:if>>
					<table>
						<tr>
							<td style="padding-top: 10px;">
								<div class="ui-autocomplete-farmacieAdmin">
									<label for="attrs${model.nr_of_attributes_in_page}">Selectati un atribut pentru categoria </label><b><label id='selected_cat_label_1${model.nr_of_attributes_in_page}'></label></b>
									<input id="attrs${model.nr_of_attributes_in_page}" onkeydown="jQuery('#selectedAttr' + nrOfCurrentAttribute).val('');" style="background-color: #E0E0E0;" value="${model.nume_atribut}">
								</div>
								<input type="hidden" name="selectedAttr${model.nr_of_attributes_in_page}" id ="selectedAttr${model.nr_of_attributes_in_page}" value="${model.id_atribut}"/>		
							</td>
		
						</tr>
					</table>
				</div>
			</td>
			<td>
				<img src="/farmacie/images/remove_attribute.jpg" style="width: 15px; cursor: pointer;" id="deleteAttribute${model.nr_of_attributes_in_page}" />
			</td>					
		</tr>
	</table>	
</div>			
