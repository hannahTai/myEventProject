<%@page import="com.ticket_type.model.TicketTypeVO"%>
<%@page import="com.ticket_type.model.TicketTypeJDBCDAO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	TicketTypeJDBCDAO dao = new TicketTypeJDBCDAO();
	List<TicketTypeVO> list = dao.getAll();
	pageContext.setAttribute("theList", list);
%>


<html>
<head>
<title></title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white'>
	<table>
		<tr>
			<th>tictype_no</th>
			<th>eve_no</th>
			<th>tictype_ename</th>
			<th>tictype_color</th>
			<th>tictype_name</th>
			<th>tictype_price</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="empVO" items="${theList}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">
			<tr>
				<td>${empVO.tictype_no}</td>
				<td>${empVO.eve_no}</td>
				<td>${empVO.tictype_ename}</td>
				<td>${empVO.tictype_color}</td>
				<td>${empVO.tictype_name}</td>
				<td>${empVO.tictype_price}</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>
</body>
</html>