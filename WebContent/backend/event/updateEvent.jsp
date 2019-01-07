<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="com.event.model.*"%>
<jsp:useBean id="eventTitleService" scope="page" class="com.event_title.model.EventTitleService" />	
<jsp:useBean id="venueService" scope="page" class="com.venue.model.VenueService" />
<jsp:useBean id="eventService" scope="page" class="com.event.model.EventService" />		

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${eventVO.eve_sessionname}</title>
    <!-- Basic -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- datetimepicker -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.datetimepicker.css" />
    
    <style>
        .eve_seatmap_area img { 
      	   width: 100%; 
    	} 
    	.actionForm{ 
			display: inline; 
 		} 
 		.colorarea{
 			height: 13px;
 			width: 13px;
			display: inline-block;
			border-radius: 2px;
 		}
        .glyphicon {
            cursor: pointer; 
        }
        input:read-only { 
            background-color: #eee;
            opacity: 0.8;
        }
        .error{
        	border-color: rgb(255,0,0);
        }
        #ajaxMsgsNavbar {
	        left: 80%;
	        border-radius: 5px;
	        background-color: #D3D3D3;
	        opacity: 0.8;
	    }
 	    .warning:read-write { 
 	    	background-color: rgba(255,0,0,.3); 
 	    	color: black;
 	    } 
 	    body{
			font-family:微軟正黑體!important;
		}
    </style>
</head>

