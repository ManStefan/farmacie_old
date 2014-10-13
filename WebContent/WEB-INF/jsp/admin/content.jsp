<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Administrare</title>	
<script type="text/javascript" src="/farmacie/js/jquery-1.6.2.js"></script>
<script type="text/javascript" src="/farmacie/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/farmacie/js/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="/farmacie/js/utils.js"></script>
<link rel="stylesheet" href="/farmacie/css/demos.css" />
<link type="text/css" href="/farmacie/css/ui-lightness/jquery-ui-1.8.17.custom.css" rel="stylesheet" />	
<script>
$(function() {
	$( "#tabs" ).tabs({
		ajaxOptions: {
			error: function( xhr, status, index, anchor ) {
				$( anchor.hash ).html(
					"Couldn't load this tab. We'll try to fix this as soon as possible. " +
					"If this wouldn't be a demo." );
			},
			cache: false
		},
		cache: false,
		select: function(event, ui) {
			$("#ui-tabs-1").html("");	
			$("#ui-tabs-2").html("");
			$("#ui-tabs-3").html("");
			$("#ui-tabs-4").html("");
			$("#ui-tabs-5").html("");
			$("#ui-tabs-6").html("");
		}
	});
});
</script>
</head>

<body>
	<a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
	<div id="tabs">
		<ul>
			<li><a href="ajax/adminCategorii.htm">Editare si Adaugare Categorii</a></li>
			<li><a href="ajax/adminAtribute.htm">Editare si Adaugare Atribute</a></li>
			<li><a href="ajax/adminProducatori.htm">Editare si Adaugare Producatori</a></li>
			<li><a href="ajax/adminEtaloane.htm">Editare si Adaugare Etaloane</a></li>
			<li><a href="ajax/adminProduse.htm">Adaugare Produse</a></li>
			<li><a href="ajax/adminEditareProduse.htm">Editare Produse</a></li>
		</ul>
	</div>
</body>
</html>



