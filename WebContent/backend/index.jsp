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
</head>
<body>

	<br><br><br>
	<div class="container">	
		<a href="<%=request.getContextPath()%>/backend/event_title/listAllEventTitleRelatives.jsp">活動管理</a><br>	
		
		<a href="<%=request.getContextPath()%>/backend/venue/listAllVenue.jsp">場地管理</a><br>	
		
		<a href="<%=request.getContextPath()%>/backend/advertisement/listAllAdvertisement.jsp">廣告管理</a><br>	
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