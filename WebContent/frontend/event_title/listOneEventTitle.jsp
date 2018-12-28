<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.event_title.model.*"%>
<%@ page import="com.event_title.controller.*"%>

<%
	// just for add&delete FavoriteEvent Test
	String member_no = "member_no";
%>

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
	    
	    .pointer {cursor: pointer;}
    </style>
</head>

<body>
    <div class="container">
        <div class="col-xs-12 col-sm-8 col-sm-offset-2">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-9">
                    <h4 id="evetit_name">${aEventTitle.evetit_name}</h4>
                </div>
                <div id="toggleFavoriteEvent" class="pointer">
	                <div class="col-xs-12 col-sm-12 col-md-3 text-right" style="color:red;">
	                     <h4><i class="glyphicon glyphicon-heart-empty"></i>加入最愛</h4>
	                     <input type="hidden" id="favoriteEventStatus" value="inTheFavoriteEvent">
	                </div>
                </div>
            </div>
            <img src="<%= request.getContextPath()%>/event_title/EventTitleGifReader?scaleSize=850&evetit_no=${aEventTitle.evetit_no}" id="poster">
            <div>
                <input id="flip" type="button" value="查看活動場次" class="btn btn-primary"></input>
                <div id="panel" style="display:none;">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>日期時間</th>
                                <th>地點</th>
                                <th>購買狀態</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>資料</td>
                                <td>資料</td>
                                <td>資料</td>
                            </tr>
                            <tr>
                                <td>資料</td>
                                <td>資料</td>
                                <td>資料</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
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
    <script type="text/javascript">   
    
    $(document).ready(function() {
        $("#flip").click(function() {
            $("#panel").slideToggle("fast");
        });
        
        $("#toggleFavoriteEvent").click(function(){
        	$(".glyphicon").toggleClass("glyphicon-heart glyphicon-heart-empty");
        	
        	if($("#favoriteEventStatus").val() == "inTheFavoriteEvent"){
        		$("#favoriteEventStatus").val("outTheFavoriteEvent");
        		console.log($("#favoriteEventStatus").val());
        	} else {
        		$("#favoriteEventStatus").val("inTheFavoriteEvent");
        		console.log($("#favoriteEventStatus").val());
        	}
        	
        });
        
        toDataURL($("#poster").attr("src"), function(dataUrl) {
        	
//         	localStorage.clear();
        	        	
        	if(localStorage.getItem("eventTitleBrowsingHistory") == null){
        		var eventTitleBrowsingHistoryArray = [];
        		console.log("123");
        	} else {
        		var eventTitleBrowsingHistoryJSONstr = localStorage.getItem("eventTitleBrowsingHistory");
        		var eventTitleBrowsingHistoryArray = JSON.parse(eventTitleBrowsingHistoryJSONstr);
        	}
        	
//         				console.log(eventTitleBrowsingHistoryStr);
      	
            var evetit_name = $("#evetit_name").html();
// 			            console.log("evetit_name : ", evetit_name);
// 						console.log('RESULT:', dataUrl);

			var oneEventTitleBrowsingHistory = new eventTitleBrowsingHistory(evetit_name, dataUrl);
// 						console.log("oneEventTitleBrowsingHistory", oneEventTitleBrowsingHistory);
			console.log(oneEventTitleBrowsingHistory);
			
			//!!!!!!!!!!!!!!!!!!!!!!!!!檢查是否在array出現過
			eventTitleBrowsingHistoryArray.push(oneEventTitleBrowsingHistory);
			var eventTitleBrowsingHistoryJSONstr = JSON.stringify(eventTitleBrowsingHistoryArray);
					
			localStorage.setItem('eventTitleBrowsingHistory', eventTitleBrowsingHistoryJSONstr);
			console.log(localStorage.getItem('eventTitleBrowsingHistory'));
					    
        });       
        
    });
    

    
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
        
    function eventTitleBrowsingHistory(evetit_name, evetit_poster) {
        this.evetit_name = evetit_name;
        this.evetit_poster = evetit_poster;
    	this.toString = function (){
    		return this.evetit_name + this.evetit_poster;
    	};
    }
    </script>
</body>

</html>