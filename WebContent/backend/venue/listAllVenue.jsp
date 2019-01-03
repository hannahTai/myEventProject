<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.venue.model.*"%>

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
<title>場地管理</title>
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
		<span class="text-danger">${venueErrorMsgs.venue_no}</span>
		<span class="text-danger">${venueErrorMsgs.Exception}</span>
	</div>
	
	<div class="container">
		<a href="<%=request.getContextPath()%>/backend/venue/addVenue.jsp" class="btn btn-primary" style="margin-bottom:10px;">新增場地</a>	
	</div>

	<div class="container">		
		<table id="venueListTable" class="display" style="width:100%;">
			<thead>
				<tr>
					<th>編號</th>
					<th>場地名稱</th>
					<th>地址</th>
					<th>動作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="venueVO" items="${venueList}">
					<tr class="${(venueVO.venue_no==param.venue_no) ? 'selected':''}">	
						<td>${venueVO.venue_no}</td>												
						<td>${venueVO.venue_name}</td>
						<td>${venueVO.address}</td>
						<td>						
							<form method="post" action="<%=request.getContextPath()%>/venue/VenueServlet.do" class="actionForm">
								<input type="hidden" name="venue_no"         value="${venueVO.venue_no}">
			    		 		<input type="hidden" name="requestURL"       value="<%=request.getServletPath()%>">
			    		 		<input type="hidden" name="return_venue_no"  value="${param.venue_no}">
			    		 		<input type="hidden" name="action"           value="getOneVenue_For_Display">
								<input type="submit" value="瀏覽" class="btn btn-info btn-sm"> 	
							</form>	
							<form method="post" action="<%=request.getContextPath()%>/venue/VenueServlet.do" class="actionForm">
								<input type="hidden" name="venue_no"         value="${venueVO.venue_no}">
			    		 		<input type="hidden" name="requestURL"       value="<%=request.getServletPath()%>">
			    		 		<input type="hidden" name="return_venue_no"  value="${param.venue_no}">
			    		 		<input type="hidden" name="action"           value="getOneVenue_For_Update">
								<input type="submit" value="修改" class="btn btn-warning btn-sm">
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
	<script src="https://cdn.datatables.net/plug-ins/1.10.19/api/page.jumpToData().js"></script>
	<script>
		var venueListTable;
		
		$(document).ready(function() {
			venueListTable = $("#venueListTable").DataTable({
				stateSave: true,
				stateSaveCallback: function(settings,data) {
					localStorage.setItem( 'DataTables_' + settings.sInstance, JSON.stringify(data) )
				},
				stateLoadCallback: function(settings) {
					return JSON.parse( localStorage.getItem( 'DataTables_' + settings.sInstance ) )					
				}
			});
	
			if($("input[name=return_venue_no]").val() != null) {
				venueListTable.page.jumpToData( $("input[name=return_venue_no]").val(), 0 );
			}
		});
	</script>
</body>

</html>