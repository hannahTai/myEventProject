<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.event_title.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>ETIckeTs娛樂</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<style>
		.memberphoto {
			border-radius: 50px;
			margin-top:20px;
			}
		.membermenu {
			margin-top:100px;
			margin-left: 200px;
			}
		</style>
	</head>
	<body>
		
		

		<nav class="navbar navbar-default" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
						<span class="sr-only">選單切換</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<img src="https://i.imgur.com/T0YnkK9.png"  href="#" alt="LOGO" width="202.25px" height="165.5px">
				</div>
				
				<!-- 手機隱藏選單區 -->
				
				<div class="collapse navbar-collapse navbar-ex1-collapse">
					<!-- 右選單 -->
					<img src="https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-1/c53.53.662.662a/s160x160/996812_623306544360262_513913499_n.jpg?_nc_cat=109&_nc_eui2=AeEvi_vj3AZ5wk2s31mtunvrLPbVPtJK2jf7uWRYtFCuPw_M1yTd23yuh2AGeVu5aGSm_1aLOh_81tqazaXh-ECnpuFl77aq8E38y3WIOxRGcA&_nc_ht=scontent-hkg3-1.xx&oh=c8b216f2429b70114bdb941b525f73cf&oe=5CA0CFE7" class="memberphoto" href="#"  alt="LOGO" style="float:right" width="80px" height="80px">
				
					<ul class="nav navbar-nav navbar-right membermenu">
						<li><a href="#">登出</a></li>
						<li><a href="#">個人設定</a></li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">繁體中文 <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="#">繁體中文</a></li>
								<li><a href="#">English</a></li>
								<li><a href="#">日本語</a></li>
							</ul>
						</li>
					</ul>
				</div>
				<!-- 手機隱藏選單區結束 -->
			</div>
		</nav>





		<div class="container">
		<div id="carousel-id" class="carousel slide" data-ride="carousel">
		    <!-- 幻燈片小圓點區 -->
		    <ol class="carousel-indicators">
		        <li data-target="#carousel-id" data-slide-to="0" class=""></li>
		        <li data-target="#carousel-id" data-slide-to="1" class=""></li>
		        <li data-target="#carousel-id" data-slide-to="2" class="active"></li>
		    </ol>
		    <!-- 幻燈片主圖區 -->
		    <div class="carousel-inner">
		        <div class="item">
		            <img src="https://static.tixcraft.com/images/activity/19_MrC_440ebd20d4c44ae6dd24f3aa0928b931.jpg" alt="" style="display:block; margin:auto">
		        </div>
		        <div class="item">
		            <img src="https://static.tixcraft.com/images/activity/18_LisAni_90af209a6c8ac5d0e42f534f061c6ab0.jpg" alt="" style="display:block; margin:auto">
		        </div>
		        <div class="item active">
		            <img src="https://static.tixcraft.com/images/activity/18_IU_612d92aa08abcad087c1f2b82117a02a.png" alt="" style="display:block; margin:auto">
		        </div>
		    </div>
		    <!-- 上下頁控制區 -->
		    <a class="left carousel-control" href="#carousel-id" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
		    <a class="right carousel-control" href="#carousel-id" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
		</div>

		<br>


		<div class="container">
				<div class="col-xs-12 col-sm-2">
					<div class="list-group">
						<a href="<%=request.getContextPath()%>/frontend/event_title/selectEventTitle.jsp" class="list-group-item" align="center">所有活動</a>
					</div>
				</div>
				<div class="col-xs-12 col-sm-2">
					<div class="list-group">
						<a href="#" class="list-group-item" align="center">周邊商品</a>
					</div>
				</div>
				<div class="col-xs-12 col-sm-2">
					<div class="list-group">
						<a href="#" class="list-group-item" align="center">所有團購</a>
					</div>
				</div>
				<div class="col-xs-12 col-sm-2">
					<div class="list-group">
						<a href="#" class="list-group-item" align="center">轉讓票券</a>
					</div>
				</div>
				<div class="col-xs-12 col-sm-2">
					<div class="list-group">
						<a href="#" class="list-group-item" align="center">常見問題</a>
					</div>
				</div>
				<div class="col-xs-12 col-sm-2">
					<div class="list-group">
						<a href="#" class="list-group-item" align="center">訂單查詢</a>
					</div>
			</div>



			<div>
				<div>
					<div class="row">
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="http://www.fangoods.com.tw/UMG/Uploads/Product/O_20180402164850_0.jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="https://shoplineimg.com/594c83239f9a4ff00f00631d/5af178b510abb9e38100357b/400x.webp?source_format=jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="http://www.fangoods.com.tw/UMG/Uploads/Product/O_20161202182430_0.jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div>
				<div>
					<div class="row">
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="https://shoplineimg.com/594c83239f9a4ff00f00631d/5af178b70e64fe332900190a/400x.webp?source_format=jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="https://shoplineimg.com/594c83239f9a4ff00f00631d/5af178c70e64fe068300332a/400x.webp?source_format=jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="http://www.fangoods.com.tw/UMG/Uploads/Product/O_20161205180205_0.jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div>
				<div>
					<div class="row">
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="http://www.fangoods.com.tw/UMG/Uploads/Product/O_20161205183921_0.jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="http://www.fangoods.com.tw/UMG/Uploads/Product/O_20161205190011_0.jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="http://www.fangoods.com.tw/UMG/Uploads/Product/O_20161205182128_0.jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div>
				<div>
					<div class="row">
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="https://www.rockmall.com.tw/public/files/product/thumb/N56716-64495R.png" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="https://www.rockmall.com.tw/public/files/product/thumb/N04311-50205R.jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="https://www.rockmall.com.tw/public/files/product/thumb/N77730-53359R.jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div>
				<div>
					<div class="row">
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="https://www.rockmall.com.tw/public/files/product/thumb/N17407-49085R.jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="https://www.rockmall.com.tw/public/files/product/thumb/N22991-86992R.jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<div class="thumbnail">
								<img src="https://www.rockmall.com.tw/public/files/product/thumb/N39705-24419R.jpg" alt="" width="30%" height="30%">
								<div class="caption">
									<h2>商品</h2>
									<p>簡短介紹</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<footer align="center" style="background-color:rgb(51, 153, 255);">
			<br>
			| 關於我們 | 會員服務條款 | 隱私權政策 |

			<br><br><br><br>

			ETIckeTs娛樂客服信箱<p>ca105.java.002@gmail.com</p>
			<br>
		</footer>


		
		
		<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script>    	
	
	    $(document).ready(function() {
	        localStorage.removeItem("searchStatus");
	    });
    
    
	</script>
	</body>
</html>