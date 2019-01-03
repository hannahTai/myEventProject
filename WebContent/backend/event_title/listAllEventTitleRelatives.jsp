<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.event_title.model.*"%>
<%@ page import="com.event.model.*"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">


<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>活動管理</title>
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
		<span class="text-danger">${eventTitleErrorMsgs.evetit_no}</span>
		<span class="text-danger">${eventTitleErrorMsgs.Exception}</span>
		<span class="text-danger">${eventErrorMsgs.Exception}</span>
	</div>
		
		
		
		
		
		
		
	
	
		
<!-- --------------------活動主題---------------------------------------------------------------------------------------------------- -->
		
<% 
	EventTitleService eventTitleService = new EventTitleService();
	List<EventTitleVO> list = eventTitleService.getAll();
	pageContext.setAttribute("eventTitleList", list); 
%>
		
	<div class="container">
		<div class="panel-group">		
			<div class="panel panel-info">
				<div class="panel-heading" data-toggle="collapse" href="#toggleEventTitle" aria-expanded="false">
					<h3 class="panel-title">活動主題</h3>				
				</div>			
				<div class="panel-collapse collapse in" id="toggleEventTitle" style="margin:15px;">
					<a href="<%=request.getContextPath()%>/backend/event_title/addEventTitle.jsp" class="btn btn-primary" style="margin-bottom:10px;">新增活動主題</a>
					<table id="eventTitleListTable" class="display" style="width:100%;">
						<thead>
							<tr>
								<th>編號</th>
								<th>分類</th>
								<th>活動主題名稱</th>
								<th>場次<br>數量</th>
								<th>狀態</th>
								<th>上架日期</th>
								<th>下架日期</th>
								<th>推廣<br>熱度</th>
								<th>動作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="eventTitleVO" items="${eventTitleList}">
								<tr class="${(eventTitleVO.evetit_no==param.evetit_no) ? 'selected':''}">													
									<td>${eventTitleVO.evetit_no}</td>
									<td>
										<jsp:useBean id="eventClassificationService" scope="page" class="com.event_classification.model.EventClassificationService" />
										${eventClassificationService.getOneEventClassification(eventTitleVO.eveclass_no).eveclass_name}
			                        </td>
									<td>${eventTitleVO.evetit_name}</td>
									<td>${eventTitleVO.evetit_sessions}</td>
									<td>
										${(eventTitleVO.evetit_status == "confirmed") ? '確認' : '' }
										${(eventTitleVO.evetit_status == "temporary") ? '暫存' : '' }
										${(eventTitleVO.evetit_status == "cancel") ? '取消' : '' }
									</td>
									<td>${eventTitleVO.launchdate}</td>
									<td>${eventTitleVO.offdate}</td>
									<td>${eventTitleVO.promotionranking}</td>
									<td>
										<div>
											<form method="post" action="<%=request.getContextPath()%>/event_title/EventTitleServlet.do" class="actionForm">								
											    <input type="hidden" name="evetit_no"         value="${eventTitleVO.evetit_no}">
											    <input type="hidden" name="requestURL"	      value="<%=request.getServletPath()%>">
											    <input type="hidden" name="action"	          value="listEvents_ByEventTitle">
											    <input type="submit" value="查看" class="btn btn-basic btn-sm" onclick="reloadEvent(this)"> 							
											</form>
											<form method="post" action="<%=request.getContextPath()%>/event_title/EventTitleServlet.do" class="actionForm" target="_blank">								
											    <input type="hidden" name="evetit_no"         value="${eventTitleVO.evetit_no}">
											    <input type="hidden" name="requestURL"	      value="<%=request.getServletPath()%>">
											    <input type="hidden" name="return_evetit_no"  value="${param.evetit_no}">
											    <input type="hidden" name="action"	          value="getOneEventTitle_For_Display">
											    <input type="submit" value="瀏覽" class="btn btn-info btn-sm"> 							
											</form>
											<form method="post" action="<%=request.getContextPath()%>/event_title/EventTitleServlet.do" class="actionForm" target="_blank">								
											    <input type="hidden" name="evetit_no"         value="${eventTitleVO.evetit_no}">
											    <input type="hidden" name="requestURL"	      value="<%=request.getServletPath()%>">
											    <input type="hidden" name="return_evetit_no"  value="${param.evetit_no}">
											    <input type="hidden" name="action"	          value="getOneEventTitle_For_Update">
											    <input type="submit" value="修改" class="btn btn-warning btn-sm"> 							
											</form>		
										</div>					
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>	
				</div>
			</div>
		</div>		
	</div>
	
	
	
	
	
	




<!-- --------------------活動場次---------------------------------------------------------------------------------------------------- -->

<%		
	String evetit_no = request.getParameter("evetit_no");
	Set<EventVO> set = eventTitleService.getEventsByEventTitle(evetit_no);
	pageContext.setAttribute("listEvents_ByEventTitle", set);
