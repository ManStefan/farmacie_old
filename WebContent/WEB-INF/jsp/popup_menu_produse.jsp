<%@ include file="/WEB-INF/jsp/include.jsp" %>

<form name="submitCategorie" method="get" action="produse.htm">
	<input type="hidden" name="id_categorie" id="id_categorie" />
</form>

<div class="templatemo_right_section">
	<h2><spring:message code="farmacie.produse"/></h2>
                    
<!-- ---------------------------------------------------------------------------------------------- -->
<!--
<!--

Welcome to the FreeStyle Menu script!

The menus have 4 components that you need to copy and paste into your page:
 1) A <link> tag that includes a CSS file, controlling menu layout and formatting.
 2) A core <script> tag that includes the main fsmenu.js to control menu behaviour.
 3) The menu data itself, which is either a <ul> list or some <div> tags.
 4) A configuration <script> that activates the menu data.

(1) and (2) must be pasted into your document <head>, while (3) must be pasted
into your document <body>. (4) must be pasted anywhere after (2); you can even
combine the two into one file if you want.

I suggest using server-side includes for including menu data on all pages of your site.
Try a language like PHP or ASP if your webhost supports it perhaps.

See below for menu examples; there are 2 main modes of operation for this script, using a
nested UL/LI list, and using un-nested tags in the page.
I recommend you use the list mode, but I've included an example of the other as well.

Good luck - Angus.

-->


 <!-- FreeStyle Menu v1.0RC by Angus Turnbull http://www.twinhelix.com -->
 <script type="text/javascript" src="js/fsmenu.js"></script>

 <!-- Demo CSS layouts for the list menu. Pick your favourite one and customise! -->
 <!-- Remove all but one and change "alternate stylesheet" to "stylesheet" to enable -->
 <link rel="stylesheet" type="text/css" id="listmenu-o"
  href="css/listmenu_o.css" title="Vertical 'Office'" />
<!--  <link rel="alternate stylesheet" type="text/css" id="listmenu-v"
  href="../css/listmenu_v.css" title="Vertical 'Earth'" />
 <link rel="alternate stylesheet" type="text/css" id="listmenu-h"
  href="../css/listmenu_h.css" title="Horizontal 'Earth'" />
 <link rel="alternate stylesheet" type="text/css" id="listmenu-a"
  href="../css/listmenu_a.css" title="Accordion Menu" />  -->

 <!-- Fallback CSS menu file allows list menu operation when JS is disabled. -->
 <!-- This is automatically disabled via its ID when the script kicks in. -->
 <link rel="stylesheet" type="text/css" id="fsmenu-fallback"
  href="css/listmenu_fallback.css" />

 <!-- Alternatively, this CSS file is for the second div-based demo menu. -->
 <link rel="stylesheet" type="text/css" href="css/divmenu.css" />

<body style="font: 13px/15px sans-serif; background-color: #FFF">

<!--

***** EXAMPLE 1: LIST MENU (v5+ browsers only) *****

You just need a series of <ul> lists, one nested inside another, with <a> tags in each item,
and <ul> tags after <a> tags to trigger another level of submenus.
The script will then automatically manage them as a multilevel popup menu.
Paste your data into here to get started, and be careful to close all your </li> tags!

-->

<ul class="menulist" id="listMenuRoot">
	<c:forEach var="element" items="${model.pop_up_menu_categorii}">
		<c:if test="${element.nivel > 0}">
			<a href="#" <c:if test="${element.frunza == 1}"> onclick="document.submitCategorie.id_categorie.value='${element.id}'; document.submitCategorie.submit();" </c:if>>
		</c:if>
	
		<c:out escapeXml="false" value="${element.nume}"/>
		
		<c:if test="${element.nivel > 0}">
			</a>
		</c:if>
		
	</c:forEach>
</ul>


<script type="text/javascript">

//<![CDATA[

// For each menu you create, you must create a matching "FSMenu" JavaScript object to represent
// it and manage its behaviour. You don't have to edit this script at all if you don't want to;
// these comments are just here for completeness. Also, feel free to paste this script into the
// external .JS file to make including it in your pages easier!

// Here's a menu object to control the above list of menu data:
var listMenu = new FSMenu('listMenu', true, 'display', 'block', 'none');

