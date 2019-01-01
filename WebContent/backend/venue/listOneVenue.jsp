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
        .container img {
     	   width: 100%;
    	}
    	.actionForm{
			display: inline;
		}
    </style>
</head>

<body>



	<div class="container">
		<span class="text-danger">${venueErrorMsgs.venue_no}</span>
		<span class="text-danger">${venueErrorMsgs.Exception}</span>
	</div>



    	<div class="container">
    	
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
              
			<div class="col-xs-12 col-sm-12 text-right">	            	
				<span class="form-group">
					<a class="btn btn-info" href="<%=request.getContextPath()%>/backend/venue/listAllVenue.jsp?venue_no=${venueVO.venue_no}">回到場地總覽</a>
				</span>
				<form method="post" action="<%=request.getContextPath()%>/venue/VenueServlet.do" class="actionForm">								
					<input type="hidden" name="venue_no"    value="${venueVO.venue_no}">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
					<input type="hidden" name="action"	    value="getOneVenue_For_Update">
					<input type="submit" value="修改" class="btn btn-warning"> 							
				</form>	
			</div>
                
			<div class="tabbable">
                <!-- 標籤面板：標籤區 -->
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#infoTab" data-toggle="tab">交通資訊</a></li>
                    <li><a href="#picTab" data-toggle="tab">位置圖</a></li>
                    <li><a href="#googleMapTab" data-toggle="tab">Google Map</a></li>
                </ul>
                <!-- 標籤面板：內容區 -->
                <div class="tab-content">
                    <div class="tab-pane active" id="infoTab">
						${venueVO.venue_info} 
                    </div>
                    <div class="tab-pane" id="picTab">
           				 <img src="<%= request.getContextPath()%>/venue/VenueGifReader?scaleSize=1200&venue_no=${venueVO.venue_no}">      
                    </div>
                    <div class="tab-pane" id="googleMapTab">
           				
                    </div>
                </div>
            </div>
                      
        </div>

    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    
	<!-- Google Map -->
</body>

</html>