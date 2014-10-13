<%@ include file="/WEB-INF/jsp/include.jsp" %>

<div class="templatemo_post" >

<script src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script>
      function initialize() {
        var map_canvas = document.getElementById('map_canvas');
        var map_options = {
          center: new google.maps.LatLng(44.4651528, 24.265096),
          zoom: 14,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        var map = new google.maps.Map(map_canvas, map_options)
      }
      google.maps.event.addDomListener(window, 'load', initialize);
</script>

    <div class="post_body">
    
    	<div id="map_canvas"></div>
    
    </div>

</div>