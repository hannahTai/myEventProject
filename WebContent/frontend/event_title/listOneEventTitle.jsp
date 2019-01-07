<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.event_title.model.*"%>
<%@ page import="com.event_title.controller.*"%>
<%@ page import="com.event.model.*"%>
<%@ page import="com.event.controller.*"%>

<%
	String evetit_no = request.getParameter("evetit_no");

	EventTitleService eventTitleService = new EventTitleService();
	EventTitleVO aEventTitle = eventTitleService.getOneEventTitle(evetit_no);
	pageContext.setAttribute("aEventTitle", aEventTitle); 
%>

<%		
	Set<EventVO> set = eventTitleService.getEventsByEventTitle(evetit_no);
	pageContext.setAttribute("listEvents_ByEventTitle", set);
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
        #poster {
    	    width: 100%;
	    }
	    .glyphicon {
	    	cursor: pointer;
	    }
		body {
			font-family:微軟正黑體!important;
		}	    
    </style>
</head>

<body>



<!-- for test only ::: favorite event member -->
<input type="hidden" name="member_no" id="member_no" value="M000013">
<!-- for test only ::: favorite event member -->



	<jsp:include page="/frontend/navbar_front-end.html" flush="true" />



    <div class="container" style="margin-bottom:30px;">
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-9">
                <h3 id="evetit_name">${aEventTitle.evetit_name}</h3>
                <input type="hidden" name="evetit_no" id="evetit_no" value="${aEventTitle.evetit_no}">
                <input type="hidden" name="projectName" id="projectName" value="<%=request.getContextPath() %>">
            </div>
            <div id="toggleFavoriteEvent" class="pointer">
             <div class="col-xs-12 col-sm-12 col-md-3 text-right" style="color:red;">
                  <h3><i class="glyphicon glyphicon-heart-empty " id="clickFavoriteEvent">加入最愛</i></h3>
                  <input type="hidden" id="favoriteEventStatus" value="outTheFavoriteEvent">
             </div>
            </div>
        </div>
        <img src="<%= request.getContextPath()%>/event_title/EventTitleGifReader?scaleSize=850&evetit_no=${aEventTitle.evetit_no}" id="poster">
        <div>
            <input id="flip" type="button" value="查看活動場次" class="btn btn-primary" style="margin-top:15px;margin-bottom:15px;">
            <div id="panel" style="display:none;">
                <table class="table table-hover table-bordered">
                    <thead>
                        <tr class="info">
                            <th>開始時間</th>
                            <th>結束時間</th>
                            <th>地點</th>
                            <th>購買狀態</th>
                        </tr>
                    </thead>
                    <tbody>
						<c:forEach var="eventVO" items="${listEvents_ByEventTitle}">
							<tr>
								<td style="vertical-align:middle;">
									<fmt:formatDate value="${eventVO.eve_startdate}" pattern="yyyy-MM-dd (E) HH:mm"/>
								</td>
								<td style="vertical-align:middle;">
									<fmt:formatDate value="${eventVO.eve_enddate}" pattern="yyyy-MM-dd (E) HH:mm"/>
								</td>
								<td style="vertical-align:middle;">
									<jsp:useBean id="venueService" scope="page" class="com.venue.model.VenueService" />
									<input type="hidden" name="venue_no" value="${eventVO.venue_no}">
									<a href="<%=request.getContextPath() %>/frontend/venue/listOneVenue.jsp?venue_no=${eventVO.venue_no}" target="_blank">
										<i class="glyphicon glyphicon-info-sign">${venueService.getOneVenue(eventVO.venue_no).venue_name}</i> 
									</a>
								</td>
								<td style="vertical-align:middle;">
									<fmt:formatDate var="eve_onsaledate" value="${eventVO.eve_onsaledate}" pattern="yyyy-MM-dd HH:mm:ss"/>
									<fmt:formatDate var="eve_offsaledate" value="${eventVO.eve_offsaledate}" pattern="yyyy-MM-dd HH:mm:ss"/>
									<jsp:useBean id="today" class="java.util.Date"/>  
									<fmt:timeZone value="Asia/Tokyo">   
										<fmt:formatDate var="now" value="${today}" pattern="yyyy-MM-dd HH:mm:ss"/>
									</fmt:timeZone> 
									<c:if test="${now < eve_onsaledate}">
										<div class="btn btn-warning">尚未開賣</div>
									</c:if>
									<c:if test="${now > eve_onsaledate && now < eve_offsaledate}">
										<a href="#" target="_blank" class="btn btn-danger">售票中</a>${eventVO.eve_no}
									</c:if>
									<c:if test="${now > eve_offsaledate}">
										<div class="btn btn-default">已售完</div>
									</c:if>
								</td>
							</tr>
						</c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tabbable" style="border-color:pink;">
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
 
    
    
	<jsp:include page="/frontend/footer_front-end.html" flush="true" />

    
    
    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">   
    
    $(document).ready(function() {
    	
        $("#flip").click(function() {
            $("#panel").slideToggle("fast");
        });
        
        $("#toggleFavoriteEvent").click(function(){
        	// checkFavoriteEventData
        	var member_no = $("#member_no").val();
        	if(member_no.trim().length != 7){
        		window.alert("請先登入");
        		return;
        	}
        	var evetit_no = $("#evetit_no").val();
        	if(evetit_no.trim().length != 5){
        		window.alert("找不到活動主題");
        		return;
        	}
        	// deleteFavoriteEvent
        	if($("#favoriteEventStatus").val() == "inTheFavoriteEvent"){        		
        		var url = $("#projectName").val();
                url += '/favorite_event/FavoriteEventServlet.do';
                var data = '';
              	data += 'member_no=';
               	data += member_no;
               	data += '&evetit_no=';
               	data += evetit_no;
               	data += '&';
                data += 'action=deleteFavoriteEvent';
                $.ajax({
                    type: 'post',
                    url: url,
                    data: data,
                    success: function(data) {              	
                    	if(data.indexOf("成") != -1){
                    		$("#clickFavoriteEvent").toggleClass("glyphicon-heart glyphicon-heart-empty");
                    		$("#favoriteEventStatus").val("outTheFavoriteEvent");
                    		$("#clickFavoriteEvent").html("加入最愛");
                    	} else {
                    		window.alert(data);
                    	}
                    }
                });	
        	}
        	// addFavoriteEvent
        	if($("#favoriteEventStatus").val() == "outTheFavoriteEvent"){
        		var url = $("#projectName").val();
                url += '/favorite_event/FavoriteEventServlet.do';
                var data = '';
               	data += 'member_no=';
               	data += member_no;
               	data += '&evetit_no=';
               	data += evetit_no;
               	data += '&';
                data += 'action=addFavoriteEvent';
                $.ajax({
                    type: 'post',
                    url: url,
                    data: data,
                    success: function(data) {              	
                    	if(data.indexOf("成") != -1){
                    		$("#clickFavoriteEvent").toggleClass("glyphicon-heart glyphicon-heart-empty");
                    		$("#favoriteEventStatus").val("inTheFavoriteEvent");
                    		$("#clickFavoriteEvent").html("取消最愛");
                    	} else {
                    		window.alert(data);
                    	}
                    }
                });
        	}
        });
        
        
        
        toDataURL($("#poster").attr("src"), function(dataUrl) {
        	if(localStorage.getItem("eventTitleBrowsingHistory") == null){
        		var eventTitleBrowsingHistoryArray = [];
        	} else {
        		var eventTitleBrowsingHistoryJSONstr = localStorage.getItem("eventTitleBrowsingHistory");
        		var eventTitleBrowsingHistoryArray = JSON.parse(eventTitleBrowsingHistoryJSONstr);
        	}
            var evetit_name = $("#evetit_name").html();
            var evetit_no = $("#evetit_no").val();
			var oneEventTitleBrowsingHistory = new eventTitleBrowsingHistory(evetit_no, evetit_name, dataUrl);
			if(!isEventTitleBrowsingHistoryExist(eventTitleBrowsingHistoryArray, oneEventTitleBrowsingHistory)){
 				eventTitleBrowsingHistoryArray.push(oneEventTitleBrowsingHistory);
 			}
			var eventTitleBrowsingHistoryJSONstr = JSON.stringify(eventTitleBrowsingHistoryArray);
			localStorage.setItem("eventTitleBrowsingHistory", eventTitleBrowsingHistoryJSONstr);
        }); 
        
        
        
     	// the init favorite event state
     	if($("#member_no").val().trim().length == 7 && $("#evetit_no").val().trim().length == 5){
        	var member_no = $("#member_no").val();
        	var evetit_no = $("#evetit_no").val();
     		var url = $("#projectName").val();
            url += '/favorite_event/FavoriteEventServlet.do';
            var data = '';
           	data += 'member_no=';
           	data += member_no;
           	data += '&evetit_no=';
           	data += evetit_no;
           	data += '&';
            data += 'action=getOneFavoriteEvent_For_Display';
            $.ajax({
                type: 'post',
                url: url,
                data: data,
                success: function(data) {   
                	if(data.indexOf("true") != -1){
                		$("#clickFavoriteEvent").toggleClass("glyphicon-heart glyphicon-heart-empty");
                		$("#favoriteEventStatus").val("inTheFavoriteEvent");
                		$("#clickFavoriteEvent").html("取消最愛");
                	}
                	else if(data.indexOf("false") != -1){
						//do nothing if not in the record
                	} else {
                		window.alert(data);
                	}
                }
            });
     	}
     	
    });
    
    
    
    
    
	function isEventTitleBrowsingHistoryExist (eventTitleBrowsingHistoryArray, oneEventTitleBrowsingHistory){
		var isEventTitleBrowsingHistoryExist = false;
		for(var i = 0; i < eventTitleBrowsingHistoryArray.length; i++){
			if(eventTitleBrowsingHistoryArray[i].evetit_name === oneEventTitleBrowsingHistory.evetit_name){
				isEventTitleBrowsingHistoryExist = true;
				break;
			} 
		}		
		return isEventTitleBrowsingHistoryExist;
	}
    
    function toDataURL(url, callback) {
		var xhr = new XMLHttpRequest();
		xhr.onload = function() {
			var reader = new FileReader();
			reader.onloadend = function() {
			callback(reader.result);
  	    }
		reader.readAsDataURL(xhr.response);
  	  };
  	  xhr.open('GET', url);
  	  xhr.responseType = 'blob';
  	  xhr.send();
  	}
        
    function eventTitleBrowsingHistory(evetit_no, evetit_name, evetit_poster) {
    	this.evetit_no = evetit_no;
    	this.evetit_name = evetit_name;
        this.evetit_poster = evetit_poster;
    }
    </script>
</body>

</html>