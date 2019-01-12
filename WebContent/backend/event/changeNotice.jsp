<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="eventTitleService" scope="page" class="com.event_title.model.EventTitleService" />	
<jsp:useBean id="eventService" scope="page" class="com.event.model.EventService" />	

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>異動通知</title>
    <!-- Basic -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	<!-- dataTables -->
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
	<style>
 		body{
			font-family:微軟正黑體!important;
		}
		 #ajaxMsgsNavbar {
	        left: 80%;
	        border-radius: 5px;
	        background-color: #D3D3D3;
	        opacity: 0.8;
	    }
	    .paramNamesRow h4{
	    	background-color: rgba(51, 122, 183, 0.2);;
	    	border-radius: 1px;
	    }
    </style>
</head>

<body>



	<jsp:include page="/backend/navbar_back-end.jsp" flush="true" />
	
	<div class="container">
		<span class="text-danger">${eventErrorMsgs.Exception}</span>
		<input type="hidden" id="projectName" name="projectName" value="<%=request.getContextPath() %>" readonly>
		<input type="hidden" id="changeNoticeSuccess" name="changeNoticeSuccess" value="${changeNoticeSuccess}" readonly>
	</div>



    <div class="container" style="margin-bottom:30px;">
        <div class="row">
        	<form method="post" action="<%=request.getContextPath()%>/event/EventServlet.do">	
            <div class="col-xs-12 col-sm-6">
                <div class="form-group">
                    <label>異動通知對象</label>
                    <select class="form-control" name="evetit_no" id="selectedEventTitle_forCopyChoose">
                        <option value="pleaseChoose">
							請選擇活動主題
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
					<select class="form-control" name="eve_no" id="selectedEvent_forCopyChoose">
					</select>
                </div>
            </div>
            <div class="col-xs-2 col-sm-1">
                <span class="form-group">
					<label style="color:white;">theHiddenEvent</label>					
				    <input type="hidden" name="requestURL"	   value="<%=request.getServletPath()%>">
				    <input type="hidden" name="action"	       value="getTicketOrders_byEvent">
				    <button type="submit" class="btn btn-success" style="float:right;">搜尋</button> 							
				</span>
            </div>
            </form>	
        </div>
		<br>



		<div class="row paramNamesRow">
			<div class="col-xs-12 col-sm-6">
				<c:if test="${param.evetit_no != ''}">
					<h4>${eventTitleService.getOneEventTitle(param.evetit_no).evetit_name}</h4>
				</c:if>
			</div>		
			<div class="col-xs-12 col-sm-6">
				<c:if test="${param.eve_no != ''}">
					<c:if test="${param.eve_no == 'allEvents'}">
						<h4>全部場次</h4>
					</c:if>
					<c:if test="${param.eve_no != 'allEvents'}">
						<h4>${eventService.getOneEvent(param.eve_no).eve_sessionname}</h4>
					</c:if>
				</c:if>
			</div>
		</div>
                
             
                
        <table class="display" style="width:100%" id="memberAffected">
            <thead>
                <tr>
                    <th>票券編號</th>
                    <th>會員名字</th>
                    <th>購買票價</th>
                    <th>電子信箱</th>
                    <th>手機號碼</th>
                </tr>
            </thead>
            <tbody>
            	<jsp:useBean id="ticketOrderService" scope="page" class="com.ticketorder.model.TicketOrderService" />	
            	<jsp:useBean id="memberService" scope="page" class="com.member.model.MemberService" />
           		<c:forEach var="ticketVOset" items="${ticketVOset}">
           		<c:if test="${ticketVOset.ticket_status == 'ACTIVE1'}">
                <tr>
                	<td style="vartical-align:middle;">${ticketVOset.ticket_no}</td>
                	<td style="vartical-align:middle;">${memberService.getOneMember(ticketVOset.member_no).memberFullname}</td>
                	<td style="vartical-align:middle;">${ticketOrderService.getOneTicketOrder(ticketVOset.ticket_order_no).total_price}</td>
                	<td style="vartical-align:middle;">${memberService.getOneMember(ticketVOset.member_no).email}</td>
                	<td style="vartical-align:middle;">${memberService.getOneMember(ticketVOset.member_no).phone}</td>
                </tr>
                </c:if>
                </c:forEach>
            </tbody>
        </table>
        
        
        
        <br>
        <form method="post" action="<%=request.getContextPath()%>/event/EventServlet.do" id="changeNoticeSendNoticesForm">	
        <div class="form-group">
            <label for="phoneMessageText">簡訊內容(不得超過50字)</label>
            <textarea class="form-control" rows="1" name="phoneMessageText" id="phoneMessageText" maxlength="50">${param.phoneMessageText}</textarea>
        </div>
        <div class="form-group">
            <label for="subject">電子郵件主旨</label>
            <input type="text" name="subject" id="subject" class="form-control" value="${param.subject}">
        </div>
        <div class="form-group">
            <label for="messageText">電子郵件內容</label>
            <textarea class="form-control" rows="15" name="messageText" id="messageText">${param.messageText}</textarea>
        </div>
        <span class="form-group">
       		<input type="hidden" name="evetit_no"	 value="${param.evetit_no}">
        	<input type="hidden" name="eve_no"	     value="${param.eve_no}">
        	<input type="hidden" name="requestURL"   value="<%=request.getServletPath()%>">
			<input type="hidden" name="action"       value="changeNoticeSendNotices">
			<button type="button" class="btn btn-primary" style="margin-top:15px;" id="changeNoticeSendNotices">送出</button>
		</span>
		</form>
    </div>
    
    
    
    <!-- --------------------=====----- ///ajaxBack/// -----=====-------------------- -->    
	<nav class="navbar navbar-default navbar-fixed-bottom" role="navigation" style="display:none" id="ajaxMsgsNavbar">
		<div id="ajaxMsgs" style="margin:5px;"></div>
	</nav>
    
    
    
	<!-- Basic -->
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- dataTables -->
	<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
	<script src="https://cdn.datatables.net/plug-ins/1.10.19/api/page.jumpToData().js"></script>
	<!-- sweetalert -->
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script>
    $(document).ready(function() {

        var memberAffected = $('#memberAffected').DataTable();
        
        if($("#changeNoticeSuccess").val() == 'changeNoticeSuccess'){
        	var projectName = $("#projectName").val();
			$("#changeNoticeSendNotices").hide();
			swal({
				title: "寄送通知完成",
				icon: "success",
			})
			.then((value) => {
        		window.location.href = projectName + "/backend/event_title/listAllEventTitleRelatives.jsp";
        	});	
        }
        
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
	        $.ajax({
				type: 'post',
	            url: url,
	            data: data,
	            success: function(data) {
	            	console.log(data);
	            	if(data.indexOf("失") != -1){
	            		$("#ajaxMsgs").html(data);
	                	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();                
	            	} else {
	                	$("#ajaxMsgs").html("  ### 取得活動成功 ");
	                	$("#ajaxMsgsNavbar").show().delay(5000).fadeOut();
	                	var eventVoList = JSON.parse(data);
	                	$("#selectedEvent_forCopyChoose").empty();
	                	$("#selectedEvent_forCopyChoose").append("<option value='allEvents'>全部場次</option>");
	                	for(var i = 0; i < eventVoList.length; i++){
	                		$("#selectedEvent_forCopyChoose").append("<option value='" + eventVoList[i].eve_no + "'>" + eventVoList[i].eve_sessionname + "</option>");
	                	}
	            	}
	            }
	        });
        });
        
        $("#changeNoticeSendNotices").click(function(e){
        	var subject = $("#subject").val();
        	var messageText = $("#messageText").val();
        	var phoneMessageText = $("#phoneMessageText").val();
        	
        	if(subject == null || subject.length == 0 || messageText == null || messageText.length == 0 || phoneMessageText == null || phoneMessageText.length == 0){
        		swal("主旨與內容不得為空");
        	} else {
        		$("#changeNoticeSendNoticesForm").submit();
        		swal({
        			title: "寄送通知中...",
					icon: "info",
					button: false,
				});
        	}
        });

    });
    </script>
</body>

</html>