<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="com.venue.model.*"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${venueVO.venue_name}</title>
    <!-- Basic -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <style>
    	.actionForm{
			display: inline;
		}
		
		#map {
	        height: 500px;
	        width: 100%;
	        margin-top: 15px;
	        border-radius: 25px;
      	}
      	body{
			font-family:微軟正黑體!important;
		}
    </style>
</head>

<body>



	<jsp:include page="/backend/navbar_back-end.jsp" flush="true" />




	<div class="container">
		<span class="text-danger">${venueErrorMsgs.venue_no}</span>
		<span class="text-danger">${venueErrorMsgs.Exception}</span>
	</div>



	<div class="container" style="margin-bottom:30px;">
		<form method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/venue/VenueServlet.do">
            <div class="col-xs-12 col-sm-12">    
				<div class="tabbable">
	                <!-- 標籤面板：標籤區 -->
	                <ul class="nav nav-tabs" style="margin-bottom:10px;">
	                	<li class="active"><a href="#googleMapTab" data-toggle="tab">場地位置</a></li>
	                    <li><a href="#infoTab" data-toggle="tab">交通資訊</a></li>
	                    <li><a href="#picTab" data-toggle="tab">位置圖</a></li>
	                </ul>
	                <!-- 標籤面板：內容區 -->
	                <div class="tab-content">
	                	
	                	<div class="tab-pane active" id="googleMapTab">
	           				<div class="form-group">
				                <label for="venue_no">場地編號</label>
				                <input type="text" name="venue_no" id="venue_no" class="form-control" value="${venueVO.venue_no}" readonly>
				            </div>
				            
				            <div class="form-group">
				                <label for="venue_name">場地名稱</label>
				                <span class="text-danger">${venueErrorMsgs.venue_name}</span>
				                <input type="text" name="venue_name" id="venue_name" class="form-control" value="${venueVO.venue_name}" placeholder="請輸入場地名稱">
				                <button type="button" class="btn btn-primary" id="searchAddress" style="float:right;">點我搜尋</button>
				            </div>
				            
				            <div class="form-group">
				                <label for="address">地址</label>
				                <span class="text-danger">${venueErrorMsgs.address}</span>
				                <input type="text" name="address" id="address" class="form-control" value="${venueVO.address}">
				            </div>
				                        
				            <div class="row">
				                <div class="col-xs-12 col-sm-6">
				                    <div class="form-group">
				                        <label for="latitude">緯度</label>
				                        <span class="text-danger">${venueErrorMsgs.latitude}</span>
				                        <input type="text" id="latitude" name="latitude" class="form-control" value="${venueVO.latitude}" readonly>
				                    </div>
				                </div>
				                <div class="col-xs-12 col-sm-6">
				                    <div class="form-group">
				                        <label for="longitude">經度</label>
				                        <span class="text-danger">${venueErrorMsgs.longitude}</span>
				                        <input type="text" id="longitude" name="longitude" class="form-control" value="${venueVO.longitude}" readonly> 
				                    </div>
				                </div>
				            </div>
				            <div id="map"></div>     
	                    </div>
	                    
	                    <div class="tab-pane" id="infoTab">
							<textarea name="venue_info" id="infoEditor">
		                		${venueVO.venue_info} 
		               		</textarea>
	                    </div>
	                    
	                    <div class="tab-pane" id="picTab">
           				 	<div class="form-group">
								<label for="venue_locationPic">場地位置圖</label>
								<input type="file" id="venue_locationPic" name="venue_locationPic" class="form-control" accept="image/*" style="margin-bottom:10px;">
								<input type="hidden" id="venue_locationPic_status" name="venue_locationPic_status" value="${(venue_locationPic_status == 'alreadyUpload') ? 'alreadyUpload' : 'noUpload'}">
								<c:if test="${venue_locationPic_status != 'alreadyUpload'}">
				                	<img src="<%= request.getContextPath()%>/venue/VenueGifReader?scaleSize=1000&venue_no=${venueVO.venue_no}" id="venue_locationPic_preview">
				                </c:if>
								<c:if test="${venue_locationPic_status == 'alreadyUpload'}">
				                	<img src="${venue_locationPic_path}" id="venue_locationPic_preview">
				                </c:if>
							</div>      
	                    </div>
	                </div>
	            </div>
            </div>
  
			<div class="col-xs-12 col-sm-12" style="margin-top:15px;">	            	
	  			<span class="form-group">
	  				<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
					<button type="submit" class="btn btn-success" name="action" value="updateVenue">儲存</button>
					<a class="btn btn-info" href="<%=request.getContextPath()%>/backend/venue/listAllVenue.jsp?venue_no=${venueVO.venue_no}">回場地總覽</a>
				</span>	
			</div>
		</form>               
	</div>

    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

	
	<!-- ckEditor JS -->
    <script src="<%=request.getContextPath()%>/vendor/ckeditor_easyImage_final/ckeditor.js"></script>
    <script src="<%=request.getContextPath()%>/backend/venue/js/venueCKEditor.js"></script>
    
    <script>
    $(function() {
    	
    	initInfoEditor();
      
        $("#venue_locationPic").change(function() {
            imagesPreview(this);
            $("#venue_locationPic_status").attr("value", "yesUpload");
        });
             
        $(".text-danger").each(function(){
        	var errorMsg = $(this).text();
        	if( errorMsg.trim() != "" ){
        		$(this).prepend("<i class='glyphicon glyphicon-triangle-left'></i>");
        	}        	
        });
    });
    
    function initMap() {
    	
    	var latitude = $("#latitude").val();
    	var longitude = $("#longitude").val();
    	var address = $("#address").val();
    	var encodeURIaddress = encodeURIComponent(address);
    	
    	var contentString = $("#venue_name").val() + "<br>" + address;
		var latlngObj = new latlng(latitude, longitude);
        
        var toGoogleMapWebsiteLink = "<a href='https://www.google.com/maps/search/?api=1&query=" + encodeURIaddress + "' target='_blank'>" + contentString + "</a>";
        var infowindow = new google.maps.InfoWindow({
            content: toGoogleMapWebsiteLink
        });

        var map = new google.maps.Map(document.getElementById('map'), { zoom: 14, center: latlngObj });
        var marker = new google.maps.Marker({ position: latlngObj, map: map });

        infowindow.open(map, marker);
        
        var geocoder = new google.maps.Geocoder();
        document.getElementById('searchAddress').addEventListener('click', function() {
            geocodeAddress(geocoder, map);
        });
    }
    
    function latlng(lat, lng){
    	this.lat = parseFloat(lat);
    	this.lng = parseFloat(lng);
    }
    
    function geocodeAddress(geocoder, resultsMap) {
        var address = document.getElementById('venue_name').value;
        
        if(address.trim().length == 0){
        	window.alert("請輸入場地名稱");
        	return;
        }     
        
        geocoder.geocode({ 'address': address }, function(results, status) {
            if (status === 'OK') {
                resultsMap.setCenter(results[0].geometry.location);
                
                var lat = results[0].geometry.location.lat();
                var lng = results[0].geometry.location.lng();
                var formatted_address = results[0].formatted_address;
                var encodeURIaddress = encodeURIComponent(formatted_address);
                
            	$("#latitude").val(lat);
            	$("#longitude").val(lng);
            	$("#address").val(formatted_address);

                var marker = new google.maps.Marker({
                    map: resultsMap,
                    position: results[0].geometry.location
                });

                var contentString = $("#venue_name").val() + "<br>" + formatted_address;
                var toGoogleMapWebsiteLink = "<a href='https://www.google.com/maps/search/?api=1&query=" + encodeURIaddress + "' target='_blank'>" + contentString + "</a>";
                var infowindow = new google.maps.InfoWindow({
                    content: toGoogleMapWebsiteLink
                });
                infowindow.open(map, marker);

            } else {
                alert('Geocode was not successful for the following reason: ' + status);
            }
        });
    }
    
    function imagesPreview(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $("#venue_locationPic_preview").attr("src", e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

	</script>

	<!-- Google Map -->
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD5W5_78W5LzdktsSjTuDPJuUYDY3Qjy9U&callback=initMap"></script>
	
</body>

</html>