<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">

<title>ETIckeTs娛樂後台管理</title>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<style>
.memberphoto {
	border-radius: 50px;
	margin-top: 20px;
}
.membermenu {
	margin-top: 100px;
	margin-left: 200px;
}
.topnav {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: visible;
	background-color: #333;
	display: block;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
	cursor: pointer;
}
</style>
</head>
<body>
	<nav class="navbar navbar-inverse" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-ex1-collapse">
					<span class="sr-only">選單切換</span> <span class="icon-bar"></span> <span
						class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<img src="/CA105G2/backend/LOGO_back-end.png" href="#" alt="LOGO"
					width="202.25px" height="165.5px">
			</div>

			<!-- 手機隱藏選單區  -->

			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<!-- 右選單 -->
				<img
					src="https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-1/c53.53.662.662a/s160x160/996812_623306544360262_513913499_n.jpg?_nc_cat=109&_nc_eui2=AeEvi_vj3AZ5wk2s31mtunvrLPbVPtJK2jf7uWRYtFCuPw_M1yTd23yuh2AGeVu5aGSm_1aLOh_81tqazaXh-ECnpuFl77aq8E38y3WIOxRGcA&_nc_ht=scontent-hkg3-1.xx&oh=c8b216f2429b70114bdb941b525f73cf&oe=5CA0CFE7"
					class="memberphoto" href="#" alt="LOGO" style="float: right"
					width="80px" height="80px">

				<ul class="nav navbar-nav navbar-right membermenu">
					<li><a href="#">管理員登出</a></li>
					<li><a href="#">設定</a></li>
					<!-- 				<li class="dropdown"><a href="#" class="dropdown-toggle" -->
					<!-- 					data-toggle="dropdown">繁體中文 <b class="caret"></b></a> -->
					<!-- 					<ul class="dropdown-menu"> -->
					<!-- 						<li><a href="#">繁體中文</a></li> -->
					<!-- 						<li><a href="#">English</a></li> -->
					<!-- 						<li><a href="#">日本語</a></li> -->
					<!-- 					</ul> -->
					<!-- 				</li> -->
				</ul>
			</div>
			<!-- 手機隱藏選單區結束 -->
		</div>
		<div class="topnav row">
			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-2">
				<div class="dropdown">
					<a class="dropdown-toggle topnav" id="eventManagement" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" align="center">活動管理
						<span class="caret"></span>
					</a>
			        <ul class="dropdown-menu eventManagementMenu" aria-labelledby="eventManagement" style="text-align:center;background-color:rgba(255, 255, 255, 0.9);">
			            <li><a href="<%=request.getContextPath()%>/backend/event_title/listAllEventTitleRelatives.jsp" target="_blank">活動管理</a></li>
			            <li><a href="<%=request.getContextPath()%>/backend/venue/listAllVenue.jsp" target="_blank">場地管理</a></li>
			            <li><a href="<%=request.getContextPath()%>/backend/advertisement/listAllAdvertisement.jsp" target="_blank">廣告管理</a></li>
			            <li><a href="<%=request.getContextPath()%>/backend/event/changeNotice.jsp" target="_blank">異動通知</a></li>
			        </ul>
				</div>			
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-2">
				<div>
					<a href="#" class="topnav" align="center">票券管理</a>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-2">
				<div>
					<a href="#" class="topnav" align="center">商品管理</a>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-2">
				<div>
					<a href="#" class="topnav" align="center">團購管理</a>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-2">
				<div>
					<a href="#" class="topnav" align="center">常見問題管理</a>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4 col-lg-2">
				<div>
					<a href="#" class="topnav" align="center">公告管理</a>
				</div>
			</div>
		</div>
	</nav>
</body>
</html>