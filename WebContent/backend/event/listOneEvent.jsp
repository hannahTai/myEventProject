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
	            
	        <div class="form-group">
                <label for="evetit_no">主題</label>
                <jsp:useBean id="eventTitleService" scope="page" class="com.event_title.model.EventTitleService" />				
                <input type="text" name="evetit_no" id="evetit_no" class="form-control" value="${eventTitleService.getOneEventTitle(eventVO.evetit_no).evetit_name}" readonly>                
            </div>    
	            
            <div class="form-group">
                <label for="eve_no">場次編號</label>
                <input type="text" name="eve_no" id="eve_no" class="form-control" value="${eventVO.eve_no}" readonly>
            </div>	  
            
            <div class="form-group">
                <label for="eve_sessionname">場次名稱</label>
                <input type="text" name="eve_sessionname" id="eve_sessionname" class="form-control" value="${eventVO.eve_sessionname}" readonly>
            </div>	      
    		
    		<jsp:useBean id="venueService" scope="page" class="com.venue.model.VenueService" />	            
            <div class="form-group">
                <label>場地</label>
                <select class="form-control" name="venue_no" readonly>
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
                        <input type="text" id="eve_startdate" name="eve_startdate" class="form-control" 
                        value="<fmt:formatDate value="${eventVO.eve_startdate}" pattern="yyyy-MM-dd HH:mm"/>" readonly>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6">
                    <div class="form-group">
                        <label for="eve_enddate">活動結束日期時間</label>
                        <input type="text" id="eve_enddate" name="eve_enddate" class="form-control" 
                        value="<fmt:formatDate value="${eventVO.eve_enddate}" pattern="yyyy-MM-dd HH:mm"/>" readonly> 
                    </div>
                </div>
            </div>           
            
            <div class="row">
                <div class="col-xs-12 col-sm-6">
                     <div class="form-group">
                         <label for="eve_onsaledate">售票開始日期時間</label>
                         <input type="text" id="eve_onsaledate" name="eve_onsaledate" class="form-control" 
                         value="<fmt:formatDate value="${eventVO.eve_onsaledate}" pattern="yyyy-MM-dd HH:mm"/>" readonly>
                     </div>
                </div>
                <div class="col-xs-12 col-sm-6">
                     <div class="form-group">
                         <label for="eve_offsaledate">售票結束日期時間</label>
                         <input type="text" id="eve_offsaledate" name="eve_offsaledate" class="form-control"
                         value="<fmt:formatDate value="${eventVO.eve_offsaledate}" pattern="yyyy-MM-dd HH:mm"/>" readonly>
                     </div>
                </div>
            </div>
                     
            <div class="row">
                <div class="col-xs-12 col-sm-3">
                     <div class="form-group">
                         <label>購買張數限制</label>                             
                         <select class="form-control" name="ticlimit" readonly>
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
                         <select class="form-control" name="eve_status" readonly>
                         	<option value="normal" ${(eventVO.eve_status == 'normal') ? 'selected' : '' }>正常</option>
                         	<option value="cancel" ${(eventVO.eve_status == 'cancel') ? 'selected' : '' }>取消</option>
                         </select>     
                     </div>
                </div>
                <div class="col-xs-12 col-sm-6">
                     <div class="form-group">
                         <label for="fullrefundenddate">可全額退款到期日期時間</label>
                         <input type="text" id="fullrefundenddate" name="fullrefundenddate" class="form-control"
                         value="<fmt:formatDate value="${eventVO.fullrefundenddate}" pattern="yyyy-MM-dd HH:mm"/>" readonly>
                     </div>
                </div>
            </div>     

			<div class="row">
                <div class="col-xs-12 col-sm-6">
                	<div class="form-group">
						<label>座位圖</label>
                    	<img src="<%= request.getContextPath()%>/event/EventGifReader?scaleSize=850&eve_no=${eventVO.eve_no}">
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
													總張數 : ${SeatingAreaVO.tictotalnumber},&nbsp;&nbsp;
													已訂張數 : ${SeatingAreaVO.ticbookednumber},&nbsp;&nbsp;
													剩餘張數 : ${SeatingAreaVO.tictotalnumber - SeatingAreaVO.ticbookednumber}
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
				<a class="btn btn-info" href="<%=request.getContextPath()%>/backend/event_title/listAllEventTitleRelatives.jsp?evetit_no=${eventVO.evetit_no}&eve_no=${eventVO.eve_no}">回到活動總覽</a>
			</span>
			<form method="post" action="<%=request.getContextPath()%>/event/EventServlet.do" class="actionForm">								
			    <input type="hidden" name="eve_no"            value="${eventVO.eve_no}">
			    <input type="hidden" name="requestURL"	      value="<%=request.getServletPath()%>">
			    <input type="hidden" name="action"	          value="getOneEvent_For_Update">
			    <input type="submit" value="修改" class="btn btn-warning"> 							
			</form>	
        </div>

    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        $("#flip").click(function() {
            $("#panel").slideToggle("fast");
        });     
    });
    </script>
</body>

</html>