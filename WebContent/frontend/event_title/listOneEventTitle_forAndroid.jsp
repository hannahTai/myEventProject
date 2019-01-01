<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.event_title.model.*"%>
<%@ page import="com.event_title.controller.*"%>

<%
	String evetit_no = request.getParameter("evetit_no");

	EventTitleService eventTitleService = new EventTitleService();
	EventTitleVO aEventTitle = eventTitleService.getOneEventTitle(evetit_no);
	pageContext.setAttribute("aEventTitle", aEventTitle); 
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${aEventTitle.evetit_name}</title>
    <!-- Basic -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <style>
        .container img {
    	    width: 100%;
	    }
	    
	    .nav-tabs>li>a{
	    	padding:5px;
	    	font-size:10px;
	    }
    </style>
</head>

<body>
    <div class="container">
        <div class="col-xs-12 col-sm-8 col-sm-offset-2">
            <h5 id="evetit_name">${aEventTitle.evetit_name}</h5>
            <img src="<%= request.getContextPath()%>/event_title/EventTitleGifReader?scaleSize=850&evetit_no=${aEventTitle.evetit_no}" id="poster">
            <input type="hidden" name="evetit_no" id="evetit_no" value="${aEventTitle.evetit_no}">
            <div class="tabbable">
                <!-- 標籤面板：標籤區 -->
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#infoTab" data-toggle="tab">活動介紹</a></li>
                    <li><a href="#noticeTab" data-toggle="tab">注意事項</a></li>
                    <li><a href="#eticpurchaserulesTab" data-toggle="tab">購票提醒</a></li>
                    <li><a href="#eticrulesTab" data-toggle="tab">用票提醒</a></li>
                    <li><a href="#refundrulesTab" data-toggle="tab">退票說明</a></li>
                </ul>
                <!-- 標籤面板：內容區 -->
                <div class="tab-content">
                    <div class="tab-pane active" id="infoTab">${aEventTitle.info}</div>
                    <div class="tab-pane" id="noticeTab">${aEventTitle.notices}</div>
                    <div class="tab-pane" id="eticpurchaserulesTab">${aEventTitle.eticpurchaserules}</div>
                    <div class="tab-pane" id="eticrulesTab">${aEventTitle.eticrules}</div>
                    <div class="tab-pane" id="refundrulesTab">${aEventTitle.refundrules}</div>
                </div>
            </div>
        </div>
    </div>
    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>