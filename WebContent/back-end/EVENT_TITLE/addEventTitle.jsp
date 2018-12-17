<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.EVENT_TITLE.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	EventTitleVO eventTitleVO = (EventTitleVO) request.getAttribute("eventTitleVO");
%>



---eventTitleVO.evetit_no: ${eventTitleVO.evetit_no}---
<br>
---eventTitleVO.eveclass_no: ${eventTitleVO.eveclass_no}---
<br>
---eventTitleVO.ticrefpolicy_no: ${eventTitleVO.ticrefpolicy_no}---
<br>
---eventTitleVO.evetit_name: ${eventTitleVO.evetit_name}---
<br>
---eventTitleVO.evetit_startdate: ${eventTitleVO.evetit_startdate}---
<br>
---eventTitleVO.evetit_enddate: ${eventTitleVO.evetit_enddate}---
<br>
---eventTitleVO.evetit_poster: ${eventTitleVO.evetit_poster}---
<br>
================================================================
<br>
---eventTitleVO.info: ${eventTitleVO.info}---
<br>
---eventTitleVO.notices: ${eventTitleVO.notices}---
<br>
---eventTitleVO.eticpurchaserules: ${eventTitleVO.eticpurchaserules}---
<br>
---eventTitleVO.eticrules: ${eventTitleVO.eticrules}---
<br>
---eventTitleVO.refundrules: ${eventTitleVO.refundrules}---
<br>
================================================================
<br>
---eventTitleVO.evetit_sessions: ${eventTitleVO.evetit_sessions}---
<br>
---eventTitleVO.evetit_status: ${eventTitleVO.evetit_status}---
<br>
---eventTitleVO.launchdate: ${eventTitleVO.launchdate}---
<br>
---eventTitleVO.offdate: ${eventTitleVO.offdate}---
<br>
---eventTitleVO.promotionranking: ${eventTitleVO.promotionranking}---
<br>
================================================================



<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>新增活動主題</title>

<!-- Basic -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<!-- datetimepicker -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.datetimepicker.css" />
</head>