<body>



		<jsp:include page="/backend/navbar_back-end.html" flush="true" />
		


		<div class="container">
			<span class="text-danger">${eventErrorMsgs.Exception}</span>
			<input type="hidden" id="projectName" name="projectName" value="<%=request.getContextPath() %>" readonly>
		</div>





		<div class="container">
			<form method="post" action="<%=request.getContextPath()%>/event/EventServlet.do" id="copyEventForm">
				<div class="row">
					<div class="col-xs-12 col-sm-6">
						<div class="form-group">
							<label>快速套用活動場次</label>
							<select class="form-control" name="evetit_no_forCopy" id="selectedEventTitle_forCopyChoose">
								<option value="pleaseChoose">
									請選擇欲套用的活動主題
								</option>
			                    <c:forEach var="eventTitleVO" items="${eventTitleService.all}">
									<option value="${eventTitleVO.evetit_no}">
										${eventTitleVO.evetit_no} : ${eventTitleVO.evetit_name}
									</option>
			                    </c:forEach>
			                </select>
						</div>
						</div>
						<div class="col-xs-10 col-sm-5">
							<div class="form-group">
								<label style="color:white;">theHiddenEvent</label>
								<select class="form-control" name="eve_no_forCopy" id="selectedEvent_forCopyChoose">
								</select>
							</div>
						</div>
						<div class="col-xs-2 col-sm-1">
						<span class="form-group">
							<label style="color:white;">theHiddenEvent</label>
							<input type="hidden" name="eve_no" id="eve_no" value="${eventVO.eve_no}">
							<input type="hidden" name="action" value="copyEvent">
							<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
							<button type="button" class="btn btn-success" id="copyEvent" style="float:right;">快速套用</button>
						</span>	
					</div>
				</div>
            </form>
            <br>
		</div>





    	<div class="container" style="margin-bottom:30px;">
			<form method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/event/EventServlet.do">
			
		        <div class="form-group">
	                <label for="evetit_no">主題</label>
	                <input type="hidden" name="evetit_no" id="evetit_no" class="form-control" value="${eventVO.evetit_no}"> 			
	                <input type="text" name="evetit_name" id="evetit_name" class="form-control" value="${eventTitleService.getOneEventTitle(eventVO.evetit_no).evetit_name}" readonly>                
	            </div>    
		            
	            <div class="form-group">
	                <label for="eve_no">場次編號</label>
	                <input type="text" name="eve_no" id="eve_no" class="form-control" value="${eventVO.eve_no}" readonly>
	            </div>	  
	            
	            <div class="form-group">
	                <label for="eve_sessionname">場次名稱</label>
	                <span class="text-danger">${eventErrorMsgs.eve_sessionname}</span>
	                <input type="text" name="eve_sessionname" id="eve_sessionname" class="form-control" value="${eventVO.eve_sessionname}">
	            </div>	      
	    			            
	            <div class="form-group">
	                <label>場地</label>
	                <select class="form-control" name="venue_no">
	                    <c:forEach var="venueVO" items="${venueService.all}">
	                        <option value="${venueVO.venue_no}" ${(venueVO.venue_no == eventVO.venue_no) ? 'selected' : '' }>
	                            ${venueVO.venue_name} ( ${venueVO.address} ) 
	                        </option>
	                    </c:forEach>
	                </select>
	            </div>
	    		
	    		<div class="row">
	                <div class="col-xs-12 col-sm-6">
	                    <div class="form-group">
	                        <label for="eve_startdate">活動開始日期時間</label>
	                        <span class="text-danger">${eventErrorMsgs.eve_startdate}</span>
	                        <input type="text" id="eve_startdate" name="eve_startdate" class="form-control" 
	                        value="<fmt:formatDate value="${eventVO.eve_startdate}" pattern="yyyy-MM-dd HH:mm"/>">
	                    </div>
	                </div>
	                <div class="col-xs-12 col-sm-6">
	                    <div class="form-group">
	                        <label for="eve_enddate">活動結束日期時間</label>
	                        <span class="text-danger">${eventErrorMsgs.eve_enddate}</span>
	                        <span class="text-danger">${eventErrorMsgs.eve_enddate_BiggerThanToday}</span>
	                        <span class="text-danger">${eventErrorMsgs.eve_enddate_BiggerThanEveStartdate}</span>
	                        <input type="text" id="eve_enddate" name="eve_enddate" class="form-control" 
	                        value="<fmt:formatDate value="${eventVO.eve_enddate}" pattern="yyyy-MM-dd HH:mm"/>"> 
	                    </div>
	                </div>
	            </div>           
	            
	            <div class="row">
	                <div class="col-xs-12 col-sm-6">
	                     <div class="form-group">
	                         <label for="eve_onsaledate">售票開始日期時間</label>
	                         <span class="text-danger">${eventErrorMsgs.eve_onsaledate}</span>
	                         <input type="text" id="eve_onsaledate" name="eve_onsaledate" class="form-control" 
	                         value="<fmt:formatDate value="${eventVO.eve_onsaledate}" pattern="yyyy-MM-dd HH:mm"/>">
	                     </div>
	                </div>
	                <div class="col-xs-12 col-sm-6">
	                     <div class="form-group">
	                         <label for="eve_offsaledate">售票結束日期時間</label>
	                         <span class="text-danger">${eventErrorMsgs.eve_offsaledate}</span>
	                         <span class="text-danger">${eventErrorMsgs.eve_offsaledate_BiggerThanToday}</span>
	                         <span class="text-danger">${eventErrorMsgs.eve_offsaledate_BiggerThanEve_onsaledate}</span>
	                         <input type="text" id="eve_offsaledate" name="eve_offsaledate" class="form-control"
	                         value="<fmt:formatDate value="${eventVO.eve_offsaledate}" pattern="yyyy-MM-dd HH:mm"/>">
	                     </div>
	                </div>
	            </div>
	                     
	            <div class="row">
	                <div class="col-xs-12 col-sm-3">
	                     <div class="form-group">
	                         <label>購買張數限制</label>                             
	                         <select class="form-control" name="ticlimit">
	                         	<option value="1" ${(eventVO.ticlimit == '1') ? 'selected' : '' }>1 張 / 人</option>
	                         	<option value="2" ${(eventVO.ticlimit == '2') ? 'selected' : '' }>2 張 / 人</option>
	                         	<option value="3" ${(eventVO.ticlimit == '3') ? 'selected' : '' }>3 張 / 人</option>
	                         	<option value="4" ${(eventVO.ticlimit == '4') ? 'selected' : '' }>4 張 / 人</option>
	                         </select> 
	                     </div>
	                </div>
	                <div class="col-xs-12 col-sm-3">
	                     <div class="form-group">
	                         <label>狀態</label>                             
	                         <select class="form-control" name="eve_status">
	                         	<option value="normal" ${(eventVO.eve_status == 'normal') ? 'selected' : '' }>正常</option>
	                         	<option value="cancel" ${(eventVO.eve_status == 'cancel') ? 'selected' : '' }>取消</option>
	                         </select>     
	                     </div>
	                </div>
	                <div class="col-xs-12 col-sm-6">
	                     <div class="form-group">
	                         <label for="fullrefundenddate">可全額退款到期日期時間</label>
	                         <span class="text-danger">${eventErrorMsgs.fullrefundenddate_BiggerThanToday}</span>
	                         <span class="text-danger">${eventErrorMsgs.fullrefundenddate_BiggerThanEve_startdate}</span>
	                         <input type="text" id="fullrefundenddate" name="fullrefundenddate" class="form-control"
	                         value="<fmt:formatDate value="${eventVO.fullrefundenddate}" pattern="yyyy-MM-dd HH:mm"/>" readonly>
	                     </div>
	                </div>
	            </div>     
	
				<div class="row">
	                <div class="col-xs-12 col-sm-6">
	                	<div class="form-group eve_seatmap_area">
							<label for="eve_seatmap">座位圖</label>
							<span class="text-danger">${eventErrorMsgs.eve_seatmap}</span>
							<input type="file" id="eve_seatmap" name="eve_seatmap" class="form-control" accept="image/*">	               
			                <input type="hidden" id="eve_seatmap_status" name="eve_seatmap_status" value="${(eve_seatmap_status == 'alreadyUpload') ? 'alreadyUpload' : 'noUpload'}">
							<c:if test="${eventVO.eve_startdate == null && eventVO.eve_enddate == null && eventVO.eve_onsaledate == null && eventVO.eve_offsaledate == null}">
			         			<input type="hidden" id="eve_seatmap_init" name="eve_seatmap_init" value="init">
		        			</c:if>
							<c:if test="${eve_seatmap_status != 'alreadyUpload'}">
			                	<img src="<%= request.getContextPath()%>/event/EventGifReader?scaleSize=850&eve_no=${eventVO.eve_no}" id="eve_seatmap_preview">
		                    </c:if>					
							<c:if test="${eve_seatmap_status == 'alreadyUpload'}">
			                	<img src="${eve_seatmap_path}" id="eve_seatmap_preview">
		                    </c:if>
	                    </div>	                    
	                </div>                	
	                <div class="col-xs-12 col-sm-6">
	                	<div class="form-group">
	                    	<label>票種票區</label>                      			
							<c:forEach var="ticketTypeVO" items="${eventService.getTicketTypesByEvent(eventVO.eve_no)}">
						         <div class="panel-group">
						            <div class="panel panel-info">
						                <div class="panel-heading">
						                    <div class="panel-title">
						                    	<!-- --------------------票種位置::頭-------------------- -->
												<span>
													<input type="hidden" name="tictype_no" value="${ticketTypeVO.tictype_no}" readonly>
													票種色 : <input type="color" name="tictype_color" value="${ticketTypeVO.tictype_color}" readonly>&nbsp;&nbsp;&nbsp;
													票種名 : <input type="text" class="warning" name="tictype_name" value="${ticketTypeVO.tictype_name}" size="10" readonly>&nbsp;&nbsp;&nbsp;
													票價 : <input type="text" class="warning" name="tictype_price" value="${ticketTypeVO.tictype_price}" size="10" readonly>元
												</span>
												<!-- --------------------票種位置::尾-------------------- -->
												<div style="display: flex; justify-content: flex-end; margin-top:10px">
						                            <i class="glyphicon glyphicon-pencil">編輯&nbsp;</i>
						                            <i class="glyphicon glyphicon-ok updateTicketType" style="display:none;">儲存&nbsp;</i>
						                            <i class="glyphicon glyphicon-trash deleteTicketType">刪除&nbsp;</i>
						                            <i class="glyphicon glyphicon-file copyTicketType">複製&nbsp;</i>
						                            <i class="glyphicon glyphicon-th-list" data-toggle="collapse" aria-expanded="false" href="#${ticketTypeVO.tictype_no}">收合</i>
						                        </div>
						                    </div> 
						                </div>
						                <div id="${ticketTypeVO.tictype_no}" class="panel-collapse collapse in">
						                    <div class="panel-body">
						                    	<jsp:useBean id="ticketTypeService" scope="page" class="com.ticket_type.model.TicketTypeService" />
						                    	<c:forEach var="SeatingAreaVO" items="${ticketTypeService.getSeatingAreasByTicketType(ticketTypeVO.tictype_no)}"> 
						                    		<div style="border-bottom: 1px dotted #31708f; margin-bottom:5px;">
														<!-- --------------------票區位置::頭-------------------- -->
                            							<span>
                            								 <input type="hidden" name="ticarea_no" value="${SeatingAreaVO.ticarea_no}" readonly>
											                                票區色 : <input type="color" name="ticarea_color" value="${SeatingAreaVO.ticarea_color}" readonly>&nbsp;&nbsp;&nbsp;
											                                票區名 : <input type="text" class="warning" name="ticarea_name" value="${SeatingAreaVO.ticarea_name}" size="10" readonly>&nbsp;&nbsp;&nbsp;
											                                張數 : <input type="text" class="warning" name="tictotalnumber" value="${SeatingAreaVO.tictotalnumber}" size="10" readonly>張
													    </span>
							                            <!-- --------------------票區位置::尾-------------------- -->
							                            <div style="display:flex; justify-content:flex-end; margin-bottom:10px; margin-top:10px;">
							                                <i class="glyphicon glyphicon-pencil">編輯&nbsp;</i>
							                                <i class="glyphicon glyphicon-ok updateSeatingArea" style="display:none;">儲存&nbsp;</i>
							                                <i class="glyphicon glyphicon-trash deleteSeatingArea">刪除&nbsp;</i>
							                                <i class="glyphicon glyphicon-file copySeatingArea">複製&nbsp;</i>
							                            </div>
						                            </div>
						                   		</c:forEach>
						                   		<button type="button" class="btn btn-basic btn-sm" style="margin-top:20px;padding:0px;">
								                    	<i class="glyphicon glyphicon-plus addSeatingArea">新增票區</i>
								            	</button>
						                    </div>
						                </div>
						            </div>
						        </div>
							</c:forEach>
							<div>
								<button type="button" class="btn btn-basic btn-sm" style="margin-top:20px;padding:0px;">
						            <i class="glyphicon glyphicon-plus" id="addTicketType">新增票種</i>
						        </button>
					        </div> 
						</div>         
	                </div>
	            </div> 
	  			<span class="form-group">
	  				<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
					<button type="submit" class="btn btn-success" name="action" value="updateEvent" style="margin-top:15px;">儲存</button>
					<a class="btn btn-danger" href="<%=request.getContextPath()%>/event/EventServlet.do?action=deleteEvent&evetit_no=${eventVO.evetit_no}&eve_no=${eventVO.eve_no}&requestURL=/backend/event_title/listAllEventTitleRelatives.jsp" style="margin-top:15px;">刪除</a>
					<a class="btn btn-info" href="<%=request.getContextPath()%>/backend/event_title/listAllEventTitleRelatives.jsp" style="margin-top:15px;">回活動總覽</a>
				</span>			
			</form>
        </div>
        
        
        
        
        
