<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.event_title.model.*"%>

<% 
	EventTitleService eventTitleService = new EventTitleService();
	List<EventTitleVO> list = eventTitleService.getAll();
	pageContext.setAttribute("eventTitleList", list); 
%>

<jsp:useBean id="eventClassificationService" scope="page" class="com.event_classification.model.EventClassificationService" />

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">


<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title></title>
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
	</div>

	<div class="container">
		<a href="<%=request.getContextPath()%>/backend/event_title/addEventTitle.jsp" class="btn btn-primary">新增活動主題</a>
	</div>
		
	<div class="container">				
		<table id="eventTitleListTable" class="display" style="width: 100%">
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
							${(eventTitleVO.eveclass_no == "A") ? '演唱會' : '' }
							${(eventTitleVO.eveclass_no == "B") ? '音樂會' : '' }
							${(eventTitleVO.eveclass_no == "C") ? '音樂劇' : '' }
							${(eventTitleVO.eveclass_no == "D") ? '戲劇' : '' }
							${(eventTitleVO.eveclass_no == "E") ? '舞蹈' : '' }
							${(eventTitleVO.eveclass_no == "F") ? '展覽' : '' }
							${(eventTitleVO.eveclass_no == "G") ? '親子' : '' }
							${(eventTitleVO.eveclass_no == "H") ? '講座' : '' }
							${(eventTitleVO.eveclass_no == "I") ? '運動' : '' }
							${(eventTitleVO.eveclass_no == "J") ? '其他' : '' }
							<%-- ${eventClassificationService.getOneEventClassification(eventTitleVO.eveclass_no).eveclass_name} --%>
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
								<form method="post" action="<%=request.getContextPath()%>/event_title/EventTitleServlet.do" class="actionForm">																
								    <input type="hidden" name="evetit_no"   value="${eventTitleVO.evetit_no}">
								    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
								    <input type="hidden" name="whichPage"	value="${param.whichPage}">
								    <input type="hidden" name="action"	    value="deleteEventTitle">
								    <input type="submit" value="刪除" class="btn btn-danger btn-sm" onClick="getWhichPage(this)">	
								</form>
							</div>					
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
		var eventTitleListTable;
	
		$(document).ready(function() {			
			eventTitleListTable = $("#eventTitleListTable").DataTable({
				stateSave: true,
				stateSaveCallback: function(settings,data) {
					localStorage.setItem( 'DataTables_' + settings.sInstance, JSON.stringify(data) )
				},
				stateLoadCallback: function(settings) {
					return JSON.parse( localStorage.getItem( 'DataTables_' + settings.sInstance ) )					
				}
			});
			
			var whichPage = parseInt($("input[name=whichPage]").val());
			var pages = eventTitleListTable.page.info().pages;
		
			if(whichPage === pages){
				eventTitleListTable.page( whichPage - 1 ).draw( 'page' );
			} else {
				eventTitleListTable.page.jumpToData( $("input[name=return_evetit_no]").val(), 0 );				
			}				
		});
		
		function getWhichPage(e){
			var info = eventTitleListTable.page.info();
			$(e).prev().prev().val(info.page);
		}
	</script>
</body>

</html>