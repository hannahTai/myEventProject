<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="com.VENUE.model.*"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${venueVO.venue_name}</title>
    <!-- Basic -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>

<body>

	<div class="container">
		<%-- 錯誤表列 --%>
		<c:if test="${not empty eventTitleErrorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${eventTitleErrorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
	</div>


    	<div class="container">
            
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
            
            <div class="form-group">
                <label for="venue_locationPic">場地位置圖</label>
                <div><img src="<%= request.getContextPath()%>/VENUE/VenueGifReader?scaleSize=400&venue_no=${venueVO.venue_no}">
            	</div>
            </div>      
             
            
            
            <div class="form-group">
                <label for="venue_name">場地資訊</label>
                <div id="venue_info">${venueVO.venue_info}</div>
            </div>
            
        </div>

    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>