<!-- --------------------=====----- ///secretSeatingtArea/// -----=====-------------------- -->        
<div style="border-bottom: 1px dotted #31708f; margin-bottom:5px; display:none;" id="secretSeatingtArea">
	<!-- --------------------票區位置::頭-------------------- -->
	<span>
	   <input type="hidden" name="ticarea_no" value="">
	          票區色 : <input type="color" name="ticarea_color" value="#3399ff">&nbsp;&nbsp;&nbsp;
	          票區名 : <input type="text" class="warning" name="ticarea_name" value="" size="10">&nbsp;&nbsp;&nbsp;
	          張數 : <input type="text" class="warning" name="tictotalnumber" value="" size="10">張
	</span>
	<!-- --------------------票區位置::尾-------------------- -->
	<div style="display:flex; justify-content:flex-end; margin-bottom:10px; margin-top:10px;">
	    <i class="glyphicon glyphicon-pencil" style="display:none;">編輯&nbsp;</i>
	    <i class="glyphicon glyphicon-ok updateSeatingArea">儲存&nbsp;</i>
	    <i class="glyphicon glyphicon-trash deleteSeatingArea">刪除&nbsp;</i>
	    <i class="glyphicon glyphicon-file copySeatingArea" style="display:none;">複製&nbsp;</i>
	</div>
