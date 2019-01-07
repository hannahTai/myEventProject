<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.event_title.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.advertisement.model.*"%>
<%
	AdvertisementService advertisementService = new AdvertisementService();
	AdvertisementVO theOneAdvertisementVO = advertisementService.getOneAdvertisementRandom();
	pageContext.setAttribute("theOneAdvertisementVO", theOneAdvertisementVO);
%>
<%
	List<AdvertisementVO> advertisementList = advertisementService.getLaunched();
	pageContext.setAttribute("advertisementList", advertisementList);
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
	<title>前台廣告</title>
	<!-- Basic -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	<!-- Owl Stylesheets -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/OwlCarousel2-2.3.4/docs/assets/owlcarousel/assets/owl.carousel.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/OwlCarousel2-2.3.4/docs/assets/owlcarousel/assets/owl.theme.default.min.css">
	<style>
		.adImgModal img {
			width: 100%;
		}
		
		.adImgModalHeader {
			background-color: #ff3547;
			border-top-left-radius: 6px;
			border-top-right-radius: 6px;
		}
		
		.adImgModalFooter {
			background-color: #ff3547;
			border-bottom-left-radius: 6px;
			border-bottom-right-radius: 6px;
		}
		
		.owl-item:not(.center) img {
			opacity: 0.2;
		}
		
		.owl-item img:hover {
			opacity: 1;
		}
		
		#eventAdvertiseModal {
			background-color: rgba(0, 0, 0, 0.5);
		}
	</style>
</head>
<body>





	<!-- --------------------------------------------------advertisement carousel ::: begin-------------------------------------------------- -->
	<div class="container-fluid">
		<div class="row">
			<div class="owl-carousel owl-theme" id="advertisementCarousel">
				<c:forEach var="advertisementVO" items="${advertisementList}">
					<div class="item">
						<a href="<%= request.getContextPath()%>/frontend/event_title/listOneEventTitle.jsp?evetit_no=${advertisementVO.evetit_no}" target="_blank">
							<img src="<%= request.getContextPath()%>/event_title/EventTitleGifReader?scaleSize=1200&evetit_no=${advertisementVO.evetit_no}">
						</a>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<!-- --------------------------------------------------advertisement carousel ::: end-------------------------------------------------- -->





	<!-- --------------------------------------------------randomOneEventAdvertiseModal ::: begin-------------------------------------------------- -->
	<div class="container">
		<div class="modal fade" id="eventAdvertiseModal">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header adImgModalHeader">
						<font size="5" color="white"> <i
							class="glyphicon glyphicon-bullhorn"></i> <i
							class="glyphicon glyphicon-bullhorn"></i> <i
							class="glyphicon glyphicon-bullhorn"></i> 現正熱賣中
						</font> <i style="color: white;" class="glyphicon glyphicon-menu-right"></i><i
							style="color: white;" class="glyphicon glyphicon-menu-right"></i><i
							style="color: white;" class="glyphicon glyphicon-menu-right"></i>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body adImgModal">
						<a
							href="<%= request.getContextPath()%>/frontend/event_title/listOneEventTitle.jsp?evetit_no=${theOneAdvertisementVO.getEvetit_no()}"
							target="_blank"> <img
							src="<%= request.getContextPath()%>/event_title/EventTitleGifReader?scaleSize=1000&evetit_no=${theOneAdvertisementVO.getEvetit_no()}">
						</a>
					</div>
					<div class="modal-footer adImgModalFooter">
						<h4 class="modal-title">
							<jsp:useBean id="eventTitleService" scope="page"
								class="com.event_title.model.EventTitleService" />
							<a
								href="<%= request.getContextPath()%>/frontend/event_title/listOneEventTitle.jsp?evetit_no=${theOneAdvertisementVO.getEvetit_no()}"
								target="_blank"> <font color="white">${eventTitleService.getOneEventTitle(theOneAdvertisementVO.getEvetit_no()).evetit_name}</font>
							</a>
						</h4>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- --------------------------------------------------randomOneEventAdvertiseModal ::: end-------------------------------------------------- -->





	<!-- Basic -->
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- Owl Stylesheets -->
	<script src="<%=request.getContextPath()%>/vendor/OwlCarousel2-2.3.4/docs/assets/owlcarousel/owl.carousel.js"></script>
	<script>
	$(document).ready(function() {
		localStorage.removeItem("searchStatus");
		$('#eventAdvertiseModal').modal('show');

		var advertisementCarousel = $('#advertisementCarousel');
		advertisementCarousel.owlCarousel({
			center : true,
			margin : 5,
			nav : true,
			loop : true,
			autoplay : true,
			autoplayTimeout : 3000,
			responsive : {
				0 : {
					items : 1,
					stagePadding : 50
				},
				800 : {
					items : 1,
					stagePadding : 150
				},
				1300 : {
					items : 1,
					stagePadding : 300
				}
			}
		})
	});
	</script>
</body>
</html>