%>

	<div class="container">	
		<div class="panel-group">
			<div class="panel panel-info">
				<div class="panel-heading" data-toggle="collapse" href="#toggleEvent" aria-expanded="false">
					<h3 class="panel-title">活動場次</h3>				
				</div>			
				<div class="panel-collapse collapse in" id="toggleEvent" style="margin:15px;">
					<c:if test="${not empty param.evetit_no}">
						<a href="<%=request.getContextPath()%>/event/EventServlet.do?action=addEvent&evetit_no=${param.evetit_no}" class="btn btn-primary" style="margin-bottom:10px;">新增活動場次</a>	
					</c:if>
					<c:if test="${not empty listEvents_ByEventTitle}">		
						<table id="eventListTable" class="display" style="width: 100%">
							<thead>
								<tr>
									<th>編號</th>
									<th>場次名稱</th>
									<th>場地名稱</th>
									<th>售票開始時間</th>
									<th>售票結束時間</th>
									<th>狀態</th>
									<th>動作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="eventVO" items="${listEvents_ByEventTitle}">
									<tr class="${(eventVO.eve_no==param.eve_no) ? 'selected':''}">													
										<td>${eventVO.eve_no}</td>
										<td>${eventVO.eve_sessionname}</td>
										<td>
											<jsp:useBean id="venueService" scope="page" class="com.venue.model.VenueService" />
											${venueService.getOneVenue(eventVO.venue_no).venue_name}
										</td>
										<td><fmt:formatDate value="${eventVO.eve_onsaledate}" pattern="yyyy-MM-dd HH:mm"/></td>
										<td><fmt:formatDate value="${eventVO.eve_offsaledate}" pattern="yyyy-MM-dd HH:mm"/></td>
										<td>
											${(eventVO.eve_status == "normal") ? '正常' : '' }
											${(eventVO.eve_status == "cancel") ? '取消' : '' }
										</td>
										<th>
											<div>
												<form method="post" action="<%=request.getContextPath()%>/event/EventServlet.do" class="actionForm" target="_blank">								
												    <input type="hidden" name="eve_no"         value="${eventVO.eve_no}">
												    <input type="hidden" name="requestURL"	   value="<%=request.getServletPath()%>">
												    <input type="hidden" name="return_eve_no"  value="${param.eve_no}">
												    <input type="hidden" name="action"	       value="getOneEvent_For_Display">
												    <input type="submit" value="瀏覽" class="btn btn-info btn-sm"> 							
												</form>
												<form method="post" action="<%=request.getContextPath()%>/event/EventServlet.do" class="actionForm" target="_blank">								
												    <input type="hidden" name="eve_no"            value="${eventVO.eve_no}">
												    <input type="hidden" name="requestURL"	      value="<%=request.getServletPath()%>">
												    <input type="hidden" name="return_eve_no"  	  value="${param.eve_no}">
												    <input type="hidden" name="action"	          value="getOneEvent_For_Update">
												    <input type="submit" value="修改" class="btn btn-warning btn-sm"> 							
												</form>		
												<form method="post" action="<%=request.getContextPath()%>/event/EventServlet.do" class="actionForm">	
													<input type="hidden" name="eve_no"      value="${eventVO.eve_no}">															 
												    <input type="hidden" name="evetit_no"   value="${eventVO.evetit_no}">
												    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
												    <input type="hidden" name="eventWhichPage"	value="${param.eventWhichPage}">
												    <input type="hidden" name="action"	    value="deleteEvent">
												    <input type="submit" value="刪除" class="btn btn-danger btn-sm" onClick="getEventWhichPage(this)">	
												</form>
											</div>
										</th>
									</tr>
								</c:forEach>
							</tbody>
						</table>	
					</c:if>		
				</div>
			</div>
		</div>			
	</div>

	
		
	<!-- Basic -->
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- dataTables -->
	<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
	<script src="https://cdn.datatables.net/plug-ins/1.10.19/api/page.jumpToData().js"></script>
	<script>
		var eventTitleListTable;
		var eventListTable;
	
		$(document).ready(function() {			
			
			//--------------------活動主題----------------------------------------------------------------------------------------------------
			eventTitleListTable = $("#eventTitleListTable").DataTable({
				stateSave: true,
				stateSaveCallback: function(settings,data) {
					localStorage.setItem( 'DataTables_' + settings.sInstance, JSON.stringify(data) )
				},
				stateLoadCallback: function(settings) {
					return JSON.parse( localStorage.getItem( 'DataTables_' + settings.sInstance ) )					
				}
			});
	
			if($("input[name=return_evetit_no]").val() != null) {
				eventTitleListTable.page.jumpToData( $("input[name=return_evetit_no]").val(), 0 );
			}	
			
			
			
			//--------------------活動場次----------------------------------------------------------------------------------------------------
			eventListTable = $("#eventListTable").DataTable({
				stateSave: true,
				stateSaveCallback: function(settings,data) {
					localStorage.setItem( 'DataTables_' + settings.sInstance, JSON.stringify(data) )
				},
				stateLoadCallback: function(settings) {
					return JSON.parse( localStorage.getItem( 'DataTables_' + settings.sInstance ) )					
				}
			});	
			
			var eventWhichPage = parseInt($("input[name=eventWhichPage]").val());
			if(eventListTable.page.info() != null){
				var pages = eventListTable.page.info().pages;
			}
					
			if(eventWhichPage === pages){
				eventListTable.page( eventWhichPage - 1 ).draw( 'page' );
			} else {
				if($("input[name=return_eve_no]").val() != null){
					eventListTable.page.jumpToData( $("input[name=return_eve_no]").val(), 0 );						
				}		
			}				
		});
		
		
		
		function reloadEvent(e){
			localStorage.removeItem("DataTables_eventListTable");
		}
		
		function getEventWhichPage(e){
			var info = eventListTable.page.info();
			$(e).prev().prev().val(info.page);
		}
	</script>
</body>

</html>