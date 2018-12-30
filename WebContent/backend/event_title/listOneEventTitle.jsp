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
    <title>${eventTitleVO.evetit_name}</title>
    <!-- Basic -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <style>
        .container img {
     	   width: 100%;
    	}
    	.actionForm{
			display: inline;
		}
    </style>
</head>

<body>
    	<div class="container">
	            
            <div class="form-group">
                <label for="evetit_no">活動主題編號</label>
                <input type="text" name="evetit_no" id="evetit_no" class="form-control" value="${eventTitleVO.evetit_no}" readonly>
            </div>	        
    		    
            <div class="form-group">
                <label for="evetit_name">活動主題名稱</label>
                <input type="text" name="evetit_name" id="evetit_name" class="form-control" value="${eventTitleVO.evetit_name}" readonly>
            </div>
            
            <jsp:useBean id="ticketRefundPolicyService" scope="page" class="com.ticket_refund_policy.model.TicketRefundPolicyService" />            
            <div class="form-group">
                <label>退款政策</label>
                <select class="form-control" name="ticrefpolicy_no" readonly>
                    <c:forEach var="ticketRefundPolicyVO" items="${ticketRefundPolicyService.all}">
                        <option value="${ticketRefundPolicyVO.ticRefPolicy_no}" ${(ticketRefundPolicyVO.ticRefPolicy_no == eventTitleVO.ticrefpolicy_no) ? 'selected' : '' }>
                            ${ticketRefundPolicyVO.ticRefPolicy_name} : ${ticketRefundPolicyVO.ticRefPolicy_content}
                        </option>
                    </c:forEach>
                </select>
            </div>
                        
            <div class="row">
                <div class="col-xs-12 col-sm-6">
                    <div class="form-group">
                        <label for="evetit_startdate">開始日期</label>
                        <input type="text" id="evetit_startdate" name="evetit_startdate" class="form-control" 
                        value="<fmt:formatDate value="${eventTitleVO.evetit_startdate}" pattern="yyyy-MM-dd"/>" readonly>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6">
                    <div class="form-group">
                        <label for="evetit_enddate">結束日期</label>
                        <input type="text" id="evetit_enddate" name="evetit_enddate" class="form-control" 
                        value="<fmt:formatDate value="${eventTitleVO.evetit_enddate}" pattern="yyyy-MM-dd"/>" readonly> 
                    </div>
                </div>
            </div>           
            
            <div class="row">
                <div class="col-xs-12 col-sm-6">
                     <div class="form-group">
                         <label for="launchdate">上架日期</label>
                         <input type="text" id="launchdate" name="launchdate" class="form-control" 
                         value="<fmt:formatDate value="${eventTitleVO.launchdate}" pattern="yyyy-MM-dd"/>" readonly>
                     </div>
                </div>
                <div class="col-xs-12 col-sm-6">
                     <div class="form-group">
                         <label for="offdate">下架日期</label>
                         <input type="text" id="offdate" name="offdate" class="form-control"
                         value="<fmt:formatDate value="${eventTitleVO.offdate}" pattern="yyyy-MM-dd"/>" readonly>
                     </div>
                </div>
            </div>
            
            <div class="row">           
                <div class="col-xs-12 col-sm-3">
                    <jsp:useBean id="eventClassificationService" scope="page" class="com.event_classification.model.EventClassificationService" />              
                    <div class="form-group">
                        <label>分類</label>
                        <select class="form-control" name="eveclass_no" readonly>
                            <c:forEach var="eventClassificationVO" items="${eventClassificationService.all}">
                                <option value="${eventClassificationVO.eveclass_no}" ${(eventClassificationVO.eveclass_no == eventTitleVO.eveclass_no) ? 'selected' : '' }>
                                    ${eventClassificationVO.eveclass_name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>           
                <div class="col-xs-12 col-sm-3">
                     <div class="form-group">
                         <label>推銷熱度</label>                           
                         <select class="form-control" name="promotionranking" readonly>
                         	<option value="1" ${(eventTitleVO.promotionranking == "1") ? 'selected' : '' }>1</option>
                         	<option value="2" ${(eventTitleVO.promotionranking == "2") ? 'selected' : '' }>2</option>
                         	<option value="3" ${(eventTitleVO.promotionranking == "3") ? 'selected' : '' }>3</option>
                         	<option value="4" ${(eventTitleVO.promotionranking == "4") ? 'selected' : '' }>4</option>
                         	<option value="5" ${(eventTitleVO.promotionranking == "5") ? 'selected' : '' }>5</option>
                         </select>                                     
                     </div>
                </div>             
                <div class="col-xs-12 col-sm-3">         
                    <div class="form-group">
                         <label>儲存狀態</label>                             
                         <select class="form-control" name="evetit_status" readonly>
                         	<option value="temporary" ${(eventTitleVO.evetit_status == 'temporary') ? 'selected' : '' }>暫存</option>
                         	<option value="comfirmed" ${(eventTitleVO.evetit_status == 'confirmed') ? 'selected' : '' }>確定</option>
                         	<option value="cancel" ${(eventTitleVO.evetit_status == 'cancel') ? 'selected' : '' }>取消</option>
                         </select>                	
                    </div>
                </div>
                <div class="col-xs-12 col-sm-3">
                	<div class="form-group">
                         <label>場次數量</label>                         
                         <input type="text" name="evetit_sessions" id="evetit_sessions" class="form-control" value="${eventTitleVO.evetit_sessions}" readonly>                      
                     </div>
                </div>
            </div>
                     
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-9">
                    <h4>${eventTitleVO.evetit_name}</h4>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-3 text-right">	            	
            		<span class="form-group">
						<a class="btn btn-info" href="<%=request.getContextPath()%>/backend/event_title/listAllEventTitleRelatives.jsp?evetit_no=${eventTitleVO.evetit_no}">回到活動總覽</a>
					</span>
					<form method="post" action="<%=request.getContextPath()%>/event_title/EventTitleServlet.do" class="actionForm">								
					    <input type="hidden" name="evetit_no"   value="${eventTitleVO.evetit_no}">
					    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
					    <input type="hidden" name="action"	    value="getOneEventTitle_For_Update">
					    <input type="submit" value="修改" class="btn btn-warning"> 							
					</form>	
                </div>
            </div>
             
            <img src="<%= request.getContextPath()%>/event_title/EventTitleGifReader?scaleSize=850&evetit_no=${eventTitleVO.evetit_no}">
            
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
                    <div class="tab-pane active" id="infoTab">${eventTitleVO.info}</div>
                    <div class="tab-pane" id="noticeTab">${eventTitleVO.notices}</div>
                    <div class="tab-pane" id="eticpurchaserulesTab">${eventTitleVO.eticpurchaserules}</div>
                    <div class="tab-pane" id="eticrulesTab">${eventTitleVO.eticrules}</div>
                    <div class="tab-pane" id="refundrulesTab">${eventTitleVO.refundrules}</div>
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
    });
    </script>
</body>

</html>