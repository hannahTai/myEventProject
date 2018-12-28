<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.VENUE2.model.*"%>

<% 
	VenueService venueService = new VenueService();
	List<VenueVO> list = venueService.getAll();
	pageContext.setAttribute("venueList", list); 
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>瀏覽所有場地</title>
<!-- Basic -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<!-- dataTables -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
</head>

<style>
.actionForm{
	display: inline;
}
</style>

<body>

	<div class="container">
		<%-- 錯誤表列 --%>
		<c:if test="${not empty venueErrorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${venueErrorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
	</div>
	
	<div class="container">
		<a href="<%=request.getContextPath()%>/back-end/Venue/addVenue.jsp" class="btn btn-primary">新增場地</a>	
	</div>

	<div class="container">		
		<table id="venueTable" class="display" style="width: 100%">
			<thead>
				<tr>
					<th>編號</th>
					<th>場地名稱</th>
					<th>地址</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="venueVO" items="${venueList}">
					<tr>	
						<td>${venueVO.venue_no}</td>												
						<td>${venueVO.venue_name}</td>
						<td>${venueVO.address}</td>
						<td>						
							<form method="post" action="<%=request.getContextPath()%>/VENUE/VenueServlet.do" class="actionForm">
								<input type="hidden" name="venue_no" value="${venueVO.venue_no}">
			    		 		<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
								<button type="submit" class="btn btn-info btn-sm" name="action" value="getOneVenue_For_Display">瀏覽</button>								
							</form>	
							<form method="post" action="<%=request.getContextPath()%>/VENUE/VenueServlet.do" class="actionForm">
								<input type="hidden" name="venue_no" value="${venueVO.venue_no}">
			    		 		<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">	
								<button type="submit" class="btn btn-warning btn-sm" name="action" value="getOneVenue_For_Update">修改</button>							
							</form>
							<form method="post" action="<%=request.getContextPath()%>/VENUE/VenueServlet.do" class="actionForm">
								<input type="hidden" name="venue_no" value="${venueVO.venue_no}">
			    		 		<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
								<button type="submit" class="btn btn-danger btn-sm" name="action" value="deleteVenue">刪除</button>				
							</form>				
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>		
	</div>
	
	<!-- Basic -->
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- dataTables -->
	<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
	<script>
		$(document).ready(function() {
			$('#venueTable').DataTable();		
		});
	</script>
</body>

</html>