// The parameters of the FSMenu object are:
//  1) Its own name in quotes.
//  2) Whether this is a nested list menu or not (in this case, true means yes).
//  3) The CSS property name to change when menus are shown and hidden.
//  4) The visible value of that CSS property.
//  5) The hidden value of that CSS property.
//
// Next, here's some optional settings for delays and highlighting:
//  * showDelay is the time (in milliseconds) to display a new child menu.
//    Remember that 1000 milliseconds = 1 second.
//  * switchDelay is the time to switch from one child menu to another child menu.
//    Set this higher and point at 2 neighbouring items to see what it does.
//  * hideDelay is the time it takes for a menu to hide after mouseout.
//    Set this to a negative number to disable hiding entirely.
//  * cssLitClass is the CSS classname applied to parent items of active menus.
//  * showOnClick will, suprisingly, set the menus to show on click. Pick one of 4 values:
//     0 = All levels show on mouseover.
//     1 = Menu activates on click of first level, then shows on mouseover.
//     2 = All levels activate on click, then shows on mouseover.
//     3 = All levels show on click only (no mouseover at all).
//  * hideOnClick hides all visible menus when one is clicked (defaults to true).
//  * animInSpeed and animOutSpeed set the animation speed. Set to a number
//    between 0 and 1 where higher = faster. Setting both to 1 disables animation.

//listMenu.showDelay = 0;
//listMenu.switchDelay = 125;
//listMenu.hideDelay = 500;
//listMenu.cssLitClass = 'highlighted';
//listMenu.showOnClick = 0;
//listMenu.hideOnClick = true;
//listMenu.animInSpeed = 0.2;
//listMenu.animOutSpeed = 0.2;


// Now the fun part... animation! This script supports animation plugins you
// can add to each menu object you create. I have provided 3 to get you started.
// To enable animation, add one or more functions to the menuObject.animations
// array; available animations are:
//  * FSMenu.animSwipeDown is a "swipe" animation that sweeps the menu down.
//  * FSMenu.animFade is an alpha fading animation using tranparency.
//  * FSMenu.animClipDown is a "blind" animation similar to 'Swipe'.
// They are listed inside the "fsmenu.js" file for you to modify and extend :).

// I'm applying two at once to listMenu. Delete this to disable!
listMenu.animations[listMenu.animations.length] = FSMenu.animFade;
listMenu.animations[listMenu.animations.length] = FSMenu.animSwipeDown;
//listMenu.animations[listMenu.animations.length] = FSMenu.animClipDown;


// Finally, on page load you have to activate the menu by calling its 'activateMenu()' method.
// I've provided an "addEvent" method that lets you easily run page events across browsers.
// You pass the activateMenu() function two parameters:
//  (1) The ID of the outermost <ul> list tag containing your menu data.
//  (2) A node containing your submenu popout arrow indicator.
// If none of that made sense, just cut and paste this next bit for each menu you create.

var arrow = null;
if (document.createElement && document.documentElement)
{
 arrow = document.createElement('span');
 arrow.appendChild(document.createTextNode('>'));
 // Feel free to replace the above two lines with these for a small arrow image...
 //arrow = document.createElement('img');
 //arrow.src = 'arrow.gif';
 //arrow.style.borderWidth = '0';
 arrow.className = 'subind';
}
addReadyEvent(new Function('listMenu.activateMenu("listMenuRoot", arrow)'));

// Helps with swapping background images on mouseover in IE. Not needed otherwise.
//if (document.execCommand) document.execCommand("BackgroundImageCache", false, true);

// You may wish to leave your menu as a visible list initially, then apply its style
// dynamically on activation for better accessibility. Screenreaders and older browsers will
// then see all your menu data, but there will be a 'flicker' of the raw list before the
// page has completely loaded. If you want to do this, remove the CLASS="..." attribute from
// the above outermost UL tag, and uncomment this line:
//addReadyEvent(new Function('getRef("listMenuRoot").className="menulist"'));


// TO CREATE MULTIPLE MENUS:
// 1) Duplicate the <ul> menu data and this <script> element.
// 2) In the <ul> change id="listMenuRoot" to id="otherMenuRoot".
// 3) In the <script> change each instance of "listMenu" to "otherMenu"
// 4) In the addReadyEvent line above ensure "otherMenuRoot" is activated.
// Repeat as necessary with a unique name for each menu you want.
// You can also give each a unique CLASS and apply multiple stylesheets
// for different menu appearances/layouts, consult a CSS reference on this.

//]]>
</script>

<!-- ------------------------------------------------------------------------------------------------ -->
</body>                       
                </div>