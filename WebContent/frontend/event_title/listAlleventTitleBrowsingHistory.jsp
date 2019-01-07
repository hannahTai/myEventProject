<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Title Page</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- Owl Carousel -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/OwlCarousel2-2.3.4/docs/assets/owlcarousel/assets/owl.carousel.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/OwlCarousel2-2.3.4/docs/assets/owlcarousel/assets/owl.theme.default.min.css">
</head>
<body>



	<div class="container">
		<div class="panel panel-info">
			<div class="panel-heading">
			    <span class="panel-title">活動瀏覽紀錄</span>
			    <a href="" id="cleanEventTitleBrowsingHistory" style="float:right;">清除</a>
			</div>			
			<div class="panel-body">
				<div class="owl-carousel owl-theme eventTitleBrowsingHistoryOwlArea"></div>
			</div>
		</div>
	</div>



    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Owl Carousel -->
    <script src="<%=request.getContextPath()%>/vendor/OwlCarousel2-2.3.4/docs/assets/owlcarousel/owl.carousel.js"></script>
    <script>
    $(document).ready(function() {
    	
    	var eventTitleBrowsingHistoryArray = JSON.parse(localStorage.getItem("eventTitleBrowsingHistory"));

    	if(eventTitleBrowsingHistoryArray.length != 0){
        	eventTitleBrowsingHistoryArray.forEach(function(element) {              
            	var evetit_no = element.evetit_no;
        		var evetit_name = element.evetit_name;
            	var evetit_poster = element.evetit_poster;             
                $(".eventTitleBrowsingHistoryOwlArea")
                .append("<div class='item'><div style='height:30px;'><a href='listOneEventTitle.jsp?evetit_no=" + evetit_no + "' target='_blank'><h6>" + evetit_name + "</h6></a></div><img src='" + evetit_poster + "'></div>");
            });
    	} 	

        var eventTitleBrowsingHistoryOwlArea = $(".eventTitleBrowsingHistoryOwlArea");
        eventTitleBrowsingHistoryOwlArea.owlCarousel({
            margin: 10,
            nav: true,
            responsive: {
                0: {
                    items: 1
                },
                600: {
                    items: 3
                },
                1000: {
                    items: 5
                }
            }
        })
        
        $("#cleanEventTitleBrowsingHistory").click(function(){
        	localStorage.removeItem("eventTitleBrowsingHistory");
        });
    });
    </script>
</body>

</html>