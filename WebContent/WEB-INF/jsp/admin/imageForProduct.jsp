<div id="image_div${model.nr_of_images_in_page}" onmousedown="nrOfCurrentImage = jQuery('#' + jQuery(this).attr('id') + ' input:first').val();">
	<input type="hidden" name="nrOfImage" id="nrOfImage" value = "${model.nr_of_images_in_page}"/>
	<script>
		jQuery(function(){
			$("#deleteImage${model.nr_of_images_in_page}").click(function() {
					jQuery('#image_div' + nrOfCurrentImage).remove();				  
				});			
		});	
	</script>
	
	<table>
		<tr>
			<td>
				Imagine de incarcat: <input type="file" name="imagineProdus${model.nr_of_images_in_page}" id="imagineProdus${model.nr_of_images_in_page}" />
			</td>
			<td>
				Comentariu: <input type="text" name="descrierePozaProdus${model.nr_of_images_in_page}" id="descrierePozaProdus${model.nr_of_images_in_page}" size="60" />
			</td>
			<td>
				<img src="/farmacie/images/remove_attribute.jpg" style="width: 15px; cursor: pointer;" id="deleteImage${model.nr_of_images_in_page}" />
			</td>			
		</tr>
	</table>
</div>