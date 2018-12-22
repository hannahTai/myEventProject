<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.EVENT_TITLE.model.*"%>
<%@ page import="com.EVENT_TITLE.controller.*"%>

<% 
	EventTitleService eventTitleService = new EventTitleService();
	List<EventTitleVO> list = eventTitleService.getAllLaunched();
	pageContext.setAttribute("eventTitleList", list); 
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>瀏覽所有活動</title>
    <!-- Basic -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- datetimepicker -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.datetimepicker.css" />
    <style>
        @media only screen and (min-width : 481px) {
    .flex-row.row {
        display: flex;
        flex-wrap: wrap;
    }
    .flex-row.row>[class*='col-'] {
        display: flex;
        flex-direction: column;
    }
    .flex-row.row:after, .flex-row.row:before {
        display: flex;
    }
}

.flex-row .thumbnail, .flex-row .caption {
    flex: 1 0 auto;
    flex-direction: column;
}

.flex-text {
    flex-grow: 1
}

.flex-row img {
    height: auto;
    width: 100%
}
</style>
</head>

<body>
    <div class="container">
    	<!-- 
        <div class="searchArea">
            <div class="form-group">
                <label for="evetit_name">活動主題名稱</label> <input type="text" name="evetit_name" id="evetit_name" placeholder="請輸入活動主題名稱" class="form-control" value="">
            </div>
            <div class="row">
                <div class="col-xs-12 col-sm-6">
                    <div class="form-group">
                        <label for="evetit_startdate">開始日期</label> <input type="text" id="evetit_startdate" name="evetit_startdate" class="form-control" value="" pattern="yyyy-MM-dd">
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6">
                    <div class="form-group">
                        <label for="evetit_enddate">結束日期</label> <input type="text" id="evetit_enddate" name="evetit_enddate" class="form-control" value="" pattern="yyyy-MM-dd">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-sm-10">
                    <div class="form-group">
                        <button type="submit" class="btn btn-info">全部</button>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-2">
                    <div class="form-group text-right">
                        <button type="submit" class="btn btn-primary">查詢</button>
                    </div>
                </div>
            </div>
        </div>
         -->
        <div class="row flex-row">
            <c:forEach var="eventTitleVO" items="${eventTitleList}">
                <div class="col-xs-12 col-sm-4">
                    <div class="thumbnail">
                        <a href="<%= request.getContextPath()%>/front-end/EVENT_TITLE/listOneEventTitle.jsp?evetit_no=${eventTitleVO.evetit_no}">
                            <img src="<%= request.getContextPath()%>/EVENT_TITLE/EventTitleGifReader?scaleSize=425&evetit_no=${eventTitleVO.evetit_no}" alt="">
                        </a>
                        
                        
                        <div class="caption">
                            <p class="flex-text">${eventTitleVO.evetit_startdate}
                                ${(eventTitleVO.evetit_startdate == eventTitleVO.evetit_enddate) ? '' : ' ~ '}
                                ${(eventTitleVO.evetit_startdate == eventTitleVO.evetit_enddate) ? '' : eventTitleVO.evetit_enddate}
                                <a
                                    href="<%= request.getContextPath()%>/front-end/EVENT_TITLE/listOneEventTitle.jsp?evetit_no=${eventTitleVO.evetit_no}">
                                    <h4>${eventTitleVO.evetit_name}</h4>
                                </a>
                        </div>
                    
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- datetimepicker -->
    <script src="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.datetimepicker.full.js"></script>
    <!-- JavaScript in File -->
    <script src="<%=request.getContextPath()%>/front-end/EVENT_TITLE/js/listAllEventTitle.js"></script>
</body>

</html>