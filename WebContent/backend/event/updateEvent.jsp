<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="com.event.model.*"%>

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
        .container img { 
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
    </style>
</head>

<body>

		<div class="container">
			<span class="text-danger">${eventErrorMsgs.Exception}</span>
		</div>


    	<div class="container">
			<form method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/event/EventServlet.do">
		        <div class="form-group">
	                <label for="evetit_no">主題</label>
	                <jsp:useBean id="eventTitleService" scope="page" class="com.event_title.model.EventTitleService" />	
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
	    		
	    		<jsp:useBean id="venueService" scope="page" class="com.venue.model.VenueService" />	            
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
	                        value="<fmt:formatDate value="${eventVO.eve_startdate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
	                    </div>
	                </div>
	                <div class="col-xs-12 col-sm-6">
	                    <div class="form-group">
	                        <label for="eve_enddate">活動結束日期時間</label>
	                        <span class="text-danger">${eventErrorMsgs.eve_enddate}</span>
	                        <span class="text-danger">${eventErrorMsgs.eve_enddate_BiggerThanToday}</span>
	                        <span class="text-danger">${eventErrorMsgs.eve_enddate_BiggerThanEveStartdate}</span>
	                        <input type="text" id="eve_enddate" name="eve_enddate" class="form-control" 
	                        value="<fmt:formatDate value="${eventVO.eve_enddate}" pattern="yyyy-MM-dd HH:mm:ss"/>"> 
	                    </div>
	                </div>
	            </div>           
	            
	            <div class="row">
	                <div class="col-xs-12 col-sm-6">
	                     <div class="form-group">
	                         <label for="eve_onsaledate">售票開始日期時間</label>
	                         <span class="text-danger">${eventErrorMsgs.eve_onsaledate}</span>
	                         <input type="text" id="eve_onsaledate" name="eve_onsaledate" class="form-control" 
	                         value="<fmt:formatDate value="${eventVO.eve_onsaledate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
	                     </div>
	                </div>
	                <div class="col-xs-12 col-sm-6">
	                     <div class="form-group">
	                         <label for="eve_offsaledate">售票結束日期時間</label>
	                         <span class="text-danger">${eventErrorMsgs.eve_offsaledate}</span>
	                         <span class="text-danger">${eventErrorMsgs.eve_offsaledate_BiggerThanToday}</span>
	                         <span class="text-danger">${eventErrorMsgs.eve_offsaledate_BiggerThanEve_onsaledate}</span>
	                         <input type="text" id="eve_offsaledate" name="eve_offsaledate" class="form-control"
	                         value="<fmt:formatDate value="${eventVO.eve_offsaledate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
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
	                         value="<fmt:formatDate value="${eventVO.fullrefundenddate}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly>
	                     </div>
	                </div>
	            </div>     
	
				<div class="row">
	                <div class="col-xs-12 col-sm-6">
	                	<div class="form-group">
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
		                	<jsp:useBean id="eventService" scope="page" class="com.event.model.EventService" />					
							<c:forEach var="ticketTypeVO" items="${eventService.getTicketTypesByEvent(eventVO.eve_no)}">
						         <div class="panel-group">
						            <div class="panel panel-info">
						                <div class="panel-heading" data-toggle="collapse" aria-expanded="false" href="#${ticketTypeVO.tictype_no}">
						                    <div class="panel-title">
						                    	<div class="colorarea" style="background-color:${ticketTypeVO.tictype_color};"></div>
												${ticketTypeVO.tictype_name}&nbsp;&nbsp;
												票價 : ${ticketTypeVO.tictype_price}
						                    </div>
						                </div>
						                <div id="${ticketTypeVO.tictype_no}" class="panel-collapse collapse in">
						                    <div class="panel-body">
						                    	<jsp:useBean id="ticketTypeService" scope="page" class="com.ticket_type.model.TicketTypeService" />
						                    	<c:forEach var="SeatingAreaVO" items="${ticketTypeService.getSeatingAreasByTicketType(ticketTypeVO.tictype_no)}"> 
						                    		<div style="border-bottom: 1px dotted #31708f;">
						                    			<div class="colorarea" style="background-color:${SeatingAreaVO.ticarea_color};"></div>			                           
						                           		${SeatingAreaVO.ticarea_name},&nbsp;&nbsp;
														總張數 : ${SeatingAreaVO.tictotalnumber}
						                            </div>
						                   		</c:forEach>
						                    </div>
						                </div>
						            </div>
						        </div>
							</c:forEach> 
						</div>         
	                </div>
	            </div> 
	  			<span class="form-group">
	  				<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
					<button type="submit" class="btn btn-success" name="action" value="updateEvent">儲存</button>
				</span>			
			</form>
        </div>

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
        	format: 'Y-m-d H:i:s',
        	minDate: 0,
        	step: 30,
        	timepicker: true,
            changeMonth: true,
            changeYear: true,	
        	onShow: function(){
        		this.setOptions({maxDate: $("#eve_enddate").val() ? $("#eve_enddate").val() : false})}
        });
        $("#eve_enddate").datetimepicker({
        	format: 'Y-m-d H:i:s',
        	step: 30,
        	timepicker: true,
            changeMonth: true,
            changeYear: true,	
        	onShow: function(){
        		this.setOptions({minDate: $("#eve_startdate").val() ? $("#eve_startdate").val() : false})}
        });
        
        $("#eve_onsaledate").datetimepicker({
        	format: 'Y-m-d H:i:s',
        	minDate: 0,
        	step: 30,
        	timepicker: true,
            changeMonth: true,
            changeYear: true,	
        	onShow: function(){
        		this.setOptions({maxDate: $("#eve_offsaledate").val() ? $("#eve_offsaledate").val() : false})}
        });
        $("#eve_offsaledate").datetimepicker({
        	format: 'Y-m-d H:i:s',
        	step: 30,
        	timepicker: true,
            changeMonth: true,
            changeYear: true,	
        	onShow: function(){
        		this.setOptions({minDate: $("#eve_onsaledate").val() ? $("#eve_onsaledate").val() : false})}
        });
        $("#fullrefundenddate").datetimepicker({
        	format: 'Y-m-d H:i:s',
        	minDate: 0,
        	step: 30,
        	timepicker: true,
            changeMonth: true,
            changeYear: true,	
        	onShow: function(){
        		this.setOptions({maxDate: $("#eve_startdate").val() ? $("#eve_startdate").val() : false})}
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