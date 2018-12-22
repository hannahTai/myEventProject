<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.EVENT_TITLE.model.*"%>

<% 
	EventTitleService eventTitleService = new EventTitleService();
	List<EventTitleVO> list = eventTitleService.getAll();
	pageContext.setAttribute("eventTitleList", list); 
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>瀏覽所有活動</title>
<!-- Basic -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<!-- dataTables -->
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
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
		<a href="<%=request.getContextPath()%>/back-end/EVENT_TITLE/addEventTitle.jsp" class="btn btn-primary">新增活動主題</a>
			
		<form method="post" action="<%=request.getContextPath()%>/EVENT_TITLE/EventTitleServlet.do">
			<table id="eventTitleListTable" class="display" style="width: 100%">
				<thead>
					<tr>
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
						<tr>													
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
								<div data-inline="true">
									<button style="display: inline" type="submit" class="btn btn-info btn-sm" name="action" value="getOneEventTitle_For_Display_${eventTitleVO.evetit_no}">瀏覽</button>								
									<button style="display: inline" type="submit" class="btn btn-warning btn-sm" name="action" value="getOneEventTitle_For_Update_${eventTitleVO.evetit_no}">修改</button>							
									<button style="display: inline" type="submit" class="btn btn-danger btn-sm" name="action" value="deleteEventTitle_${eventTitleVO.evetit_no}">刪除</button>				
								</div>					
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
		
	</div>
	
	<!-- Basic -->
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- dataTables -->
	<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
	<script>
		$(document).ready(function() {
			$('#eventTitleListTable').DataTable();		
		});
	</script>
</body>

</html>