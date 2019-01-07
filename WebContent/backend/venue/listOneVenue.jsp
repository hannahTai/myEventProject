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



	<jsp:include page="/backend/navbar_back-end.html" flush="true" />




	<div class="container">
		<span class="text-danger">${venueErrorMsgs.venue_no}</span>
		<span class="text-danger">${venueErrorMsgs.Exception}</span>
	</div>



    	<div class="container" style="margin-bottom:30px;">
    	
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
				                <label for="venue_name">場地編號</label>
				                <input type="text" name="venue_no" id="venue_no" class="form-control" value="${venueVO.venue_no}" readonly>
				            </div>
				            
				            <div class="form-group">
				                <label for="venue_name">場地名稱</label>
				                <input type="text" name="venue_name" id="venue_name" class="form-control" value="${venueVO.venue_name}" readonly>
				            </div>
				            
				            <div class="form-group">
				                <label for="address">地址</label>
				                <input type="text" name="address" id="address" class="form-control" value="${venueVO.address}" readonly>
				            </div>
				                        
				            <div class="row">
				                <div class="col-xs-12 col-sm-6">
				                    <div class="form-group">
				                        <label for="latitude">緯度</label>
				                        <input type="text" id="latitude" name="latitude" class="form-control" value="${venueVO.latitude}" readonly>
				                    </div>
				                </div>
				                <div class="col-xs-12 col-sm-6">
				                    <div class="form-group">
				                        <label for="longitude">經度</label>
				                        <input type="text" id="longitude" name="longitude" class="form-control" value="${venueVO.longitude}" readonly> 
				                    </div>
				                </div>
				            </div>
				            <div id="map"></div>     
	                    </div>
	                    
	                    <div class="tab-pane" id="infoTab">
							${venueVO.venue_info} 
	                    </div>
	                    
	                    <div class="tab-pane" id="picTab">
	                    	<div class="form-group" style="margin-bottom:10px;">
	           					<img src="<%= request.getContextPath()%>/venue/VenueGifReader?scaleSize=800&venue_no=${venueVO.venue_no}">      
	                   		</div>
	                    </div>
	                </div>
	            </div>
            </div>
  
			<div class="col-xs-12 col-sm-12" style="margin-top:15px;">	            	
				<form method="post" action="<%=request.getContextPath()%>/venue/VenueServlet.do" class="actionForm">								
					<input type="hidden" name="venue_no"    value="${venueVO.venue_no}">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
					<input type="hidden" name="action"	    value="getOneVenue_For_Update">
					<input type="submit" value="修改" class="btn btn-warning"> 							
				</form>	
				<span class="form-group">
					<a class="btn btn-info" href="<%=request.getContextPath()%>/backend/venue/listAllVenue.jsp?venue_no=${venueVO.venue_no}">回場地總覽</a>
				</span>
			</div>
                      
        </div>

    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    
	<!-- Google Map -->
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD5W5_78W5LzdktsSjTuDPJuUYDY3Qjy9U&callback=initMap"></script>
    
    <script>
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
    }
    
    function latlng(lat, lng){
    	this.lat = parseFloat(lat);
    	this.lng = parseFloat(lng);
    }

	</script>
</body>

</html>