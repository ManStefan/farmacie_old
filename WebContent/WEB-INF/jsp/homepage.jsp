<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<tiles:insertAttribute name="head" />

<tiles:insertAttribute name="templatemo_menu_panel" />

<div id="templatemo_header_content_container">    
        
	<div id="templatemo_header_section">
    	<div id="templatemo_title_section">
         	<spring:message code="farmacie.titlu"/>
        </div>

		<tiles:insertAttribute name="templatemo_search_section" />	
    </div> <!-- end of templatemo header panel -->
    
    <div id="templatemo_content">
        <div id="templatemo_content_left">
            	<tiles:insertAttribute name="continut1" />  
        </div>
    
        <div id="templatemo_content_right">
			<tiles:insertAttribute name="popup_menu_produse" />   
        </div> <!-- end of right content -->
	</div> <!-- end of content -->
</div> <!-- end of content container -->

<div id="templatemo_bottom_panel">
	<div id="templatemo_bottom_section">
        <tiles:insertAttribute name="templatemo_bottom_section_content" />
    </div> <!-- end of templatemo bottom section -->
</div> <!-- end of templatemo bottom panel -->
    
<div id="templatemo_footer_panel">
	<tiles:insertAttribute name="templatemo_footer_section" />
</div>

<tiles:insertAttribute name="bottom" />