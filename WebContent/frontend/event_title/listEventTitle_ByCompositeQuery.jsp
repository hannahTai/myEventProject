<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.event_title.model.*"%>
<%@ page import="com.event_title.controller.*"%>

<%
	if(request.getAttribute("listEventTitle_ByCompositeQuery") == null){
		EventTitleService eventTitleService = new EventTitleService();
		List<EventTitleVO> list = eventTitleService.getAllLaunched();
		pageContext.setAttribute("listEventTitle_ByCompositeQuery", list); 
	}
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
        <div class="row flex-row">
            <c:forEach var="eventTitleVO" items="${listEventTitle_ByCompositeQuery}">
                <div class="col-xs-12 col-sm-4">
                    <div class="thumbnail">
                        <a href="<%= request.getContextPath()%>/frontend/event_title/listOneEventTitle.jsp?evetit_no=${eventTitleVO.evetit_no}" target="_blank">
                            <img src="<%= request.getContextPath()%>/event_title/EventTitleGifReader?scaleSize=425&evetit_no=${eventTitleVO.evetit_no}" alt="">
                        </a>                       
                        <div class="caption">
                            <p class="flex-text">${eventTitleVO.evetit_startdate}
                                ${(eventTitleVO.evetit_startdate == eventTitleVO.evetit_enddate) ? '' : ' ~ '}
                                ${(eventTitleVO.evetit_startdate == eventTitleVO.evetit_enddate) ? '' : eventTitleVO.evetit_enddate}
                            </p>
                            <h4><a href="<%= request.getContextPath()%>/frontend/event_title/listOneEventTitle.jsp?evetit_no=${eventTitleVO.evetit_no}" target="_blank">
                            	${eventTitleVO.evetit_name}                   
                            </a></h4>
                        </div>                   
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>