</div>



<!-- --------------------=====----- ///secretTicketType/// -----=====-------------------- -->  
<div class="panel-group" id="secretTicketType" style="display:none;">
	<div class="panel panel-info">
		<div class="panel-heading">
			<div class="panel-title">
				<!-- --------------------票種位置::頭-------------------- -->
				<span>
					<input type="hidden" name="tictype_no" value="">
					票種色 : <input type="color" name="tictype_color" value="#3399ff">&nbsp;&nbsp;&nbsp;
					票種名 : <input type="text" class="warning" name="tictype_name" value="" size="10">&nbsp;&nbsp;&nbsp;
					票價 : <input type="text" class="warning" name="tictype_price" value="" size="10">元
				</span>
				<!-- --------------------票種位置::尾-------------------- -->
				<div style="display:flex; justify-content:flex-end; margin-top:10px">
					<i class="glyphicon glyphicon-pencil" style="display:none;">編輯&nbsp;</i>
					<i class="glyphicon glyphicon-ok updateTicketType">儲存&nbsp;</i>
					<i class="glyphicon glyphicon-trash deleteTicketType">刪除&nbsp;</i>
					<i class="glyphicon glyphicon-file copyTicketType" style="display:none;">複製&nbsp;</i>
					<i class="glyphicon glyphicon-th-list" data-toggle="collapse" aria-expanded="false" href="#init">收合</i>
				</div>
			</div> 
		</div>
		<div id="init" class="panel-collapse collapse in">
			<div class="panel-body">
				<button type="button" class="btn btn-basic btn-sm" style="margin-top:20px;padding:0px;">
					<i class="glyphicon glyphicon-plus addSeatingArea">新增票區</i>
				</button>
			</div>
		</div>
	</div>
	<br>
