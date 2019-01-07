<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="com.event_title.model.*"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>新增活動主題</title>
    <!-- Basic -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- datetimepicker -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.datetimepicker.css" />
    
    <style>
        .evetit_poster_area img {
	        width: 100%;
	    }
    	body{
			font-family:微軟正黑體!important;
		}
    </style>
</head>

<body>



		<jsp:include page="/backend/navbar_back-end.html" flush="true" />



		<div class="container">
			<span class="text-danger">${eventTitleErrorMsgs.Exception}</span>
		</div>

    	<div class="container" style="margin-bottom:30px;">    	
	        <form method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/event_title/EventTitleServlet.do">        
	           
	            <div class="form-group">
	                <label for="evetit_name">活動主題名稱</label>
	                <span class="text-danger">${eventTitleErrorMsgs.evetit_name}</span>
	                <input type="text" name="evetit_name" id="evetit_name" class="form-control" value="${param.evetit_name}">	                
	            </div>
	            
	            <jsp:useBean id="ticketRefundPolicyService" scope="page" class="com.ticket_refund_policy.model.TicketRefundPolicyService" />            
	            <div class="form-group">
	                <label>退款政策</label>
	                <select class="form-control" name="ticrefpolicy_no">
	                    <c:forEach var="ticketRefundPolicyVO" items="${ticketRefundPolicyService.all}">
	                        <option value="${ticketRefundPolicyVO.ticRefPolicy_no}" ${(ticketRefundPolicyVO.ticRefPolicy_no == param.ticrefpolicy_no) ? 'selected' : '' }>
	                            ${ticketRefundPolicyVO.ticRefPolicy_name} : ${ticketRefundPolicyVO.ticRefPolicy_content}
	                        </option>
	                    </c:forEach>
	                </select>
	            </div>
	                        
	            <div class="row">
	                <div class="col-xs-12 col-sm-6">
	                    <div class="form-group">
	                        <label for="evetit_startdate">開始日期</label>
	                        <span class="text-danger">${eventTitleErrorMsgs.evetit_startdate}</span>
	                        <input type="text" id="evetit_startdate" name="evetit_startdate" class="form-control" value="${param.evetit_startdate}">	                        
	                    </div>
	                </div>
	                <div class="col-xs-12 col-sm-6">
	                    <div class="form-group">
	                        <label for="evetit_enddate">結束日期</label>
	                        <span class="text-danger">${eventTitleErrorMsgs.evetit_enddate}</span>
	                        <span class="text-danger">${eventTitleErrorMsgs.evetit_enddate_BiggerThanToday}</span>
	                        <span class="text-danger">${eventTitleErrorMsgs.evetit_enddate_BiggerThanEvetitStartdate}</span>
	                        <input type="text" id="evetit_enddate" name="evetit_enddate" class="form-control" value="${param.evetit_enddate}"> 
	                	                    	
	                    </div>
	                </div>
	            </div>           
	            
	            <div class="row">
	                <div class="col-xs-12 col-sm-6">
	                     <div class="form-group">
	                         <label for="launchdate">上架日期</label>
	                         <span class="text-danger">${eventTitleErrorMsgs.launchdate}</span>
	                         <input type="text" id="launchdate" name="launchdate" class="form-control" value="${param.launchdate}">
	                     </div>
	                </div>
	                <div class="col-xs-12 col-sm-6">
	                     <div class="form-group">
	                         <label for="offdate">下架日期</label>
	                         <span class="text-danger">${eventTitleErrorMsgs.offdate}</span>
	                         <span class="text-danger">${eventTitleErrorMsgs.offdate_BiggerThanToday}</span>
	                         <span class="text-danger">${eventTitleErrorMsgs.offdate_BiggerThanLaunchdate}</span>
	                         <input type="text" id="offdate" name="offdate" class="form-control" value="${param.offdate}">
	                     </div>
	                </div>
	            </div>
	            
	            <div class="row">           
	                <div class="col-xs-12 col-sm-3">
	                    <jsp:useBean id="eventClassificationService" scope="page" class="com.event_classification.model.EventClassificationService" />              
	                    <div class="form-group">
	                        <label>分類</label>
	                        <select class="form-control" name="eveclass_no">
	                            <c:forEach var="eventClassificationVO" items="${eventClassificationService.all}">
	                                <option value="${eventClassificationVO.eveclass_no}" ${(eventClassificationVO.eveclass_no == param.eveclass_no) ? 'selected' : '' }>
	                                    ${eventClassificationVO.eveclass_name}
	                                </option>
	                            </c:forEach>
	                        </select>
	                    </div>
	                </div>           
	                <div class="col-xs-12 col-sm-3">
	                     <div class="form-group">
	                         <label>推銷熱度</label>                         
	                         <select class="form-control" name="promotionranking">
	                         	<option value="1" ${(param.promotionranking == "1") ? 'selected' : '' }>1</option>
	                         	<option value="2" ${(param.promotionranking == "2") ? 'selected' : '' }>2</option>
	                         	<option value="3" ${(param.promotionranking == "3") ? 'selected' : '' }>3</option>
	                         	<option value="4" ${(param.promotionranking == "4") ? 'selected' : '' }>4</option>
	                         	<option value="5" ${(param.promotionranking == "5") ? 'selected' : '' }>5</option>
	                         </select>                      
	                     </div>
	                </div>             
	                <div class="col-xs-12 col-sm-3">
	                    <div class="form-group">
	                         <label>狀態</label>	                                                 
	                         <select class="form-control" name="evetit_status" readonly>
	                         	<option value="temporary">暫存</option>
	                         </select>                					
	                    </div>
	                </div>
	                <div class="col-xs-12 col-sm-3">
	                	<div class="form-group">
	                         <label>場次數量</label>                         
	                         <input type="text" name="evetit_sessions" id="evetit_sessions" class="form-control" value="0" readonly>                      
	                     </div>
	                </div>
	            </div>
	            
				<div class="form-group evetit_poster_area">
					<label for="evetit_poster">主海報</label>
					<span class="text-danger">${eventTitleErrorMsgs.evetit_poster}</span>
					<input type="file" id="evetit_poster" name="evetit_poster" class="form-control" accept="image/*">
					<input type="hidden" id="evetit_poster_status" name="evetit_poster_status" value="${(evetit_poster_status == 'alreadyUpload') ? 'alreadyUpload' : 'noUpload'}">
					<img src="${evetit_poster_path}" id="evetit_poster_preview">
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
	                    <div class="tab-pane active" id="infoTab">
		                	<textarea name="info" id="infoEditor">
		                		${param.info}
		               		</textarea>
	                    </div>
	                    <div class="tab-pane" id="noticeTab">
	                    	<textarea name="notices" id="noticesEditor">
	                    		${param.notices}
	                 		</textarea>
	                    </div>
	                    <div class="tab-pane" id="eticpurchaserulesTab">
	                        <textarea name="eticpurchaserules" id="eticpurchaserulesEditor">
	                        	${param.eticpurchaserules}
	                        </textarea>
	                    </div>
	                    <div class="tab-pane" id="eticrulesTab">
	                        <textarea name="eticrules" id="eticrulesEditor">
	                        	${param.eticrules}
	                        </textarea>
	                    </div>
	                    <div class="tab-pane" id="refundrulesTab">
	                    	<textarea name="refundrules" id="refundrulesEditor">
	                            ${param.refundrules}
	                    	</textarea>
	                    </div>
	                </div>
	            </div>
	            
				<span class="form-group">
					<button type="submit" class="btn btn-success" name="action" value="insertEventTitle" style="margin-top:15px;">新增</button>
					<a class="btn btn-info" href="<%=request.getContextPath()%>/backend/event_title/listAllEventTitleRelatives.jsp" style="margin-top:15px;">回活動總覽</a>
				</span>	
						
			</form>
        </div>



    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- ckEditor JS -->
    <script src="<%=request.getContextPath()%>/vendor/ckeditor_full/ckeditor.js"></script>
    <!-- datetimepicker -->
    <script src="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.datetimepicker.full.js"></script>
    <!-- JavaScript in File -->
    <script src="<%=request.getContextPath()%>/backend/event_title/js/eventTitleCKEditor.js"></script>
    <script src="<%=request.getContextPath()%>/backend/event_title/js/addEventTitle.js"></script>
    <!-- JavaScript in HTML -->
    <script type="text/javascript">
    $(function() {
        initInfoEditor();
        initNoticesEditor();
        initEticpurchaserulesEditor();
        initEticrulesEditor();
        initRefundrulesEditor();

        $("#evetit_poster").change(function() {
            imagesPreview(this);
            $("#evetit_poster_status").attr("value", "yesUpload");
        });
        
        $(".text-danger").each(function(){
        	var errorMsg = $(this).text();
        	if( errorMsg.trim() != "" ){
        		$(this).prepend("<i class='glyphicon glyphicon-triangle-left'></i>");
        	}        	
        });
               
    	localStorage.removeItem("DataTables_eventTitleListTable");     
    });
    
    
    </script>
</body>

</html>