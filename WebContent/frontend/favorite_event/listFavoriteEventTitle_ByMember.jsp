<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.event_title.model.*"%>
<%@ page import="com.event_title.controller.*"%>

<%@ page import="com.favorite_event.model.*"%>

<%
// 	String member_no = request.getParameter("member_no");
	String member_no = "M000001";
	FavoriteEventService favoriteEventService = new FavoriteEventService();
	List<FavoriteEventVO> list = favoriteEventService.findFavoriteEventByMember(member_no);
	pageContext.setAttribute("listFavoriteEventTitle_ByMember", list); 
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>瀏覽最愛活動</title>
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
		body{
			font-family:微軟正黑體!important;
		}
	</style>
</head>
<body>


	
    <div class="container">
        <div class="row flex-row">
            <c:forEach var="favoriteEventVO" items="${listFavoriteEventTitle_ByMember}">
                <div class="col-xs-12 col-sm-4">
                    <div class="thumbnail">
                    	<jsp:useBean id="eventTitleService" scope="page" class="com.event_title.model.EventTitleService" />
                        <a href="<%= request.getContextPath()%>/frontend/event_title/listOneEventTitle.jsp?evetit_no=${favoriteEventVO.evetit_no}" target="_blank">
                            <img src="<%= request.getContextPath()%>/event_title/EventTitleGifReader?scaleSize=425&evetit_no=${favoriteEventVO.evetit_no}" alt="">
                        </a>                       
                        <div class="caption">
                            <h4><a href="<%= request.getContextPath()%>/frontend/event_title/listOneEventTitle.jsp?evetit_no=${favoriteEventVO.evetit_no}" target="_blank">
                            	${eventTitleService.getOneEventTitle(favoriteEventVO.evetit_no).evetit_name}                   
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