<body>

	<div class="container">
		<div class="tabbable">
			<!-- 外標籤面板：標籤區 -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#eventTitleTab" data-toggle="tab">活動主題</a></li>
				<li><a href="#eventTab_01" data-toggle="tab">活動場次1</a></li>
				<li><a href="#eventTab_02" data-toggle="tab">活動場次2</a></li>
				<li><a href="#tabplus" data-toggle="tab">+++++++++</a></li>
				<li><a href="#eventTitleStatus" data-toggle="tab">上架設定</a></li>
			</ul>
			<!-- 外標籤面板：內容區 -->
			<div class="tab-content">





				<!-- form:::活動主題:::start -->
				<div class="tab-pane active" id="eventTitleTab">
					<form method="post" enctype="multipart/form-data"
						action="<%=request.getContextPath()%>/EVENT_TITLE/EventTitleServlet">
						<div class="form-group">
							<label for="evetit_name">名稱</label> <input type="text"
								name="evetit_name" id="evetit_name" class="form-control"
								value="<%=(eventTitleVO == null) ? "" : eventTitleVO.getEvetit_name()%>">
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group">
									<label for="evetit_startdate">開始日期</label> <input type="text"
										id="evetit_startdate" name="evetit_startdate"
										class="form-control"
										value="<fmt:formatDate value="${eventTitleVO.evetit_startdate}" pattern="yyyy-MM-dd"/>">
								</div>
							</div>
							<div class="col-xs-12 col-sm-6">
								<div class="form-group">
									<label for="evetit_enddate">結束日期</label> <input type="text"
										id="evetit_enddate" name="evetit_enddate" class="form-control"
										value="<fmt:formatDate value="${eventTitleVO.evetit_enddate}" pattern="yyyy-MM-dd"/>">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-2">
								<div class="form-group">
									<label>分類</label> <select class="form-control"
										name="eveclass_no">
										<option value="A">A</option>
										<option value="B">B</option>
										<option value="C">C</option>
									</select>
								</div>
							</div>
							<div class="col-xs-12 col-sm-10">
								<div class="form-group">
									<label>退款政策</label> <select class="form-control"
										name="ticrefpolicy_no">
										<option value="TRP1">TRP1</option>
										<option value="TRP2">TRP2</option>
										<option value="TRP3">TRP3</option>
										<option value="TRP4">TRP4</option>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="evetit_poster">主海報</label> <input type="file"
								id="evetit_poster" name="evetit_poster" class="form-control"
								accept="image/*"> <img src="" id="evetit_poster_preview">
							<img
								src="<%= request.getContextPath()%>/EVENT_TITLE/DBGifReader?evetit_no=${eventTitleVO.evetit_no}">





						</div>
						<div class="tabbable tabs-left">
							<!-- 內標籤面板：標籤區 -->
							<ul class="nav nav-tabs">
								<li class="active"><a href="#infoTab" data-toggle="tab">活動介紹</a></li>
								<li><a href="#noticeTab" data-toggle="tab">注意事項</a></li>
								<li><a href="#eticpurchaserulesTab" data-toggle="tab">購票提醒</a></li>
								<li><a href="#eticrulesTab" data-toggle="tab">用票提醒</a></li>
								<li><a href="#refundrulesTab" data-toggle="tab">退票說明</a></li>
							</ul>
							<!-- 內標籤面板：內容區 -->
							<div class="tab-content">
								<div class="tab-pane active" id="infoTab">
									<p>INFO</p>
									<textarea name="info" id="infoEditor">
                                    <%=(eventTitleVO == null) ? "" : eventTitleVO.getInfo()%>
                                	</textarea>
								</div>
								<div class="tab-pane" id="noticeTab">
									<p>NOTICES</p>
									<textarea name="notices" id="noticesEditor">
                                    <%=(eventTitleVO == null) ? "" : eventTitleVO.getNotices()%>
                                	</textarea>
								</div>
								<div class="tab-pane" id="eticpurchaserulesTab">
									<p>ETICPURCHASERULES</p>
									<textarea name="eticpurchaserules" id="eticpurchaserulesEditor">
                                    <%=(eventTitleVO == null) ? "" : eventTitleVO.getEticpurchaserules()%>
                                	</textarea>
								</div>
								<div class="tab-pane" id="eticrulesTab">
									<p>ETICRULES</p>
									<textarea name="eticrules" id="eticrulesEditor">
                                    <%=(eventTitleVO == null) ? "" : eventTitleVO.getEticrules()%>
                                	</textarea>
								</div>
								<div class="tab-pane" id="refundrulesTab">
									<p>REFUNDRULES</p>
									<textarea name="refundrules" id="refundrulesEditor">
                                    <%=(eventTitleVO == null) ? "" : eventTitleVO.getRefundrules()%>
                                	</textarea>
								</div>
							</div>
						</div>
						<div class="form-group">
							<input type="hidden" name="action" value="insertEventTitle">
							<button type="submit" class="btn btn-primary">
								儲存新增</button>
						</div>
					</form>
				</div>
				<!-- form:::活動主題:::end -->





				<!-- form:::活動場次original:::start -->
				<div class="tab-pane" id="eventTab_01">
					<p>活動場次1</p>
				</div>
				<!-- form:::活動場次original:::end -->



				<div class="tab-pane" id="eventTab_02">
					<p>活動場次2</p>
				</div>
				<div class="tab-pane" id="tabplus">
					<p>++++++++++</p>
				</div>





				<!-- form:::上架設定:::start -->
				<div class="tab-pane" id="eventTitleStatus">
					<form action="">
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group">
									<label for="launchdate">上架日期時間</label> <input type="text"
										id="launchdate" name="launchdate" class="form-control date">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group">
									<label for="offdate">下架日期時間</label> <input type="text"
										id="offdate" name="offdate" class="form-control date">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group">
									<label>推銷熱度</label> <select class="form-control"
										name="promotionranking">
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
									</select>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<div class="form-group">
									<label>儲存狀態</label>
									<div class="radio">
										<label> <input type="radio" name="evetit_status"
											id="temporary" value="temporary" checked>暫存
										</label>
									</div>
									<div class="radio">
										<label> <input type="radio" name="evetit_status"
											id="comfirmed" value="comfirmed">確定
										</label>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<input type="hidden" name="action" value="changeStateEventTitle">
							<button type="submit" class="btn btn-primary">
								儲存</button>
						</div>
					</form>
				</div>
				<!-- form:::上架設定:::end -->
				
				
				
				
				
			</div>
		</div>
	</div>
	<!-- Basic -->
	<script src="https://code.jquery.com/jquery.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- ckEditor JS -->
	<script
		src="<%=request.getContextPath()%>/vendor/ckeditor_full/ckeditor.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/EVENT_TITLE/js/eventTitleCKEditor.js"></script>
	<!-- datetimepicker -->
	<script
		src="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendor/datetimepicker/jquery.datetimepicker.full.js"></script>
	<!-- JavaScript in File -->
	<script
		src="<%=request.getContextPath()%>/back-end/EVENT_TITLE/js/addEventTitle.js"></script>
	<!-- JavaScript in HTML -->
	<script type="text/javascript">
		$(function() {
			initInfoEditor();
			initNoticesEditor();
			initEticpurchaserulesEditor();
			initEticrulesEditor();
			initRefundrulesEditor();

			$("#evetit_poster").change(function() {
				imagesPreview(this);
			});

			console.log("---" + $("#evetit_name").val() + "---");
			console.log("---" + $("#evetit_status").val() + "---");

		});
	</script>
</body>

</html>