</div>
        
 
 
<!-- --------------------=====----- ///ajaxBack/// -----=====-------------------- -->    
<nav class="navbar navbar-default navbar-fixed-bottom" role="navigation" style="display:none" id="ajaxMsgsNavbar">
	<div id="ajaxMsgs" style="margin:5px;"></div>
</nav>



    
        
        
        



    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- datetimepicker -->
    <script src="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.datetimepicker.full.js"></script>
    <!-- JavaScript in HTML -->
    <script type="text/javascript">
    $(document).ready(function() {
        $("#flip").click(function() {
            $("#panel").slideToggle("fast");
        }); 
        
        $("#eve_seatmap").change(function() {
            imagesPreview(this);
            $("#eve_seatmap_status").attr("value", "yesUpload");
        });
        
        $(".text-danger").each(function(){
        	var errorMsg = $(this).text();
        	if( errorMsg.trim() != "" ){
        		$(this).prepend("<i class='glyphicon glyphicon-triangle-left'></i>");
        	}        	
        });
        
        
        
        
        
		// -----datetimepicker---------------------------------------------
        $.datetimepicker.setLocale('zh');

        $("#eve_startdate").datetimepicker({
        	format: 'Y-m-d H:i',
        	minDate: 0,
        	step: 30,
        	timepicker: true,
            changeMonth: true,
            changeYear: true,	
        	onShow: function(){
        		this.setOptions({maxDate: $("#eve_enddate").val() ? $("#eve_enddate").val() : false})}
        });
        $("#eve_enddate").datetimepicker({
        	format: 'Y-m-d H:i',
        	step: 30,
        	timepicker: true,
            changeMonth: true,
            changeYear: true,	
        	onShow: function(){
        		this.setOptions({minDate: $("#eve_startdate").val() ? $("#eve_startdate").val() : false})}
        });
        
        $("#eve_onsaledate").datetimepicker({
        	format: 'Y-m-d H:i',
        	minDate: 0,
        	step: 30,
        	timepicker: true,
            changeMonth: true,
            changeYear: true,	
        	onShow: function(){
        		this.setOptions({maxDate: $("#eve_offsaledate").val() ? $("#eve_offsaledate").val() : false})}
        });
        $("#eve_offsaledate").datetimepicker({
        	format: 'Y-m-d H:i',
        	step: 30,
        	timepicker: true,
            changeMonth: true,
            changeYear: true,	
        	onShow: function(){
        		this.setOptions({minDate: $("#eve_onsaledate").val() ? $("#eve_onsaledate").val() : false})}
        });
        $("#fullrefundenddate").datetimepicker({
        	format: 'Y-m-d H:i',
        	minDate: 0,
        	step: 30,
        	timepicker: true,
            changeMonth: true,
            changeYear: true,	
        	onShow: function(){
        		this.setOptions({maxDate: $("#eve_startdate").val() ? $("#eve_startdate").val() : false})}
        });
        
        
        
        
        
        // -----dynamic input: ticketType and ticketArea---------------------------------------------
        $(".glyphicon-pencil").click(function(e){
            $(e.target).hide();
            $(e.target).next().show();
            $(e.target).next().next().next().hide();
            $(e.target).parent().prev().children("input").each(function(){
                $(this).prop("readonly", false);
            });
        });       
        
        
        
        
        
        $(".updateTicketType").click(function(e){
            var url = $("#projectName").val();
            url += '/ticket_type/TicketTypeServlet.do';
            var data = '';
            var tictype_name = $(e.target).parent().prev().children("input[name=tictype_name]");
            if(tictype_name.val().trim().length == 0){
            	tictype_name.addClass("error");
            	tictype_name.attr("placeholder", "請輸入 !");
            	return;
            }           
            tictype_name.removeClass("error");
            var tictype_price = $(e.target).parent().prev().children("input[name=tictype_price]");
            if(tictype_price.val().trim().length == 0 || isNaN(tictype_price.val())){
            	tictype_price.addClass("error");
            	tictype_price.val("請輸入正確格式 !");
            	tictype_price.select();
            	return;
            }   		
            tictype_price.removeClass("error");
            $(e.target).parent().prev().children("input").each(function(){
            	data += $(this).attr("name") ;
            	data += '=';
            	data += $(this).val();
            	data += '&';
            });
            data += 'action=updateTicketType';
            $.ajax({
                type: 'post',
                url: url,
                data: data,
                success: function(data) {
                	$("#ajaxMsgs").html(data);
                	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();
                	if(data.indexOf("成") != -1){
                		$(e.target).parent().prev().children("input").each(function(){
                            $(this).prop("readonly", true);
                        });
                        $(e.target).hide();
                        $(e.target).prev().show();
                        $(e.target).next().next().show();
                	}
                }
            });
        });


        
        
        
        $(".updateSeatingArea").click(function(e){
            var url = $("#projectName").val();
            url += '/seating_area/SeatingAreaServlet.do';
            var data = '';
            var ticarea_name = $(e.target).parent().prev().children("input[name=ticarea_name]");
            if(ticarea_name.val().trim().length == 0){
            	ticarea_name.addClass("error");
            	ticarea_name.attr("placeholder", "請輸入 !");
            	return;
            }           
            ticarea_name.removeClass("error");
            var tictotalnumber = $(e.target).parent().prev().children("input[name=tictotalnumber]");
            if(tictotalnumber.val().trim().length == 0 || isNaN(tictotalnumber.val())){
            	tictotalnumber.addClass("error");
            	tictotalnumber.val("請輸入正確格式 !");
            	tictotalnumber.select();
            	return;
            }   		
            tictotalnumber.removeClass("error");
            $(e.target).parent().prev().children("input").each(function(){
            	data += $(this).attr("name") ;
            	data += '=';
            	data += $(this).val();
            	data += '&';
            });
            data += 'action=updateSeatingArea';
            $.ajax({
                type: 'post',
                url: url,
                data: data,
                success: function(data) {
                	$("#ajaxMsgs").html(data);
                	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();               	
                	if(data.indexOf("成") != -1){
                        $(e.target).parent().prev().children("input").each(function(){
                            $(this).prop("readonly", true);
                        });                        
                        $(e.target).hide();
                        $(e.target).prev().show();
                        $(e.target).next().next().show();
                	}
                }
            });
        });
        

        
        

        $(".deleteTicketType").click(function(e){
            var url = $("#projectName").val();
            url += '/ticket_type/TicketTypeServlet.do';
            var data = '';
            $(e.target).parent().prev().children("input").each(function(){
            	data += $(this).attr("name") ;
            	data += '=';
            	data += $(this).val();
            	data += '&';
            });
            data += 'action=deleteTicketType';
            $.ajax({
                type: 'post',
                url: url,
                data: data,
                success: function(data) {
                	$("#ajaxMsgs").html(data);
                	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();
                	if(data.indexOf("成") != -1){
                        $(e.target).parents(".panel-group").remove();
                	}
                }
            });
        });
        
        
        
        
        
        $(".deleteSeatingArea").click(function(e){
        	var url = $("#projectName").val();
            url += '/seating_area/SeatingAreaServlet.do';
            var data = '';
            $(e.target).parent().prev().children("input").each(function(){
            	data += $(this).attr("name") ;
            	data += '=';
            	data += $(this).val();
            	data += '&';
            });
            data += 'action=deleteSeatingArea';
            console.log(data);
            $.ajax({
                type: 'post',
                url: url,
                data: data,
                success: function(data) {
                	$("#ajaxMsgs").html(data);
                	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();               	
                	if(data.indexOf("成") != -1){
                		$(e.target).parent().parent().remove();
                	}
                }
            });
        });
        
        

        
        
        $(".glyphicon-th-list").click(function(e){
            $(e.target).toggleClass("glyphicon-minus glyphicon-th-list");
        });
        
        
        
        
        
        $("#addTicketType").click(function(e){
        	var url = $("#projectName").val();
            url += '/ticket_type/TicketTypeServlet.do';
            var data = '';
           	data += 'eve_no' ;
           	data += '=';
           	data += $("#eve_no").val();
           	data += '&';
            data += 'action=addTicketType';
            $.ajax({
                type: 'post',
                url: url,
                data: data,
                success: function(data) {
                	if(data.indexOf("失") != -1){
                		$("#ajaxMsgs").html(data);
                    	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();                		
                	} else {
                    	$("#ajaxMsgs").html("  ### 票種新增成功 ");
                    	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();	
                    	var ticketTypeVo = JSON.parse(data);
                    	var tictype_no = ticketTypeVo.tictype_no;
                    	var tictype_color = ticketTypeVo.tictype_color;
                    	var tictype_name = ticketTypeVo.tictype_name;
                    	var tictype_price = ticketTypeVo.tictype_price;       
                    	var clonedTicketType = $("#secretTicketType").clone(true, true);
                    	clonedTicketType.attr("id", "shareFix" + tictype_no);
                    	clonedTicketType.css("display", "inline");
                    	clonedTicketType.find(".glyphicon-th-list").attr("href", "#"+tictype_no);
                    	clonedTicketType.find(".panel-collapse").attr("id", tictype_no);        
                    	clonedTicketType.find("input[name=tictype_no]").val(tictype_no);
                    	clonedTicketType.find("input[name=tictype_name]").val(tictype_name);
                    	clonedTicketType.find("input[name=tictype_color]").val(tictype_color);
                    	clonedTicketType.find("input[name=tictype_price]").val(tictype_price);                       
                        $(e.target).parent().before(clonedTicketType);
                	}
                }
            });       	
        });

        
        
        
        
        $(".addSeatingArea").click(function(e){
        	var url = $("#projectName").val();
            url += '/seating_area/SeatingAreaServlet.do';
            var data = '';
           	data += 'eve_no' ;
           	data += '=';
           	data += $("#eve_no").val();
           	data += '&';       	
           	data += 'tictype_no' ;
           	data += '=';
           	data += $(e.target).parent().parent().parent().attr("id");
           	data += '&';           	
            data += 'action=addSeatingArea';
            $.ajax({
                type: 'post',
                url: url,
                data: data,
                success: function(data) {
                	if(data.indexOf("失") != -1){
                		$("#ajaxMsgs").html(data);
                    	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();                
                    	console.log(data);
                	} else {
                    	$("#ajaxMsgs").html("  ### 票區新增成功 ");
                    	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();
                    	var seatingAreaVo = JSON.parse(data);
                    	var ticarea_no = seatingAreaVo.ticarea_no;
                    	var ticarea_color = seatingAreaVo.ticarea_color;
                    	var ticarea_name = seatingAreaVo.ticarea_name;
                    	var tictotalnumber = seatingAreaVo.tictotalnumber;               
                        var clonedSeatingtArea = $("#secretSeatingtArea").clone(true, true);
                        clonedSeatingtArea.attr("id", "shareFix" + ticarea_no);
                        clonedSeatingtArea.css("display", "block");
                        clonedSeatingtArea.find("input[name=ticarea_no]").val(ticarea_no);
                        clonedSeatingtArea.find("input[name=ticarea_color]").val(ticarea_color);
                        clonedSeatingtArea.find("input[name=ticarea_name]").val(ticarea_name);
                        clonedSeatingtArea.find("input[name=tictotalnumber]").val(tictotalnumber);
                        $(e.target).parent().before(clonedSeatingtArea);
                	}
                }
            });       	
        });

        
        
        
        
        $(".copySeatingArea").click(function(e){
            var url = $("#projectName").val();
            url += '/seating_area/SeatingAreaServlet.do';    
			var data = '';
           	data += 'eve_no' ;
           	data += '=';
           	data += $("#eve_no").val();
           	data += '&';
       		data += 'tictype_no' ;
           	data += '=';
           	data += $(e.target).parent().parent().parent().parent().attr("id");
          	data += '&';
            $(e.target).parent().prev().children("input").each(function(){
            	data += $(this).attr("name") ;
            	data += '=';
            	data += $(this).val();
            	data += '&';
            });
            data += 'action=copySeatingArea';
            $.ajax({
                type: 'post',
                url: url,
                data: data,
                success: function(data) {              	
                	if(data.indexOf("失") != -1){
                		$("#ajaxMsgs").html(data);
                    	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();                
                	} else {
                    	$("#ajaxMsgs").html("  ### 票區複製新增成功 ");
                    	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();
                    	var seatingAreaVo = JSON.parse(data);
                    	var ticarea_no = seatingAreaVo.ticarea_no;
                    	var ticarea_color = seatingAreaVo.ticarea_color;
                    	var ticarea_name = seatingAreaVo.ticarea_name;
                    	var tictotalnumber = seatingAreaVo.tictotalnumber;               
                        var clonedSeatingtArea = $("#secretSeatingtArea").clone(true, true);
                        clonedSeatingtArea.attr("id", "shareFix" + ticarea_no);
                        clonedSeatingtArea.css("display", "block");
                        clonedSeatingtArea.find("input[name=ticarea_no]").val(ticarea_no);
                        clonedSeatingtArea.find("input[name=ticarea_color]").val(ticarea_color);
                        clonedSeatingtArea.find("input[name=ticarea_name]").val(ticarea_name);
                        clonedSeatingtArea.find("input[name=tictotalnumber]").val(tictotalnumber);
                        $(e.target).parent().parent().parent().children().last().before(clonedSeatingtArea);
                	}
                }
            });
        });
        
        
        
        
        
        $("#copyEvent").click(function(e){
        	if($("#selectedEvent_forCopyChoose").val() == null){
        		window.alert("請選擇欲套用的活動場次");
        		return;
        	}
        	$("#copyEventForm").submit();
        });
        
        
        
        
        
        $(".copyTicketType").click(function(e){
        	var tictype_no_forCopy = $(e.target).parents(".panel-heading").children().find("input[name=tictype_no]").val()
        	console.log(tictype_no_forCopy);
        	
           	var url = $("#projectName").val();
            url += '/ticket_type/TicketTypeServlet.do';
            var data = '';
           	data += 'tictype_no_forCopy' ;
           	data += '=';
           	data += tictype_no_forCopy;
           	data += '&';
            data += 'action=copyTicketType';
            console.log(data);
            
            $.ajax({
                type: 'post',
                url: url,
                data: data,
                success: function(data) {
                	if(data.indexOf("失") != -1){
                		$("#ajaxMsgs").html(data);
                    	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();                		
                	} else {
                    	$("#ajaxMsgs").html("  ### 票種複製新增成功 ");
                    	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();	
                    	console.log(data);
                    	var objs = JSON.parse(data);
                    	var tictype_no = objs[0].tictype_no;
                    	var tictype_color = objs[0].tictype_color;
                    	var tictype_name = objs[0].tictype_name;
                    	var tictype_price = objs[0].tictype_price;     
                    	console.log(tictype_no);
                    	console.log(tictype_color);
                    	console.log(tictype_name);
                    	console.log(tictype_price);
                    	
                    	var clonedTicketType = $("#secretTicketType").clone(true, true);
                    	clonedTicketType.attr("id", "shareFix" + tictype_no);
                    	clonedTicketType.css("display", "inline");
                    	clonedTicketType.find(".glyphicon-th-list").attr("href", "#"+tictype_no);
                    	clonedTicketType.find(".panel-collapse").attr("id", tictype_no);        
                    	clonedTicketType.find("input[name=tictype_no]").val(tictype_no);
                    	clonedTicketType.find("input[name=tictype_name]").val(tictype_name);
                    	clonedTicketType.find("input[name=tictype_color]").val(tictype_color);
                    	clonedTicketType.find("input[name=tictype_price]").val(tictype_price);                       
                        $("#addTicketType").parent().before(clonedTicketType);
                        
                        
						for(var i = 1; i < objs.length; i++){
	                    	var ticarea_no = objs[i].ticarea_no;
	                    	var ticarea_color = objs[i].ticarea_color;
	                    	var ticarea_name = objs[i].ticarea_name;
	                    	var tictotalnumber = objs[i].tictotalnumber;               
	                        var clonedSeatingtArea = $("#secretSeatingtArea").clone(true, true);
	                        clonedSeatingtArea.attr("id", "shareFix" + ticarea_no);
	                        clonedSeatingtArea.css("display", "block");
	                        clonedSeatingtArea.find("input[name=ticarea_no]").val(ticarea_no);
	                        clonedSeatingtArea.find("input[name=ticarea_color]").val(ticarea_color);
	                        clonedSeatingtArea.find("input[name=ticarea_name]").val(ticarea_name);
	                        clonedSeatingtArea.find("input[name=tictotalnumber]").val(tictotalnumber);
							console.log($("#shareFix" + tictype_no).children().find("button").before(clonedSeatingtArea));
						}
                	}
                }
            });       	
        
        });
        
        
        
        
        
        $("#selectedEventTitle_forCopyChoose").change(function(e){
        	var selectedEventTitle_forCopyChoose = $(e.target).val();
       	  	var url = $("#projectName").val();
            url += '/event_title/EventTitleServlet.do';    
  			var data = '';
           	data += 'evetit_no' ;
           	data += '=';
           	data += selectedEventTitle_forCopyChoose;
           	data += '&';
			data += 'action=listEvents_ByEventTitle_forCopyChoose';
			console.log(data);
	        $.ajax({
				type: 'post',
	            url: url,
	            data: data,
	            success: function(data) {
	            	if(data.indexOf("失") != -1){
	            		$("#ajaxMsgs").html(data);
	                	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();                
	            	} else {
	                	$("#ajaxMsgs").html("  ### 取得活動成功 ");
	                	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();
	                	var eventVoList = JSON.parse(data);
	                	$("#selectedEvent_forCopyChoose").empty();
	                	for(var i = 0; i < eventVoList.length; i++){
	                		$("#selectedEvent_forCopyChoose").append("<option value='" + eventVoList[i].eve_no + "'>" + eventVoList[i].eve_sessionname + "</option>");
	                	}
	            	}
	            }
	        });
        });

        
        
        
    });
    
    
    
    function imagesPreview(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $("#eve_seatmap_preview").attr("src", e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }
    </script>
</body>

</html>