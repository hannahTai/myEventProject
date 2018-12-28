<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.event_title.model.*"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>所有活動</title>
    <!-- Basic -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- datetimepicker -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.datetimepicker.css" />
</head>

<style>
	.checkboxCusStyle {
		padding: 3px;
	    border: 1px solid transparent;
	    border-radius: 4px;
	    color: #31708f;
	    background-color: #d9edf7;
	    border-color: #bce8f1;
	    width: 80px;
	    text-align: center;
	}
</style>

<body>

	<div class="container">
		<span class="text-danger">${eventTitleErrorMsgs.Exception}</span>
	</div>

    <div class="container">
		<form method="post" action="<%=request.getContextPath()%>/event_title/EventTitleServlet.do">
            <div class="form-group">
                <label for="evetit_name">活動主題名稱</label>
                <input type="text" name="evetit_name" id="evetit_name" placeholder="請輸入活動主題名稱" class="form-control" value="">
            </div>
            <div class="row">
                <div class="col-xs-12 col-sm-6">
                    <div class="form-group">
                        <label for="evetit_startdate">開始日期</label>
                        <input type="text" name="evetit_startdate" id="evetit_startdate" placeholder="請選擇開始日期" class="form-control" value="">
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6">
                    <div class="form-group">
                        <label for="evetit_enddate">結束日期</label>
                        <input type="text" name="evetit_enddate" id="evetit_enddate" placeholder="請選擇結束日期" class="form-control" value="">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-11 hidden-xs hidden-sm">                          
                	<jsp:useBean id="eventClassificationService" scope="page" class="com.event_classification.model.EventClassificationService" />
                	<c:forEach var="eventClassificationVO" items="${eventClassificationService.all}">
	       				<div class="checkbox-inline checkboxCusStyle">
	            			<label>
	                			<input type="checkbox" name="eveclass_no" value="${eventClassificationVO.eveclass_no}" id="${eventClassificationVO.eveclass_no}" checked>
	       						${eventClassificationVO.eveclass_name}
	            			</label>
	       				</div>
                	</c:forEach>
                </div>               
                <div class="col-xs-12 col-sm-12 col-md-1">
                    <div class="form-group text-right">
                    	<input type="hidden" name="action" value="listEventTitle_ByCompositeQuery">
                        <button type="submit" class="btn btn-primary" id="search">查詢</button>
                    </div>
                </div>
            </div>
		</form>
    </div>
    
    <jsp:include page="/frontend/event_title/listEventTitle_ByCompositeQuery.jsp" flush="true" />
    
    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- datetimepicker -->
    <script src="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.datetimepicker.full.js"></script>
    <!-- JavaScript in File -->
    <script src="<%=request.getContextPath()%>/frontend/event_title/js/selectEventTitle.js"></script>
    <script> 
    
    
	
    $(document).ready(function() {

    	
    	
        var searchStatusBack = JSON.parse(localStorage.getItem('searchStatus'));
        if(searchStatusBack != null){
            $("#evetit_name").val(searchStatusBack.evetit_name);
            $("#evetit_startdate").val(searchStatusBack.evetit_startdate); 
            $("#evetit_enddate").val(searchStatusBack.evetit_enddate);
            
            $("#A").prop("checked", searchStatusBack.A);
            $("#B").prop("checked", searchStatusBack.B);
            $("#C").prop("checked", searchStatusBack.C);
            $("#D").prop("checked", searchStatusBack.D);
            $("#E").prop("checked", searchStatusBack.E);
            $("#F").prop("checked", searchStatusBack.F);
            $("#G").prop("checked", searchStatusBack.G);
            $("#H").prop("checked", searchStatusBack.H);
            $("#I").prop("checked", searchStatusBack.I);
            $("#J").prop("checked", searchStatusBack.J);
        }
    	
    

        $("#search").click(function() {
        	var searchStatus = new Object(); 
        	searchStatus.evetit_name = $("#evetit_name").val();
        	searchStatus.evetit_startdate = $("#evetit_startdate").val();
        	searchStatus.evetit_enddate = $("#evetit_enddate").val();
        	
        	searchStatus.A = $("#A").prop("checked");
        	searchStatus.B = $("#B").prop("checked");
        	searchStatus.C = $("#C").prop("checked");
        	searchStatus.D = $("#D").prop("checked");
        	searchStatus.E = $("#E").prop("checked");
        	searchStatus.F = $("#F").prop("checked");
        	searchStatus.G = $("#G").prop("checked");
        	searchStatus.H = $("#H").prop("checked");
        	searchStatus.I = $("#I").prop("checked");
        	searchStatus.J = $("#J").prop("checked");
        	       	
            var searchStatusJSON = JSON.stringify(searchStatus);
            localStorage.setItem("searchStatus", searchStatusJSON);
        });

        
        
    });    
	</script>
    
    
</body>

</html>