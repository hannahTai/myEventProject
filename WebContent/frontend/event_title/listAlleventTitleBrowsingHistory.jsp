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
    <!-- Owl Stylesheets -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/OwlCarousel2-2.3.4/docs/assets/owlcarousel/assets/owl.carousel.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/OwlCarousel2-2.3.4/docs/assets/owlcarousel/assets/owl.theme.default.min.css">
</head>

<body>


<div class="container">
	<div class="owl-carousel owl-theme eventTitleBrowsingHistoryOwlArea">
	</div>
</div>


    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendor/OwlCarousel2-2.3.4/docs/assets/owlcarousel/owl.carousel.js"></script>
    <script>
    $(document).ready(function() {
    	
    	var eventTitleBrowsingHistoryArray = JSON.parse(localStorage.getItem("eventTitleBrowsingHistory"));

    	eventTitleBrowsingHistoryArray.forEach(function(element) {
            
    		var evetit_name = element.evetit_name;
        	var evetit_poster = element.evetit_poster;
        	
    		console.log(element);
            console.log(element.evetit_name);
            console.log(element.evetit_poster);
            
            $(".eventTitleBrowsingHistoryOwlArea").append("<div class='item'><h6>" + evetit_name + "</h6><img src='" + evetit_poster + "'></div>");
        });
    	
    	

//         $(".eventTitleBrowsingHistoryOwlArea")
//         .append("<div class='item'><h6>" + word + "</h6><img src='" + img + "'></div>")
//         .append("<div class='item'><h6>" + word + "</h6><img src='" + img + "'></div>")
//         .append("<div class='item'><h6>" + word + "</h6><img src='" + img + "'></div>")
//         .append("<div class='item'><h6>" + word + "</h6><img src='" + img + "'></div>")
//         .append("<div class='item'><h6>" + word + "</h6><img src='" + img + "'></div>")
//         .append("<div class='item'><h6>" + word + "</h6><img src='" + img + "'></div>")
//         .append("<div class='item'><h6>" + word + "</h6><img src='" + img + "'></div>")
//         .append("<div class='item'><h6>" + word + "</h6><img src='" + img + "'></div>")
//         .append("<div class='item'><h6>" + word + "</h6><img src='" + img + "'></div>")
//         .append("<div class='item'><h6>" + word + "</h6><img src='" + img + "'></div>")
//         .append("<div class='item'><h6>" + word + "</h6><img src='" + img + "'></div>");



        var eventTitleBrowsingHistoryOwlArea = $(".eventTitleBrowsingHistoryOwlArea");
        eventTitleBrowsingHistoryOwlArea.owlCarousel({
            margin: 10,
            nav: true,
            loop: true,
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
    });
    </script>
</body>

</html>