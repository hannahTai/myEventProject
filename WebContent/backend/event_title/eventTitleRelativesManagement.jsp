<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>活動管理</title>
    <!-- Basic -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <style>
 		body{
			font-family:微軟正黑體!important;
		}
		.thumbnail img {
			opacity: 0.6;
		}
		.thumbnail img:hover {
			opacity: 1;
		}
    </style>
</head>
<body>



	<jsp:include page="/backend/navbar_back-end.jsp" flush="true" />


		
    <div class="container" style="margin-top:75px;">
        <div class="row flex-row">
            <div class="col-xs-12 col-sm-4">
                <div class="thumbnail">
                    <a href="<%=request.getContextPath()%>/backend/event_title/listAllEventTitleRelatives.jsp" target="_blank">
                    	<img src="<%=request.getContextPath()%>/backend/event_title/images/eventManagement.jpg">
                    	<div class="caption">
                        	<h4>活動管理<h4>
                   		</div>
                    </a>
                </div>
            </div>
            <div class="col-xs-12 col-sm-4">
                <div class="thumbnail">
                    <a href="<%=request.getContextPath()%>/backend/venue/listAllVenue.jsp" target="_blank">
                    	<img src="<%=request.getContextPath()%>/backend/event_title/images/venueManagement.jpg">
                    	<div class="caption">
                        	<h4>場地管理<h4>
                   		</div>
                    </a>
                </div>
            </div>
            <div class="col-xs-12 col-sm-4">
                <div class="thumbnail">
                    <a href="<%=request.getContextPath()%>/backend/advertisement/listAllAdvertisement.jsp" target="_blank">
                    	<img src="<%=request.getContextPath()%>/backend/event_title/images/advertisementManagement.jpg">
                    	<div class="caption">
                        	<h4>廣告管理<h4>
                   		</div>
                    </a>
                </div>
            </div>
            <div class="col-xs-12 col-sm-4">
                <div class="thumbnail">
                    <a href="#" target="_blank">
                    	<img src="<%=request.getContextPath()%>/backend/event_title/images/changeNotices.jpg">
                    	<div class="caption">
                        	<h4>異動通知<h4>
                   		</div>
                    </a>
                </div>
            </div>
        </div>
    </div>

	
	
	<!-- Basic -->
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script>
	$(document).ready(function() {
		localStorage.removeItem("DataTables_eventTitleListTable");
		localStorage.removeItem("DataTables_venueListTable");
		localStorage.removeItem("DataTables_advertisementListTable");
	});
	</script>



</body>
</html>